package com.github.silexrr.yandex_market_api.auth.repository;

import com.github.silexrr.yandex_market_api.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findById(String id);

    public User findByName(String name);

    public User findByLogin(String login);
}
