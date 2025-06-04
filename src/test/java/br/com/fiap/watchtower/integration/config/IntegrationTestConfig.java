package br.com.fiap.watchtower.integration.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@TestConfiguration
public class IntegrationTestConfig {
    
    @MockBean
    private RabbitTemplate rabbitTemplate;
} 