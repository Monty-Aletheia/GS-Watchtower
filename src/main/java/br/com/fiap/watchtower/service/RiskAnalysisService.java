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
            Forneça uma análise MUITO CONCISA (máximo 3 linhas) do risco descrito.
            Use linguagem direta e profissional.
            Foque apenas em:
            1. Nível de risco sugerido (BAIXO, MÉDIO, ALTO ou CRÍTICO)
            2. Principal preocupação
            3. Ação recomendada
            """;

        String userPrompt = String.format("""
            Local: %s
            Coordenadas: %.4f, %.4f
            
            Responda em português, em formato de tópicos curtos:
            """, description, latitude, longitude);
       
        return this.chatClient.prompt().system(systemPrompt).user(userPrompt).call().content();
    }

    public RiskLevel determineRiskLevel(String aiAnalysis) {
        String analysis = aiAnalysis.toLowerCase();
        if (analysis.contains("crítico")) {
            return RiskLevel.CRITICAL;
        } else if (analysis.contains("alto")) {
            return RiskLevel.HIGH;
        } else if (analysis.contains("médio")) {
            return RiskLevel.MEDIUM;
        } else {
            return RiskLevel.LOW;
        }
    }
} 