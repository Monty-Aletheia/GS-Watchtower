package br.com.fiap.watchtower.service;

import br.com.fiap.watchtower.model.RiskLevel;
import br.com.fiap.watchtower.model.SensorData;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class RiskAnalysisService {

    private final ChatClient chatClient;

    public RiskAnalysisService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String analyzeRisk(String desasterType, double latitude, double longitude, SensorData sensorData) {
        String systemPrompt = """
            Você é um especialista em análise de riscos de desastres naturais para uma
            aplicação utilizada pela defesa civil.
            
            Você receberá informações captadas por um dispositivo IOT que traz dados
            sensoriais capturados de um município, incluindo:
            - Temperatura (°C)
            - Umidade (%)
            - Pressão atmosférica (hPa)
            - Velocidade do vento (m/s)
            - Volume de chuva (mm)
            - Nível de água (m)
            - Concentração de gases (ppm)
            - Luminosidade (lux)
            
            Com base nesses dados, forneça uma análise CONCISA (máximo 3 linhas) do risco identificado e
            recomendações imediatas para a defesa civil.
            Infira informações com os dados enviados, por exemplo, o nome do município com base na latitude e longitude.
            Traga também o nível de risco identificado (BAIXO, MÉDIO, ALTO ou CRÍTICO) e uma descrição no rodapé (depois de todo o resto do texto):
            
            Descrição: <Texto breve em no máximo 1 linha descrevendo o ponto de risco e nome do local onde aconteceu>
            
            Exemplo de resposta:
            '
            Risco de evento extremo detectado na área de <Nome do município>. <Sugestões de ação>.
            
            Descrição: Risco detectado em Guarulhos
            '
            """;

        String userPrompt = String.format("""
            Tipo de Desastre: %s
            Localização: %.4f, %.4f
            
            Dados dos Sensores:
            - Temperatura: %.1f°C
            - Umidade: %.1f%%
            - Pressão: %.1f hPa
            - Vento: %.1f m/s
            - Chuva: %.1f mm
            - Nível de Água: %.2f m
            - Gases: %.3f ppm
            - Luminosidade: %.1f lux
            """,
            desasterType,
            latitude,
            longitude,
            sensorData.getTemperatura(),
            sensorData.getUmidade(),
            sensorData.getPressao(),
            sensorData.getVento(),
            sensorData.getChuva(),
            sensorData.getNivelAgua(),
            sensorData.getGases(),
            sensorData.getLuminosidade()
        );
       
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

    public String[] extractDescriptionFromAiAnalysis(String aiAnalysis) {
        String description = "Ponto de risco";
        String chave = "Descrição: ";
        int pos = aiAnalysis.indexOf(chave);

        if (pos != -1) {
            description = aiAnalysis.substring(pos + chave.length()).trim();
            aiAnalysis = aiAnalysis.substring(0, pos).trim();
            System.out.println(description);
        }

        return new String[] { aiAnalysis, description };
    }


} 