package com.github.silexrr.yandex_market_api.api.repository;

import com.github.silexrr.yandex_market_api.api.model.APIToken;
import com.github.silexrr.yandex_market_api.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface APITokenRepository extends MongoRepository<APIToken, String> {
    public APIToken findById(UUID uid);
    public List<APIToken> findByUser(User user);
    public APIToken findByToken(String token);
}
