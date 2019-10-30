package com.github.silexrr.yandex_market_api.shop.repository;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ShopRepository extends MongoRepository<Shop, String> {
    public Shop findById(UUID id);

    public Shop findByName(String name);

    public Shop findByYmCompanyId(Integer YmCompanyId);

    public List<Shop> findByUserOwner(Object user);

    @Query("{'token.oauthToken': ?0}")
    public List<Shop> findByOauthToken(String token);

    @Query("{'token.oauthClientId': ?0}")
    public List<Shop> findByOauthClientId(String ClientId);

    @Query("{'token.moderators': ?0}")
    public List<Shop> findByModerators(User user);
}