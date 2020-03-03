package com.github.silexrr.yandex_market_api.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig
{
    @Value("${rabbitmq.request.queue}")
    public String requestQueue;

    @Value("${rabbitmq.request.deadQueue}")
    public String requestDeadQueue;

    @Value("${rabbitmq.request.exchange}")
    public String requestExchange;

    @Value("${rabbitmq.request.routingKey}")
    public String requestRoutingKey;


    public static final String EXCHANGE_ORDERS = "orders-exchange";

    @Bean
    Queue ordersQueue() {
        return QueueBuilder.durable(this.requestQueue).build();
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(this.requestDeadQueue).build();
    }
    @Bean
    Exchange ordersExchange() {
        return ExchangeBuilder.topicExchange(this.requestExchange).build();
    }

//    @Bean
//    Binding binding(Queue ordersQueue, TopicExchange ordersExchange) {
//        return BindingBuilder.bind(ordersQueue).to(ordersExchange).with(this.requestQueue);
//    }
}