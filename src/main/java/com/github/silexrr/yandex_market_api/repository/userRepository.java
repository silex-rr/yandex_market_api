package com.github.silexrr.yandex_market_api.repository;

import com.github.silexrr.yandex_market_api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface userRepository extends MongoRepository<User, String> {
    User findById(UUID id);
}
