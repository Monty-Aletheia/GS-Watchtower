package br.com.fiap.watchtower.mq.producer;

import br.com.fiap.watchtower.model.RiskPoint;
import net.minidev.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskPointMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public RiskPointMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRiskPoint(RiskPoint riskPoint) {
        JSONObject jsonObject = createRiskPointJson(riskPoint);
        System.out.println("Sending Risk Point: " + jsonObject);
        rabbitTemplate.convertAndSend("MlNetService.App.Dtos.Messaging:CreateMarkersResponse",
                "create-marker-info",
                jsonObject,
                msg ->
                {
                    msg.getMessageProperties().setContentType("application/json");
                    msg.getMessageProperties().setHeader("messageType", List.of("urn:message:MlNetService.App.Dtos.Messaging:CreateMarkersResponse"));

                    return msg;
                });
    }

    private JSONObject createRiskPointJson(RiskPoint riskPoint) {
        JSONObject riskPointJson = new JSONObject();
        riskPointJson.put("latitude", riskPoint.getLatitude());
        riskPointJson.put("longitude", riskPoint.getLongitude());
        riskPointJson.put("desasterType", riskPoint.getDesasterType());
        riskPointJson.put("markerType", riskPoint.getMarkerType());
        riskPointJson.put("timestamp", riskPoint.getTimestamp().toString());
        riskPointJson.put("aiAnalysis", riskPoint.getAiAnalysis());
        riskPointJson.put("description", riskPoint.getDescription());
        riskPointJson.put("sensorData", riskPoint.getSensorData());
        JSONObject markerInfo = new JSONObject();
        markerInfo.put("MarkerInfo", riskPointJson);
        return markerInfo;
    }
}
