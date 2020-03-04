package com.github.silexrr.yandex_market_api.api.service;

import com.github.silexrr.yandex_market_api.api.model.Request;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RequestRestService {

    @Autowired
    private AmqpTemplate rabbitTemplateCustom;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.request.exchange}")
    private String exchange;

    @Value("${rabbitmq.request.routingKey}")
    private String routingKey;

    public void add(Request request) {
        rabbitTemplate.convertAndSend(exchange, routingKey, request);
        //rabbitTemplateCustom.convertAndSend(exchange, routingKey, request);
        System.out.println("Send msg=" + request);
    }
}
