package com.github.silexrr.yandex_market_api.api.service;


import com.github.silexrr.yandex_market_api.api.model.APIToken;
import com.github.silexrr.yandex_market_api.auth.model.User;

import java.util.List;
import java.util.UUID;

public interface APITokenService {
    public void save(APIToken apiToken);
    public void delete(APIToken apiToken);
    public APIToken findById(UUID uid);
    public List<APIToken> findByUser(User user);
    public APIToken findByToken(String token);
}
