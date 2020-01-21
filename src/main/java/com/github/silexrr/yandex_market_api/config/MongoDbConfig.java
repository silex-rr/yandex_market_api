package com.github.silexrr.yandex_market_api.config;

import com.github.silexrr.yandex_market_api.shop.repository.TokenTypeConverter;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@EnableMongoRepositories(
        basePackages = {"com.github.silexrr.yandex_market_api.auth.repository"})
public class MongoDbConfig {
    private final List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();

    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private MongoProperties mongoProperties;

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, mongoProperties.getDatabase());
        MappingMongoConverter mongoConverter = (MappingMongoConverter) mongoTemplate.getConverter();
        mongoConverter.setCustomConversions(mongoCustomConversions());
        mongoConverter.afterPropertiesSet();
        return mongoTemplate;
    }

    public MongoCustomConversions mongoCustomConversions() {
        converters.add(new TokenTypeConverter());
        return new MongoCustomConversions(converters);
    }

}
//@WritingConverter