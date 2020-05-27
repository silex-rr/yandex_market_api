package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.YMToken;

public interface YMTokenService {
    void save(YMToken YMToken);
    void delete(YMToken YMToken);

    YMToken findByOauthToken(String oauthToken);
    YMToken findByOauthClientId(String oauthClientId);
}
