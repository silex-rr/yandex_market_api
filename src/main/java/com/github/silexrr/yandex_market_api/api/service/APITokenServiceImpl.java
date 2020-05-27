package com.github.silexrr.yandex_market_api.api.service;

import com.github.silexrr.yandex_market_api.api.model.APIToken;
import com.github.silexrr.yandex_market_api.api.repository.APITokenRepository;
import com.github.silexrr.yandex_market_api.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class APITokenServiceImpl implements APITokenService {

    @Autowired
    private APITokenRepository apiTokenRepository;

    @Override
    public void save(APIToken apiToken) {
        apiTokenRepository.save(apiToken);
    }

    @Override
    public void delete(APIToken apiToken) {
        apiTokenRepository.delete(apiToken);
    }

    @Override
    public APIToken findById(UUID uid) {
        return apiTokenRepository.findById(uid);
    }

    @Override
    public List<APIToken> findByUser(User user) {
        return apiTokenRepository.findByUser(user);
    }

    @Override
    public APIToken findByToken(String token) {
        return apiTokenRepository.findByToken(token);
    }
}
