package com.github.silexrr.yandex_market_api.shop.model;

public enum TokenType {
    BEARER ("Bearer");

    private final String name;

    TokenType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name();
    }
}
