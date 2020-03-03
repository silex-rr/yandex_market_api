package com.github.silexrr.yandex_market_api.api.service;

import com.github.silexrr.yandex_market_api.api.model.Request;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RequestRestService {

    @Autowired
    private AmqpTemplate rabbitTemplateCustom;

    @Value("${request.rabbitmq.exchange}")
    private String exchange;

    @Value("${request.rabbitmq.routingKey}")
    private String routingKey;

    public void add(Request request) {
        rabbitTemplateCustom.convertAndSend(exchange, routingKey, request);
        System.out.println("Send msg=" + request);
    }
}
