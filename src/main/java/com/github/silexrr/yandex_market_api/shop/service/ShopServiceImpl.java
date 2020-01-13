package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopRepository shopRepository;


    @Override
    public void save(Shop shop) {
        shopRepository.save(shop);
    }

    @Override
    public Shop findByName(String name) {
        return shopRepository.findByName(name);
    }

    @Override
    public Shop findById(UUID uuid) {
        return shopRepository.findById(uuid);
    }

    @Override
    public void delete(Shop shop) {
        shopRepository.delete(shop);
    }
}
