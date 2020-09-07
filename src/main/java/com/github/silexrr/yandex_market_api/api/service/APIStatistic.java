package com.github.silexrr.yandex_market_api.api.service;

import com.github.silexrr.yandex_market_api.auth.service.SecurityServiceImpl;
import com.github.silexrr.yandex_market_api.config.RabbitMQConfig;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.service.ShopMQListener;
import com.github.silexrr.yandex_market_api.shop.service.ShopService;
import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.domain.QueueInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;

@Service
@Scope("singleton")
public class APIStatistic {

    private long totalMessagesGlobal = 0;
    private HashMap<Shop, Long> privateShopsQueueTotalMessages = new HashMap<>();

    private Client client;
    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private ShopService shopService;
    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    private void connectToRabbit() {
        String rabbitMQHost = rabbitMQConfig.getRabbitHost();
        String rabbitMQStatisticPort = rabbitMQConfig.getRabbitStatisticPort();
        String uri = "http://" + rabbitMQHost + ":" + rabbitMQStatisticPort + "/api/";
        try {
            client = new Client(uri, "guest", "guest");
        } catch (MalformedURLException | URISyntaxException exception) {
            logger.warn("API Statistic can't connect to RabbitMQ by " + uri);
            client = null;
        }
    }

    private Client getClient() {
        if (client == null) {
            this.connectToRabbit();
        }
        return client;
    }

    public void statisticUpdate() {

        long totalMessagesGlobalTmp = 0;

        Client client = this.getClient();

        for (Shop shop : shopService.getEnable(true)) {
            for(Queue queue : ShopMQListener.getShopQueue(shop)){
                String queueName = queue.getName();
                QueueInfo Queue = client.getQueue("/", queueName);
                long totalMessages = Queue.getTotalMessages();
                totalMessagesGlobalTmp += totalMessages;
                privateShopsQueueTotalMessages.put(shop, totalMessages);
            }
        }

        this.totalMessagesGlobal = totalMessagesGlobalTmp;
    }

    public long getTotalMessagesGlobal() {
        return totalMessagesGlobal;
    }

}
