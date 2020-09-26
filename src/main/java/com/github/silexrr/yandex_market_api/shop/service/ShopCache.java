package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Shop;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Optional;

@Configuration
@Scope("singleton")
public class ShopCache {
    private HashMap<String, Shop> shops;

    public ShopCache (){
        shops = new HashMap<>();
    }

    public void dropAllCache() {
        this.shops = new HashMap<>();
    }

    public void addShop(Shop shop) {
        String id = shop.getId();
//        if (shops.containsKey(id)) {
//            Shop shopCached = shops.get(id);
//            if (shopCached != shop) {
//                shopCached = shop;
//                shops.put(id, shopCached);
//            }
//            return;
//        }
        shops.put(id, shop);
    }

    public Optional<Shop> getShop(Shop shop) {
        return getShop(shop.getId());
    }

    public Optional<Shop> getShop(String id) {
        Shop shop = null;
        if (shops.containsKey(id)) {
            shop = shops.get(id);
        }
        Optional<Shop> shopOptional = Optional.ofNullable(shop);
        return shopOptional;
    }

    public void deleteShop(Shop shop) {
        deleteShop(shop.getId());
    }

    public void deleteShop(String id) {
        if (shops.containsKey(id)) {
            shops.remove(id);
        }
    }
}
