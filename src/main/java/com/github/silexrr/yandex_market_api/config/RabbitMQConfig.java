package com.github.silexrr.yandex_market_api.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;
    @Value("${spring.rabbitmq.statistic.port}")
    private String rabbitStatisticPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Value("${rabbitmq.request.queue}")
    private String queueName;

    @Value("${rabbitmq.request.exchange}")
    private String exchange;

    @Value("${rabbitmq.request.commonRoutingKey}")
    private String commonRoutingKey;

    @Value("${rabbitmq.request.privetRoutingKeyBase}")
    private String privetRoutingKeyBase;

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(this.rabbitHost);
        cachingConnectionFactory.setUsername(this.rabbitUsername);
        cachingConnectionFactory.setPassword(this.rabbitPassword);
        return cachingConnectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplateCustom(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

//	@Bean
//    public MessageListenerContainer messageListenerContainer() {
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//        simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
//        simpleMessageListenerContainer.setQueues(queue());
////        simpleMessageListenerContainer.setMessageListener(new RabbitMQListner());
//        return simpleMessageListenerContainer;
//    }

    /**
     * Define priority queue
     */
    @Bean
    public Queue queue() {
        Map<String, Object> args= new HashMap<>();
        args.put("x-max-priority", 100);
        return new Queue(queueName, false, false, false, args);
    }


    public static AbstractMessageListenerContainer startListening(
            RabbitAdmin rabbitAdmin,
            Queue queue,
            Exchange exchange,
            List<String> keys,
            MessageListener messageListener
    ) {
        keys.forEach(key -> {
            rabbitAdmin.declareBinding(
                    BindingBuilder.bind(queue).to(exchange).with(key).noargs()
            );
        });
        SimpleMessageListenerContainer listener
                = new SimpleMessageListenerContainer(rabbitAdmin.getRabbitTemplate().getConnectionFactory());
        listener.addQueues(queue);
        listener.setMessageListener(messageListener);
        listener.start();
        return listener;
    }

    public static void stopListening (SimpleMessageListenerContainer listener) {
        listener.stop();
    }

    public String getQueueName() {
        return queueName;
    }

    public String getExchange() {
        return exchange;
    }



    public String getCommonRoutingKey() {
        return commonRoutingKey;
    }

    public String getPrivetRoutingKeyBase() {
        return privetRoutingKeyBase;
    }

    public String getRabbitHost() {
        return rabbitHost;
    }

    public String getRabbitStatisticPort() {
        return rabbitStatisticPort;
    }
}