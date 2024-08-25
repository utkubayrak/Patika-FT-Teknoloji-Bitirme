package com.utkubayrak.jetbususerservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.email.name}")
    private String emailQueue;
    @Value("${rabbitmq.queue.password.name}")
    private String passwordQueue;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.binding.email.routing.key}")
    private String emailRoutingKey;
    @Value("${rabbitmq.binding.password.routing.key}")
    private String passwordRoutingKey;

    @Bean
    public Queue emailQueue(){
        return new Queue(emailQueue);
    }
    @Bean
    public Queue passwordQueue(){
        return new Queue(passwordQueue);
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }
    @Bean
    public Binding emailBinding(){
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(emailRoutingKey);
    }
    @Bean
    public Binding passwordBinding(){
        return BindingBuilder
                .bind(passwordQueue())
                .to(exchange())
                .with(passwordRoutingKey);
    }
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
