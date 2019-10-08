package com.github.silexrr.yandex_market_api.auth.service;

import com.github.silexrr.yandex_market_api.auth.model.User;

public interface UserService {
    void save(User user);

    User findByLogin(String name);
}
