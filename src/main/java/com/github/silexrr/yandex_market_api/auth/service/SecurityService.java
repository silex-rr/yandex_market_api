package com.github.silexrr.yandex_market_api.auth.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String login, String password);
}