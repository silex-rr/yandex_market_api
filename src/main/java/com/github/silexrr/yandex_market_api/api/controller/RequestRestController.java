package com.github.silexrr.yandex_market_api.api.controller;


import com.github.silexrr.yandex_market_api.api.model.APIResponse;
import com.github.silexrr.yandex_market_api.api.model.APIResponseStatus;
import com.github.silexrr.yandex_market_api.api.model.Request;
import com.github.silexrr.yandex_market_api.api.service.RequestRestService;
import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.config.RabbitMQConfig;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.YMToken;
import com.github.silexrr.yandex_market_api.shop.service.ShopMQListener;
import com.github.silexrr.yandex_market_api.shop.service.ShopService;
import com.github.silexrr.yandex_market_api.yandexApi.model.Response;
import com.github.silexrr.yandex_market_api.yandexApi.service.ResponseService;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.amqp.rabbit.core.RabbitAdmin.QUEUE_NAME;

@RestController
@RequestMapping(value = "/rest/request", produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestRestController {
    @Autowired
    private RequestRestService requestRestService;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private ShopService shopService;

    @PutMapping(value = "/add")
    public APIResponse add(
            @RequestParam("method") String method,
            @RequestParam("param") String param,
            @RequestParam("shop") Optional<String> shopOptional
    ) {
        Request request = new Request();
        request.setId(UUID.randomUUID().toString());
        request.setMethod(method);
        if (!shopOptional.isEmpty()) {
            request.setShop(shopOptional.get());
        }
        request.setParam(param);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            User principal = (User)authentication.getPrincipal();
            request.setUserId(principal.getId());
        }
        requestRestService.add(request);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setResponseStatus(APIResponseStatus.DONE);
        apiResponse.setRequestId(request.getId());
        apiResponse.setQueueNumber(requestRestService.getQueueSize());
        return apiResponse;
    }

    @GetMapping(value = "/getAllMyResponse")
    public List<Response> getAllMyResponse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Response> responses = new ArrayList<>();
        if(authentication != null) {
            User user = (User) authentication.getPrincipal();
            responses = responseService.findAllByUserId(user.getId());
        }
        return responses;
    }

    @GetMapping(value = "/getShops")
    public List<Shop> getShops() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Shop> shops = new ArrayList<>();
        if (authentication != null) {
            User principal = (User)authentication.getPrincipal();
            shops = shopService.findByUserOwnersContains(principal);
        }
//        shops.forEach(shop -> {
//            shop.setYMTokens(new ArrayList<>());
//            shop.setUserOwners(new ArrayList<>());
//        });
        return shops;
    }

    @GetMapping(value = "/getByRequestId")
    public APIResponse get(
            @RequestParam("requestId") String requestId
    ) {
        Optional<Response> byRequestId = responseService.findByRequestId(requestId);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setRequestId(requestId);
        if (byRequestId.isEmpty()) {
            apiResponse.setResponseStatus(APIResponseStatus.IN_PROCESS);
            return apiResponse;
        }
        apiResponse.setResponseStatus(APIResponseStatus.DONE);
        Response response = byRequestId.get();
        apiResponse.setBody(response.getResponse().replaceAll("\\\"", ""));
        return apiResponse;
    }
}
