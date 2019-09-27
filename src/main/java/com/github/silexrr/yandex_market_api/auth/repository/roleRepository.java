package com.github.silexrr.yandex_market_api.auth.UserController;

import com.github.silexrr.yandex_market_api.auth.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface roleRepository extends MongoRepository<Role, String> {
    public Role findById(Integer id);
}
