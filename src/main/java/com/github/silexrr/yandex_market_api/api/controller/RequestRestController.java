package com.github.silexrr.yandex_market_api.api.controller;


import com.github.silexrr.yandex_market_api.api.model.APIResponse;
import com.github.silexrr.yandex_market_api.api.model.APIResponseStatus;
import com.github.silexrr.yandex_market_api.api.model.Request;
import com.github.silexrr.yandex_market_api.api.service.RequestRestService;
import com.github.silexrr.yandex_market_api.config.RabbitMQConfig;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.service.ShopMQListener;
import com.github.silexrr.yandex_market_api.shop.service.ShopService;
import com.github.silexrr.yandex_market_api.yandexApi.model.Response;
import com.github.silexrr.yandex_market_api.yandexApi.service.ResponseService;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import static org.springframework.amqp.rabbit.core.RabbitAdmin.QUEUE_NAME;

@RestController
@RequestMapping(value = "/rest/request", produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestRestController {
    @Autowired
    private RequestRestService requestRestService;
    @Autowired
    private ResponseService responseService;


    @GetMapping(value = "/add")
    public APIResponse add(
            @RequestParam("method") String method,
            @RequestParam("param") String param,
            @RequestParam("shop") Optional<String> shopOptional
    ) {
        Request request = new Request();
        request.setId(UUID.randomUUID().toString());
        request.setMethod(method);
        if (shopOptional.isEmpty() == false) {
            request.setShop(shopOptional.get());
        }
        request.setParam(param);
        requestRestService.add(request);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setResponseStatus(APIResponseStatus.DONE);
        apiResponse.setRequestId(request.getId());
        apiResponse.setQueueNumber(requestRestService.getQueueSize());
        return apiResponse;
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
