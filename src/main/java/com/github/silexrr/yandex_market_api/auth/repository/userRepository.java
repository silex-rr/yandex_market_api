package com.github.silexrr.yandex_market_api.auth.repository;

import com.github.silexrr.yandex_market_api.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface userRepository extends MongoRepository<User, String> {
    public User findById(UUID id);
}
