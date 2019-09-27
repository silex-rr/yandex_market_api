package com.github.silexrr.yandex_market_api.auth.model;

import org.springframework.data.annotation.Id;

public class Role {

    @Id
    private Integer id;
    private String name;

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
