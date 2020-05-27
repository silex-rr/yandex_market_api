package com.github.silexrr.yandex_market_api.api.service;

import com.github.silexrr.yandex_market_api.YandexMarketApiApplication;
import com.github.silexrr.yandex_market_api.api.model.Request;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.BindingBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.github.silexrr.yandex_market_api.config.RabbitMQConfig.startListening;

@Service
public class RequestRestService {

//    @Autowired
//    private AmqpTemplate rabbitTemplateCustom;
    @Autowired
    private AmqpTemplate rabbitTemplateCustom;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Value("${rabbitmq.request.exchange}")
    private String exchange;

    @Value("${rabbitmq.request.commonRoutingKey}")
    private String commonRoutingKey;
    @Value("${rabbitmq.request.privetRoutingKeyBase}")
    private String privateRoutingKeyBase;

    public void add(Request request) {

//        System.out.println("Send msg=" + request);
//        template.convertAndSend(exchange, routingKey, "Message");

//        Map map = parseParams(request.getParam());
//        System.out.println(map);

        String key = commonRoutingKey;

        String shop = request.getShop();
        if (shop != "") {
            key = privateRoutingKeyBase + '.' + shop;
        }
        System.out.println("Send msg=" + request.getParam() + " for exchange " + exchange + " whit key " + key);
        rabbitTemplateCustom.convertAndSend(exchange, key, request);

//        rabbitTemplateCustom.convertAndSend(exchange, routingKey, request);
    }

    public SimpleMessageListenerContainer addListener(String queueName, String exchangeName, String key, MessageListener messageListener) {
        Queue queue = new Queue(queueName);
        Exchange exchange = new DirectExchange(exchangeName);

        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(key).noargs());
        SimpleMessageListenerContainer listener = new SimpleMessageListenerContainer(rabbitAdmin.getRabbitTemplate().getConnectionFactory());
        listener.addQueues(queue);
        listener.setMessageListener(messageListener);
        listener.start();

        return listener;
    }



//    public void ping()
//    {
//        System.out.println("pong");
//    }

    public Map parseParams(String param)
    {
        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        return jsonParser.parseMap(param);
    }

    public RabbitAdmin getRabbitAdmin() {
        return rabbitAdmin;
    }
}
