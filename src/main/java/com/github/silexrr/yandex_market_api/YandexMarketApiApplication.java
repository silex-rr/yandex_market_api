package com.github.silexrr.yandex_market_api;

import com.github.silexrr.yandex_market_api.api.service.RequestRestService;
import com.github.silexrr.yandex_market_api.shop.service.ShopService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class YandexMarketApiApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(YandexMarketApiApplication.class);
	}
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(YandexMarketApiApplication.class, args);
		RequestRestService requestRestService = context.getBean(RequestRestService.class);
		requestRestService.ping();
		ShopService shopService = context.getBean(ShopService.class);
		shopService.activateMqListeners();
	}


	@Bean(name="messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource
				= new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	/*
	 * Create LocaleResolver Bean
	 */
	@Bean
	public LocaleResolver localeResolver(){
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("en_US")); // your default locale
		return resolver;
	}

}