package com.github.silexrr.yandex_market_api.api.model;

public enum APIResponseStatus {
    DONE ("done"),
    IN_PROCESS ("In process"),
    ERROR ("error");

    private final String name;

    APIResponseStatus(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
