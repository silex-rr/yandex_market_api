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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

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
    public Optional<Shop> findById(String id) {
        return shopRepository.findById(id);
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

        getEnable(true).forEach(shop -> {
            ShopMQListener.addListener(
                    shop,
                    rabbitMQConfig,
                    responseService,
                    shopStatisticsService
            );
        });
    }
}
