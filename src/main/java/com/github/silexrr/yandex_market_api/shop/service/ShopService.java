package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Shop;

public interface ShopService {
    void save(Shop shop);
    Shop findByName(String name);
}
