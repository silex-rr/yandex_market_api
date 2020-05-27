package com.github.silexrr.yandex_market_api.shop.model;

public enum YMTokenType {
    BEARER ("Bearer");

    private final String name;

    YMTokenType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
