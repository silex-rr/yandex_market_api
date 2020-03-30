package com.github.silexrr.yandex_market_api.yandexApi.method.base;

import com.github.silexrr.yandex_market_api.yandexApi.Method;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.Request;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.RequestType;

public class Campaigns extends Method {
    @Override
    public void execute() {
        if (request == null) {
            return;
        }

        String uri = "v"
                + request.getVersion()
                + "/campaigns."
                + request.getRequestResponseFormat().getName();
        this.getRequest().setUrn(uri);
        this.getRequest().setType(RequestType.GET);
    }

}
