package com.github.silexrr.yandex_market_api.yandexApi;

import com.github.silexrr.yandex_market_api.yandexApi.request.model.Request;

public abstract class Method {
    private final Request request;

    public Method(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }
}
