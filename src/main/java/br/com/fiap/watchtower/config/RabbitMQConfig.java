package br.com.fiap.watchtower.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_RISK_EVENTS = "java-queue";
    public static final String EXCHANGE_RISK_EVENTS = "risk-events-exchange";
    public static final String ROUTING_KEY_RISK_EVENTS = "risk.event.#";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_RISK_EVENTS);
    }

    @Bean
    public Queue markerQueue() {
        return new Queue("create-marker-info");
    }

//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(EXCHANGE_RISK_EVENTS);
//    }
//
//    @Bean
//    public Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder
//                .bind(queue)
//                .to(exchange)
//                .with(ROUTING_KEY_RISK_EVENTS);
//    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
} 