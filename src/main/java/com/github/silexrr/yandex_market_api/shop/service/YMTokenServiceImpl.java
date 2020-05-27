package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.YMToken;
import org.springframework.stereotype.Service;

@Service
public class YMTokenServiceImpl implements YMTokenService {
    @Override
    public void save(YMToken YMToken) {

    }

    @Override
    public void delete(YMToken YMToken) {

    }

    @Override
    public YMToken findByOauthToken(String oauthToken) {
        return null;
    }

    @Override
    public YMToken findByOauthClientId(String oauthClientId) {
        return null;
    }

    //    @Autowired
//    private TokenRepository tokenRepository;
//
//    @Override
//    public void save(Token token) {
//        tokenRepository.save(token);
//    }
//
//    @Override
//    public void delete(Token token) {
//        tokenRepository.delete(token);
//    }
//
//    @Override
//    public Token findByOauthToken(String oauthToken) {
//        return tokenRepository.findByOauthToken(oauthToken);
//    }
//
//    @Override
//    public Token findByOauthClientId(String oauthClientId) {
//        return tokenRepository.findByOauthClientId(oauthClientId);
//    }

}
