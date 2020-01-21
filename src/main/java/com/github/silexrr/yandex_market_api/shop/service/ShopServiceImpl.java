package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.repository.ShopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public int countToken(Shop shop) {
        return 0;
//        List<Token> TokensByShop = tokenRepository.findByShop(shop);
//        return TokensByShop.size();
    }

    @Override
    public boolean userHasAccess(Shop shop, User user) {
        return shop.getUserOwners().contains(user);
    }

    @Override
    public boolean currentUserHasAccess(Shop shop) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        User user = (User)authentication.getPrincipal();
        return userHasAccess(shop, user);
    }
}
