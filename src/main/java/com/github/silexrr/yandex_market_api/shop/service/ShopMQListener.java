package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.api.model.Request;
import com.github.silexrr.yandex_market_api.auth.service.SecurityServiceImpl;
import com.github.silexrr.yandex_market_api.config.RabbitMQConfig;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.YMToken;
import com.github.silexrr.yandex_market_api.yandexApi.Method;
import com.github.silexrr.yandex_market_api.yandexApi.model.Response;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.Query;
import com.github.silexrr.yandex_market_api.yandexApi.request.service.RequestService;
import com.github.silexrr.yandex_market_api.yandexApi.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.silexrr.yandex_market_api.config.RabbitMQConfig.startListening;

public class ShopMQListener {

    static private HashMap<String, ArrayList<ShopMQListener>> shopMqListeners = new HashMap<String, ArrayList<ShopMQListener>>();
    static private HashMap<String, ArrayList<Queue>> shopQueue = new HashMap<>();

    private AbstractMessageListenerContainer containerCommon;
    private AbstractMessageListenerContainer containerPrivate;
    private String exchange;
    private static String commonKey;
    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
    private String privetKeyBase;
    private String privetKey;
    private RabbitAdmin rabbitAdmin;
    private MessageConverter messageConverter;

    private ShopMQListener (Shop shop, RabbitMQConfig rabbitMQConfig, ResponseService responseService, ShopStatisticsService shopStatisticsService) {
        rabbitAdmin = rabbitMQConfig.rabbitAdmin();
        exchange = rabbitMQConfig.getExchange();
        commonKey = rabbitMQConfig.getCommonRoutingKey();
        privetKeyBase = rabbitMQConfig.getPrivetRoutingKeyBase();
        messageConverter = rabbitMQConfig.jsonMessageConverter();
        logger.info("Add MQ listener for shop " + shop.getName() + "(" + shop.getId() + ")");
        this.privetKey = privetKeyBase + "." + shop.getId();
//        System.out.println("Add one for exchange " + exchange + " with key " + commonKey);
        ArrayList<String> keys = new ArrayList<>();
        keys.add(commonKey);
        keys.add(privetKey);
//        ApplicationContext applicationContext =
//                new ClassPathXmlApplicationContext("./../../scopes.xml");

        Queue queue = rabbitAdmin.declareQueue();
        DirectExchange exchange = new DirectExchange(this.exchange);
        rabbitAdmin.declareExchange(exchange);
        containerCommon = startListening(
                rabbitAdmin,
                queue,
                exchange,
                keys,
                message -> {
                    LocalDateTime lastRequestTime = shopStatisticsService.getLastRequestTime(shop);

                    if (lastRequestTime != null) {
                        System.out.println("Last Request was at "
                                + lastRequestTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }
                    long millisecondsToNexRequest = shopStatisticsService.getMillisecondsToNexRequest(shop);
//                    System.out.println("Milliseconds to next request left: " + millisecondsToNexRequest);
                    try {
                        Thread.sleep(millisecondsToNexRequest);
                    } catch (InterruptedException e) {

                    }
                    shopStatisticsService.updateLastRequestTimeForShop(shop);
                    Request request = (Request)messageConverter.fromMessage(message);
                    Query query = new Query(request.getId());
                    JsonParser jsonParser = JsonParserFactory.getJsonParser();
                    Map<String, Object> stringObjectMap = new HashMap<>();
                    try {
                        stringObjectMap = jsonParser.parseMap(request.getParam());
                    } catch (JsonParseException e) {

                    }
                    query.setParameters(stringObjectMap);
//                    System.out.println(query);
                    List<YMToken> YMTokens = shop.getYMTokens();
                    YMToken YMTokenSelected = null;
                    for(int i = YMTokens.size(); i-- > 0;) {
                        YMToken YMToken = YMTokens.get(i);
                        System.out.println(YMToken);
                        if (YMToken.isEnable()) {
                            YMTokenSelected = YMToken;
                            break;
                        }
                    }
                    if (YMTokenSelected == null) {
                        System.out.println("This shop don't have any enabled tokens");
                        return;
                    }
                    query.setShop(shop);
                    query.setYMToken(YMTokenSelected);
//                    System.out.println(request);
//                    method.setRequest(request);
                    RequestService requestService = new RequestService();
//                    System.out.println(method);
//                    String response = requestService.send(method);
//                    System.out.println("Got common request for method " + request.getMethod() + " with params " + request.getParam());
                    String methodClassName = "com.github.silexrr.yandex_market_api.yandexApi.method." + request.getMethod();
                    try {
                        //<Magic>
                        Class<?> methodAccessorGeneratorClass =
                                Class.forName(methodClassName);
                        Constructor<?> methodAccessorGeneratorConstructor =
                                methodAccessorGeneratorClass.getDeclaredConstructor();

                        methodAccessorGeneratorConstructor.setAccessible(true);
                        Method method =
                                (Method)methodAccessorGeneratorConstructor.newInstance();
                        //</Magic>

                        method.setQuery(query);

                        String YMResponse = requestService.send(method);

                        Response response = new Response(query, YMResponse, method.getMethodName(), request.getId());
                        responseService.save(response);
                        System.out.println("Response: " + response.getResponse());

//                        ResponseServiceImpl responseService = new ResponseServiceImpl();
//                        responseService.save(response1);
//                        System.out.println(new String(message.getBody()));
                    } catch(ClassNotFoundException | NoSuchMethodException e ) {
                        //my class isn't there!
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

//        container.addQueueNames(queueName);
        ArrayList<ShopMQListener> shopListeners = getShopListeners(shop);
        shopListeners.add(this);
        ArrayList<Queue> shopQueue = getShopQueue(shop);
        shopQueue.add(queue);
    }

    public static void addListener(Shop shop, RabbitMQConfig rabbitMQConfig, ResponseService responseService, ShopStatisticsService shopStatisticsService ) {
        ShopMQListener listener = getListener(shop);
        if (listener == null) {
            new ShopMQListener(shop, rabbitMQConfig, responseService, shopStatisticsService);
        }
    }

    public static ShopMQListener getListener(Shop shop) {
        ArrayList<ShopMQListener> shopListeners = getShopListeners(shop);
        for (int i = shopListeners.size(); --i >= 0;) {
            ShopMQListener shopMQListener = shopListeners.get(i);
            if (shopMQListener.getCommonKey() == commonKey) {
                return shopMQListener;
            }
        }
        return null;
    }

    public static ArrayList<ShopMQListener> getShopListeners(Shop shop) {
        String id = shop.getId();
//        System.out.println(id);
        if (shopMqListeners.containsKey(id) == false) {
            ArrayList<ShopMQListener> shopMQListeners = new ArrayList<ShopMQListener>();
            shopMqListeners.put(id, shopMQListeners);
        }
        return shopMqListeners.get(id);
    }

    public static ArrayList<Queue> getShopQueue(Shop shop) {
        String id = shop.getId();
//        System.out.println(id);
        if (shopQueue.containsKey(id) == false) {
            ArrayList<Queue> shopQueues = new ArrayList<Queue>();
            shopQueue.put(id, shopQueues);
        }
        return shopQueue.get(id);
    }

    public AbstractMessageListenerContainer getContainerCommon() {
        return containerCommon;
    }

    public AbstractMessageListenerContainer getContainerPrivate() {
        return containerPrivate;
    }

    public String getCommonKey() {
        return commonKey;
    }

    public String getPrivetKey() {
        return privetKey;
    }
}
