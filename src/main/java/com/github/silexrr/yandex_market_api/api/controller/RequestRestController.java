package com.github.silexrr.yandex_market_api.api.controller;

import com.github.silexrr.yandex_market_api.api.model.Request;
import com.github.silexrr.yandex_market_api.api.service.RequestRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/rest/request")
public class RequestRestController {
    @Autowired
    private  RequestRestService requestRestService;

    @GetMapping(value = "/add")
    public String add(
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
        return "Added_ID=" + request.getId();
    }
}
