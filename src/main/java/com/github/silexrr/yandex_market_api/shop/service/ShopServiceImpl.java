package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.auth.service.SecurityServiceImpl;
import com.github.silexrr.yandex_market_api.config.RabbitMQConfig;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.YMToken;
import com.github.silexrr.yandex_market_api.shop.repository.ShopRepository;

import com.github.silexrr.yandex_market_api.yandexApi.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private ShopStatisticsService shopStatisticsService;

    @Autowired
    private ShopCache shopCache;

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);


    private void shopListCaching(List<Shop> shops) {
        ListIterator<Shop> shopListIterator = shops.listIterator();
        while (shopListIterator.hasNext()) {
            Shop shop = shopListIterator.next();
            Optional<Shop> shopOptional = shopCache.getShop(shop);
            if (shopOptional.isPresent()) {
                shopListIterator.set(shopOptional.get());
            } else {
                shopCache.addShop(shop);
            }
        }
    }

    private Optional<Shop> shopCaching(Optional<Shop> shopFromRepositoryOptional) {
        if (shopFromRepositoryOptional.isEmpty()) {
            return shopFromRepositoryOptional;
        }
        Shop shopFromRepository = shopFromRepositoryOptional.get();
        Optional<Shop> shopFromCacheOptional = shopCache.getShop(shopFromRepository);
        if(shopFromCacheOptional.isPresent()) {
            return shopFromCacheOptional;
        }
        shopCache.addShop(shopFromRepository);
        return shopFromRepositoryOptional;
    }

    @Override
    public void save(Shop shop) {
        shopRepository.save(shop);
        shopCache.addShop(shop);
        ArrayList<ShopMQListener> shopListeners = ShopMQListener.getShopListeners(shop);
        if (shopListeners.isEmpty()) {
            ShopMQListener.addListener(
                    shop,
                    rabbitMQConfig,
                    responseService,
                    shopStatisticsService
            );
        }
        shopListeners.forEach(listener -> {
            listener.setShop(shop);
        });
    }

    @Override
    public Optional<Shop> findByName(String name) {
        Optional<Shop> shopFromRepositoryOptional = shopRepository.findByName(name);
        return shopCaching(shopFromRepositoryOptional);
    }

    @Override
    public Optional<Shop> findById(UUID uuid) {
        Optional<Shop> shopFromRepositoryOptional = shopRepository.findById(uuid);
        return shopCaching(shopFromRepositoryOptional);
    }

    @Override
    public List<Shop> findByUserOwnersContains(User user) {
        List<Shop> shops = shopRepository.findByUserOwnersContains(user);
        shopListCaching(shops);
        return shops;
    }


    @Override
    public Optional<Shop> findById(String id) {
        Optional<Shop> shop = shopCache.getShop(id);
        if (shop.isPresent()) {
            return shop;
        }
        Optional<Shop> shopFromRepository = shopRepository.findById(id);
        if (shopFromRepository.isPresent()) {
            shopCache.addShop(shopFromRepository.get());
        }
        return shopFromRepository;
    }

    @Override
    public void delete(Shop shop) {
        shopCache.deleteShop(shop);
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
    public YMToken findTokenById(Shop shop, String tokenId) {
        List<YMToken> YMTokens = shop.getYMTokens();
        for (YMToken YMToken :
                YMTokens) {

            if(YMToken.getId().equals(tokenId)) {
                return YMToken;
            }
        }
        return null;
    }

    @Override
    public boolean removeToken(Shop shop, YMToken YMToken) {
        List<YMToken> YMTokens = shop.getYMTokens();
        if (YMTokens.contains(YMToken)) {
            YMTokens.remove(YMToken);
            shop.setYMTokens(YMTokens);
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
        logger.info("Activate MQ Listeners");

        List<Shop> shops = getEnable(true);
        shopListCaching(shops);
        shops.forEach(shop -> {
            ShopMQListener.addListener(
                    shop,
                    rabbitMQConfig,
                    responseService,
                    shopStatisticsService
            );
        });
    }
}
