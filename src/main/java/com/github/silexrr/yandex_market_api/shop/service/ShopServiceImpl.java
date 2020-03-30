package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.Token;
import com.github.silexrr.yandex_market_api.shop.repository.ShopRepository;

import com.github.silexrr.yandex_market_api.yandexApi.method.base.Campaigns;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private RabbitAdmin rabbitAdmin;

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

    @Override
    public Token findTokenById(Shop shop, String tokenId) {
        List<Token> tokens = shop.getTokens();
        for (Token token:
             tokens) {

            if(token.getId().equals(tokenId)) {
                return token;
            }
        }
        return null;
    }

    @Override
    public boolean removeToken(Shop shop, Token token) {
        List<Token> tokens = shop.getTokens();
        if (tokens.contains(token)) {
            tokens.remove(token);
            shop.setTokens(tokens);
            return true;
        }
        return false;
    }

    @Override
    public List<Shop> getEnable(boolean enable) {
        return shopRepository.findAllByEnable(enable);
    }

    @Override
    public void activateMqListeners()
    {
        System.out.println("Activate MQ Listeners");
        getEnable(true).forEach(shop -> {
            Campaigns campaigns = new Campaigns();
            ShopMQListener.addListener(
                    shop,
                    campaigns,
                    true,
                    rabbitAdmin
            );
        });
    }
}
