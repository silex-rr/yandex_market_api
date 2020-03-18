package com.github.silexrr.yandex_market_api.api.service;

import com.github.silexrr.yandex_market_api.api.model.Request;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitMqListener {

    @RabbitListener(queues = "${rabbitmq.request.queue}")
    public void processQueue1(Request request) {
        System.out.println("Consuming 1 Message - " + request);
    }

}