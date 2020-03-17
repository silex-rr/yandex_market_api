package com.github.silexrr.yandex_market_api.api.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitMqListener {

    @RabbitListener(queues = "${rabbitmq.request.queue}")
    public void processQueue1(String message) {
        System.out.println("Consuming Message - " + message);
    }
}