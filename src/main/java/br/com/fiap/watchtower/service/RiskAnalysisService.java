package br.com.fiap.watchtower.service;

import br.com.fiap.watchtower.model.RiskLevel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class RiskAnalysisService {

    private final ChatClient chatClient;

    public RiskAnalysisService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String analyzeRisk(String description, double latitude, double longitude) {
        String systemPrompt = """
            Você é um especialista em análise de riscos de desastres naturais.
            Analise a descrição fornecida e forneça uma avaliação resumida do risco,
            considerando a localização geográfica (latitude e longitude).
            Forneça recomendações específicas para mitigação de riscos.
            """;

        String userPrompt = String.format("""
            Localização: Latitude %f, Longitude %f
            Descrição do evento: %s
            
            Por favor, forneça:
            1. Nível de risco estimado
            2. Análise resumida da situação
            3. Recomendações de ação imediata
            """, latitude, longitude, description);
       
        return this.chatClient.prompt().system(systemPrompt).user(userPrompt).call().content();
    }

    public RiskLevel determineRiskLevel(String aiAnalysis) {
        if (aiAnalysis.toLowerCase().contains("crítico") || 
            aiAnalysis.toLowerCase().contains("grave") || 
            aiAnalysis.toLowerCase().contains("extremo")) {
            return RiskLevel.CRITICAL;
        } else if (aiAnalysis.toLowerCase().contains("alto")) {
            return RiskLevel.HIGH;
        } else if (aiAnalysis.toLowerCase().contains("médio")) {
            return RiskLevel.MEDIUM;
        } else {
            return RiskLevel.LOW;
        }
    }
} 