package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.api.model.Request;
import com.github.silexrr.yandex_market_api.config.RabbitMQConfig;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.Token;
import com.github.silexrr.yandex_market_api.yandexApi.Method;
import com.github.silexrr.yandex_market_api.yandexApi.model.Response;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.Query;
import com.github.silexrr.yandex_market_api.yandexApi.request.service.RequestService;
import com.github.silexrr.yandex_market_api.yandexApi.service.ResponseServiceImpl;
import jdk.nashorn.api.tree.ImportEntryTree;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.github.silexrr.yandex_market_api.config.RabbitMQConfig.startListening;

public class ShopMQListener {

    static private HashMap<String, ArrayList<ShopMQListener>> shopMqListeners = new HashMap<String, ArrayList<ShopMQListener>>();

    private AbstractMessageListenerContainer containerCommon;
    private AbstractMessageListenerContainer containerPrivate;
    private String exchange;
    static private String commonKey;
    private String privetKeyBase;
    private String privetKey;
    private RabbitAdmin rabbitAdmin;
    private MessageConverter messageConverter;

    private ShopMQListener (Shop shop, RabbitMQConfig rabbitMQConfig) {
        rabbitAdmin = rabbitMQConfig.rabbitAdmin();
        exchange = rabbitMQConfig.getExchange();
        commonKey = rabbitMQConfig.getCommonRoutingKey();
        privetKeyBase = rabbitMQConfig.getPrivetRoutingKeyBase();
        messageConverter = rabbitMQConfig.jsonMessageConverter();
        System.out.println("Add listeners for shop " + shop.getName() + "(" + shop.getId() + ")");
        this.privetKey = privetKeyBase + "." + shop.getId();
        System.out.println("Add one for exchange " + exchange + " with key " + commonKey);
        ArrayList<String> keys = new ArrayList<>();
        keys.add(commonKey);
        keys.add(privetKey);
        containerCommon = startListening(
                rabbitAdmin,
                rabbitAdmin.declareQueue(),
                new DirectExchange(exchange),
                keys,
                message -> {
                    Request request = (Request)messageConverter.fromMessage(message);
                    Query query = new Query(request.getId());
                    JsonParser jsonParser = JsonParserFactory.getJsonParser();
                    Map<String, Object> stringObjectMap = new HashMap<>();
                    try {
                        stringObjectMap = jsonParser.parseMap(request.getParam());
                    } catch (JsonParseException e) {

                    }
                    query.setParameters(stringObjectMap);
                    System.out.println(query);
                    List<Token> tokens = shop.getTokens();
                    Token tokenSelected = null;
                    for(int i = tokens.size(); i-- > 0;) {
                        Token token = tokens.get(i);
                        System.out.println(token);
                        if (token.isEnable()) {
                            tokenSelected = token;
                            break;
                        }
                    }
                    if (tokenSelected == null) {
                        System.out.println("This shop don't have any enable tokens");
                        return;
                    }
                    query.setShop(shop);
                    query.setToken(tokenSelected);
//                    System.out.println(request);
//                    method.setRequest(request);
                    RequestService requestService = new RequestService();
//                    System.out.println(method);
//                    String response = requestService.send(method);
                    System.out.println("Got common request for method " + request.getMethod() + " with params " + request.getParam());
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

                        String response = requestService.send(method);

                        Response response1 = new Response(query, response, method.getMethodName());
                        System.out.println("Response: " + response1.getResponse());

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
    }

    public static void addListener(Shop shop, RabbitMQConfig rabbitMQConfig ) {
        ShopMQListener listener = getListener(shop);
        if (listener == null) {
            new ShopMQListener(shop, rabbitMQConfig);
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
