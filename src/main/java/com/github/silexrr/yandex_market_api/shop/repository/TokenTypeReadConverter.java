package com.github.silexrr.yandex_market_api.shop.repository;
import com.github.silexrr.yandex_market_api.shop.model.TokenType;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class TokenTypeReadConverter implements Converter<String, TokenType> {
    public TokenType convert(String s) {
        TokenType tokenType = TokenType.valueOf(s);
        return tokenType;
    }
}

