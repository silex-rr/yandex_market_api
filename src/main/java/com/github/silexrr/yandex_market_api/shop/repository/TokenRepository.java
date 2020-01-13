package com.github.silexrr.yandex_market_api.shop.repository;

import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TokenRepository extends MongoRepository<Token, String> {
    public List<Token> findByShop(Shop shop);

    public Token findByOauthClientId(String oauthClientId);
    public Token findByOauthToken(String oauthToken);
}
