package com.github.silexrr.yandex_market_api.auth.service;

import com.github.silexrr.yandex_market_api.auth.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    User findByLogin(String name);
    User findById(String id);
    List<User> findAll();
}
