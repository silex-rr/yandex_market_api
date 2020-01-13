package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Token;
import com.github.silexrr.yandex_market_api.shop.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void save(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public void delete(Token token) {
        tokenRepository.delete(token);
    }

    @Override
    public Token findByOauthToken(String oauthToken) {
        return tokenRepository.findByOauthToken(oauthToken);
    }

    @Override
    public Token findByOauthClientId(String oauthClientId) {
        return tokenRepository.findByOauthClientId(oauthClientId);
    }

}
