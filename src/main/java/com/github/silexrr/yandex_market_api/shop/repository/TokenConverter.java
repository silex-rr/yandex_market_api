package com.github.silexrr.yandex_market_api.shop.repository;
import com.github.silexrr.yandex_market_api.shop.model.YMTokenType;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

public class TokenConverter {

    @ReadingConverter
    public static class TokenTypeConverter implements Converter<String, YMTokenType> {

        @Override
        public YMTokenType convert(final String source) {
            return YMTokenType.valueOf(source);
        }
    }
}

