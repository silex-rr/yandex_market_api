package com.github.silexrr.yandex_market_api.yandexApi.request.model;

public enum RequestResponseFormat {
    XML ("xml"), JSON ("json");

    private final String name;

    RequestResponseFormat (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name();
    }
}
