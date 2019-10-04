package com.github.silexrr.yandex_market_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class YandexMarketApiApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(YandexMarketApiApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(YandexMarketApiApplication.class, args);
	}

}
