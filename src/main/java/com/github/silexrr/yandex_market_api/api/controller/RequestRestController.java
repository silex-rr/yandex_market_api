package com.github.silexrr.yandex_market_api.api.controller;


import com.github.silexrr.yandex_market_api.api.model.APIResponse;
import com.github.silexrr.yandex_market_api.api.model.APIResponseStatus;
import com.github.silexrr.yandex_market_api.api.model.Request;
import com.github.silexrr.yandex_market_api.api.service.RequestRestService;
import com.github.silexrr.yandex_market_api.yandexApi.model.Response;
import com.github.silexrr.yandex_market_api.yandexApi.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

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
            @RequestParam("shop") String shop
    ) {
        Request request = new Request();
        request.setId(UUID.randomUUID().toString());
        request.setMethod(method);
        request.setShop(shop);
        request.setParam(param);
        requestRestService.add(request);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setResponseStatus(APIResponseStatus.DONE);
        apiResponse.setRequestId(request.getId());

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
