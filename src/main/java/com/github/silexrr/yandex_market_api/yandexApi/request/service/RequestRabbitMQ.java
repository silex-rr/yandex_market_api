package com.github.silexrr.yandex_market_api.yandexApi.request.service;

import com.github.silexrr.yandex_market_api.yandexApi.request.model.Request;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RequestRabbitMQ {
    @RabbitListener(queues = "${rabbitmq.request.queue}")
    public void receivedMessage(Request request) {
        System.out.println("MQ " + request);
    }
}
