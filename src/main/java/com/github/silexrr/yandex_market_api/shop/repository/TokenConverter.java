package com.github.silexrr.yandex_market_api.shop.repository;
import com.github.silexrr.yandex_market_api.shop.model.TokenType;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

public class TokenConverter {

    @ReadingConverter
    public static class TokenTypeConverter implements Converter<String, TokenType> {

        @Override
        public TokenType convert(final String source) {
            return TokenType.valueOf(source);
        }
    }
}

