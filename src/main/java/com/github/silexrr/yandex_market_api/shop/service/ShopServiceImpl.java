package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.Token;
import com.github.silexrr.yandex_market_api.shop.repository.ShopRepository;
import com.github.silexrr.yandex_market_api.shop.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private TokenRepository tokenRepository;

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

    @Override
    public int countToken(Shop shop) {
        List<Token> TokensByShop = tokenRepository.findByShop(shop);
        return TokensByShop.size();
    }
}
