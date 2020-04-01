package com.github.silexrr.yandex_market_api.yandexApi.request.model;

public enum QueryResponseFormat {
    XML ("xml"), JSON ("json");

    private final String name;

    QueryResponseFormat(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name();
    }
}
