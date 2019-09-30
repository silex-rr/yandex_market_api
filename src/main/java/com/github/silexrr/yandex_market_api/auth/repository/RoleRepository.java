package com.github.silexrr.yandex_market_api.auth.repository;

import com.github.silexrr.yandex_market_api.auth.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, Long> {
    public Role findByName(String s);
}
