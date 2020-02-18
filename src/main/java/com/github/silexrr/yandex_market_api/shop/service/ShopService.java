package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.Token;

import java.util.UUID;

public interface ShopService {
    void save(Shop shop);
    void delete(Shop shop);
    Shop findByName(String name);
    Shop findById(UUID id);
    int countToken(Shop shop);

    boolean userHasAccess(Shop shop, User user);
    boolean currentUserHasAccess(Shop shop);
    Token findTokenById(Shop shop, String tokenId);
    boolean removeToken(Shop shop, Token token);
}
