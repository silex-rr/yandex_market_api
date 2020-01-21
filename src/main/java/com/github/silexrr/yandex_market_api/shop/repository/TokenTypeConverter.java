package com.github.silexrr.yandex_market_api.shop.repository;
import com.github.silexrr.yandex_market_api.shop.model.TokenType;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class TokenTypeConverter implements Converter<String, TokenType> {
    public TokenType convert(String s) {
        return TokenType.valueOf(s);
    }
}

