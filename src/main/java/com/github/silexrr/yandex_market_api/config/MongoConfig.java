package com.github.silexrr.yandex_market_api.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;


@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.uri}")
    private String uri;


    @Bean
    public MongoTemplate mongoTemplate() {

        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(this.mongoClient(), this.database));
    }


    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(this.uri);
    }

}