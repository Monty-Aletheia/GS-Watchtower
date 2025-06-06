package br.com.fiap.watchtower.mq.consumer;

import br.com.fiap.watchtower.model.RiskPoint;
import br.com.fiap.watchtower.service.RiskAnalysisService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RiskEventConsumer {

    private final RiskAnalysisService riskAnalysisService;
    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RiskEventConsumer.class);

    public RiskEventConsumer(RiskAnalysisService riskAnalysisService, 
                           SimpMessagingTemplate messagingTemplate) {
        this.riskAnalysisService = riskAnalysisService;
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "java-queue")
    public void receiveRiskEvent(@Payload RiskPoint riskPoint) {
        try {
            logger.info("Recebendo mensagem do RabbitMQ: {}", riskPoint);

            System.out.println(riskPoint);
            String aiAnalysis = riskAnalysisService.analyzeRisk(
                riskPoint.getDesasterType(),
                riskPoint.getLatitude(),
                riskPoint.getLongitude(),
                riskPoint.getSensorData()
            );

//            String aiAnalysis = "TESTE";
            riskPoint.setRiskLevel(riskAnalysisService.determineRiskLevel(aiAnalysis));

            aiAnalysis =  riskAnalysisService.extractDescriptionFromAiAnalysis(aiAnalysis)[0];
            String description = riskAnalysisService.extractDescriptionFromAiAnalysis(aiAnalysis)[1];

            riskPoint.setDescription(description);
            riskPoint.setAiAnalysis(aiAnalysis);
            
            logger.info("Ponto de risco processado, enviando via WebSocket: {}", riskPoint);
            
            messagingTemplate.convertAndSend("/topic/risk-points", riskPoint);
            logger.info("Mensagem enviada com sucesso via WebSocket");
            
        } catch (Exception e) {
            logger.error("Erro ao processar mensagem de risco: {}", e.getMessage(), e);
        }
    }
} 