package com.github.silexrr.yandex_market_api.auth.model;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class User {

    @Id
    private final UUID id;
    private final String name;
    private final String login;
    private final String passwordHash;

    public User(UUID id, String name, String login, String passwordHash) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public User(String login, String name) {
        this(UUID.randomUUID(), name,  login, "");
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
