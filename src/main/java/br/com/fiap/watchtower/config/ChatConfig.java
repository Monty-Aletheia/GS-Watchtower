package br.com.fiap.watchtower.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
    @Bean
    public ChatClient getChatClient(ChatClient.Builder builder) {
        return builder.build();
    }
}
