package br.com.fiap.watchtower.mq.producer;

import br.com.fiap.watchtower.model.RiskPoint;
import net.minidev.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RiskPointMessageProducer {


    private final RabbitTemplate rabbitTemplate;

    public RiskPointMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRiskPoint(RiskPoint riskPoint) {
        JSONObject jsonObject = createRiskPointJson(riskPoint);
        rabbitTemplate.convertAndSend("java-queue", "patient-risk-exchange", jsonObject.toString());
    }

    public JSONObject createRiskPointJson(RiskPoint riskPoint) {
        JSONObject riskPointJson = new JSONObject();
        riskPointJson.put("latitude", riskPoint.getLatitude());
        riskPointJson.put("longitude", riskPoint.getLongitude());
        riskPointJson.put("desasterType", riskPoint.getDesasterType());
        riskPointJson.put("markerType", riskPoint.getMarkerType());
        riskPointJson.put("markerName", riskPoint.getMarkerName());
        riskPointJson.put("markerImage", riskPoint.getMarkerImage());
        riskPointJson.put("riskLevel", riskPoint.getRiskLevel());
        riskPointJson.put("timestamp", riskPoint.getTimestamp());
        riskPointJson.put("aiAnalysis", riskPoint.getAiAnalysis());
        riskPointJson.put("description", riskPoint.getDescription());
        riskPointJson.put("sensorData", riskPoint.getSensorData());
        return riskPointJson;
    }
}
