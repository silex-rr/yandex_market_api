package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.YMToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShopService {
    void save(Shop shop);
    void delete(Shop shop);
    Shop findByName(String name);
    Shop findById(UUID id);
    Optional<Shop> findById(String id);
    int countToken(Shop shop);
    List<Shop> getEnable(boolean enable);

    boolean userHasAccess(Shop shop, User user);
    boolean currentUserHasAccess(Shop shop);
    YMToken findTokenById(Shop shop, String tokenId);
    boolean removeToken(Shop shop, YMToken YMToken);
    void activateMqListeners();
}
