package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Token;

public interface TokenService {
    void save(Token token);
    void delete(Token token);

    Token findByOauthToken(String oauthToken);
    Token findByOauthClientId(String oauthClientId);
}
