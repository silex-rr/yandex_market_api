package com.github.silexrr.yandex_market_api.config;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = {"com.github.silexrr.yandex_market_api.repository"})
public class MongoDbConfig {

}
