package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Token;
import com.github.silexrr.yandex_market_api.yandexApi.method.base.Campaigns;
import com.github.silexrr.yandex_market_api.yandexApi.model.Response;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.Request;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.yandexApi.Method;
import com.github.silexrr.yandex_market_api.yandexApi.request.service.RequestService;
import com.github.silexrr.yandex_market_api.yandexApi.service.ResponseService;
import com.github.silexrr.yandex_market_api.yandexApi.service.ResponseServiceImpl;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;

import java.util.*;

import static com.github.silexrr.yandex_market_api.config.RabbitMQConfig.startListening;

public class ShopMQListener {

    static private HashMap<String, ArrayList<ShopMQListener>> shopMqListeners = new HashMap<String, ArrayList<ShopMQListener>>();

    private AbstractMessageListenerContainer container;
    private String key;

//    @Autowired
//    private RabbitAdmin rabbitAdmin;

    private ShopMQListener (Shop shop, Method method, String key, RabbitAdmin rabbitAdmin) {
        String methodName = method.getMethodName();
        System.out.println("Add listener " + key + " for shop " + shop.getName() + "(" + shop.getId() + ")");
        this.key = key;
        container = startListening(
                rabbitAdmin,
                rabbitAdmin.declareQueue(),
                new DirectExchange("request.exchange"),
                this.key,
                message -> {
                    UUID uuid = UUID.randomUUID();
                    Request request = new Request(uuid.toString());
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
                    request.setShop(shop);
                    request.setToken(tokenSelected);
//                    System.out.println(request);
                    method.setRequest(request);
                    RequestService requestService = new RequestService();
                    System.out.println(method);
                    String response = requestService.send(method);
                    System.out.println(response);
//                    Response response1 = new Response(request, response, method.getMethodName());
//                    ResponseServiceImpl responseService = new ResponseServiceImpl();
//                    responseService.save(response1);

//                    System.out.println(new String(message.getBody()));
                });
//        container.addQueueNames(queueName);
        ArrayList<ShopMQListener> shopListeners = getShopListeners(shop);
        shopListeners.add(this);
    }

    public static void addListener(Shop shop, Method method, Boolean chainedToShop, RabbitAdmin rabbitAdmin ) {
        String key = makeKey(method, shop, chainedToShop);
        ShopMQListener listener = getListener(shop, key);
        if (listener == null) {
            new ShopMQListener(shop, method, key, rabbitAdmin);
        }
    }

    public static String makeKey(Method method, Shop shop, Boolean chainedToShop) {
        String methodName = method.getMethodName();
        String key = methodName;
        if (chainedToShop) {
            key += '.' + shop.getId();
        }
        return key;
    }

    public static ShopMQListener getListener(Shop shop, String key) {
        ArrayList<ShopMQListener> shopListeners = getShopListeners(shop);
        for (int i = shopListeners.size(); --i >= 0;) {
            ShopMQListener shopMQListener = shopListeners.get(i);
            if (shopMQListener.getKey() == key) {
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

    public AbstractMessageListenerContainer getContainer() {
        return container;
    }

    public void setContainer(AbstractMessageListenerContainer container) {
        this.container = container;
    }

    public String getKey() {
        return key;
    }
}
