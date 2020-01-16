package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;

import java.util.UUID;

public interface ShopService {
    void save(Shop shop);
    void delete(Shop shop);
    Shop findByName(String name);
    Shop findById(UUID id);
    int countToken(Shop shop);

    boolean userHasAccess(Shop shop, User user);
    boolean currentUserHasAccess(Shop shop);
}
