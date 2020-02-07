package com.github.silexrr.yandex_market_api.config;
import static java.util.Arrays.asList;

import com.github.silexrr.yandex_market_api.shop.model.TokenType;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoClientSettings settings() {

        return MongoClientSettings.builder()
                .codecRegistry(CodecRegistries.fromRegistries(
                        CodecRegistries.fromProviders(new ClassCodecProvider()),
                        MongoClient.getDefaultCodecRegistry()))
                .build();
    }

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(asList(
                new StringToClassConverter(),
                new TokenTypeEnumMongoConverter()
        ));
    }

    private static class ClassCodec implements Codec<Class> {

        @Override
        public Class decode(BsonReader reader, DecoderContext decoderContext) {
            try {
                return Class.forName(reader.readString());
            } catch (ClassNotFoundException e) {
                throw new MongoException("Couldn't read value as class type", e);
            }
        }

        @Override
        public void encode(BsonWriter writer, Class value, EncoderContext encoderContext) {
            writer.writeString(value.getName());
        }

        @Override
        public Class<Class> getEncoderClass() {
            return Class.class;
        }
    }

    private static class ClassCodecProvider implements CodecProvider {

        @Override
        public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
            if (clazz == Class.class) {
                return (Codec<T>) new ClassCodec();
            }
            return null;
        }
    }

    @ReadingConverter
    private static class StringToClassConverter implements Converter<String, Class> {

        @Override
        public Class convert(String source) {
            try {
                return Class.forName(source);
            } catch (ClassNotFoundException e) {
                throw new MongoException("Couldn't read string as class type", e);
            }

        }
    }

    @ReadingConverter
    public class TokenTypeEnumMongoConverter implements Converter<String, TokenType> {

        @Override
        public TokenType convert(String source) {

            for (TokenType TokenType : TokenType.values()) {
                if (TokenType.name().equals(source))
                    return TokenType;
            }

            return null;
        }
    }
}