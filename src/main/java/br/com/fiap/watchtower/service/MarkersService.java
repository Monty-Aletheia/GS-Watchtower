package br.com.fiap.watchtower.service;

import br.com.fiap.watchtower.model.RiskLevel;
import br.com.fiap.watchtower.model.RiskPoint;
import br.com.fiap.watchtower.model.RiskPointResponse;
import br.com.fiap.watchtower.model.SensorData;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarkersService {

    private static final Logger logger = LoggerFactory.getLogger(MarkersService.class);
    private final RestTemplate restTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    public MarkersService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.restTemplate = new RestTemplate();
    }

    private RiskLevel determineRiskLevel(String desasterType, SensorData sensorData) {
        if (desasterType == null) {
            return RiskLevel.LOW;
        }

        desasterType = desasterType.toLowerCase();
        
        switch (desasterType) {
            case "enchente":
                if (sensorData != null && sensorData.getNivelAgua() > 2.0) {
                    return RiskLevel.CRITICAL;
                }
                return RiskLevel.HIGH;
            case "tempestade":
                return RiskLevel.HIGH;
            case "onda de calor":
                if (sensorData != null && sensorData.getTemperatura() > 35.0) {
                    return RiskLevel.CRITICAL;
                }
                return RiskLevel.HIGH;
            default:
                return RiskLevel.MEDIUM;
        }
    }

    public void loadAllMarkers() {
        try {
            logger.info("Iniciando carregamento de marcadores da API");
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            logger.debug("Fazendo requisição para a API de marcadores");
            ResponseEntity<RiskPointResponse> response = restTemplate.exchange(
                "https://mobile-rabbit-api.azurewebsites.net/markers",
                HttpMethod.GET,
                entity,
                RiskPointResponse.class
            );
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                logger.error("Erro na resposta da API: {}", response.getStatusCode());
                return;
            }

            if (response.getBody() == null) {
                logger.error("Resposta da API está vazia");
                return;
            }

            List<RiskPoint> riskPoints = response.getBody().markers();
            logger.info("Recebidos {} marcadores da API", riskPoints.size());

            for (RiskPoint point : riskPoints) {
                RiskLevel riskLevel = determineRiskLevel(point.getDesasterType(), point.getSensorData());
                point.setRiskLevel(riskLevel);
                logger.debug("Marcador processado - Tipo: {}, Nível de Risco: {}, Coordenadas: {}, {}",
                    point.getDesasterType(), riskLevel, point.getLatitude(), point.getLongitude());
            }
            
            logger.info("Enviando {} marcadores via WebSocket", riskPoints.size());
            messagingTemplate.convertAndSend("/topic/markers", riskPoints);
            logger.info("Marcadores enviados com sucesso");
            
        } catch (Exception e) {
            logger.error("Erro ao carregar marcadores: {}", e.getMessage(), e);
        }
    }

    private List<RiskPoint> convertJsonToRiskPoints(String jsonString) {
        List<RiskPoint> riskPoints = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                RiskPoint riskPoint = new RiskPoint();

                riskPoint.setLatitude((Double) jsonObject.get("latitude"));
                riskPoint.setLongitude((Double) jsonObject.get("longitude"));
                riskPoint.setDesasterType((String) jsonObject.get("desasterType"));
                riskPoint.setMarkerType((String) jsonObject.get("markerType"));
                riskPoint.setTimestamp(LocalDateTime.parse((String) jsonObject.get("timestamp")));
                riskPoint.setAiAnalysis((String) jsonObject.get("aiAnalysis"));
                riskPoint.setDescription((String) jsonObject.get("description"));
                
                JSONObject sensorDataJson = (JSONObject) jsonObject.get("sensorData");
                if (sensorDataJson != null) {
                    SensorData sensorData = new SensorData(
                        (Double) sensorDataJson.get("temperatura"),
                        (Double) sensorDataJson.get("umidade"),
                        (Double) sensorDataJson.get("pressao"),
                        (Double) sensorDataJson.get("vento"),
                        (Double) sensorDataJson.get("chuva"),
                        (Double) sensorDataJson.get("nivelAgua"),
                        (Double) sensorDataJson.get("gases"),
                        (Double) sensorDataJson.get("luminosidade")
                    );
                    riskPoint.setSensorData(sensorData);
                }

                // Define o riskLevel baseado no tipo de desastre e dados do sensor
                riskPoint.setRiskLevel(determineRiskLevel(riskPoint.getDesasterType(), riskPoint.getSensorData()));

                riskPoints.add(riskPoint);
            }
        } catch (Exception e) {
            logger.error("Erro ao converter JSON para RiskPoints: {}", e.getMessage(), e);
        }
        return riskPoints;
    }
}
