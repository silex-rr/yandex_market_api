package com.github.silexrr.yandex_market_api.shop.service;

import com.github.silexrr.yandex_market_api.shop.model.Shop;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ShopStatisticsService {
    private HashMap<String, LocalDateTime> lastRequestByShop;

    public ShopStatisticsService() {
        this.lastRequestByShop = new HashMap<String, LocalDateTime>();
    }

    public void updateLastRequestTimeForShop(Shop shop) {
        String id = shop.getId();
        lastRequestByShop.put(id, LocalDateTime.now());
    }

    public LocalDateTime getLastRequestTime(Shop shop) {
        String id = shop.getId();
        if (lastRequestByShop.containsKey(id)) {
            return lastRequestByShop.get(id);
        }
        return null;
    }

    public LocalDateTime getNextRequestDateTime(Shop shop) {
        LocalDateTime lastRequestTimeForShop = getLastRequestTime(shop);
        if (lastRequestTimeForShop == null) {
            return LocalDateTime.now().minusSeconds(1);
        }
        long requestTimeStep = (long)Math.ceil((1 / shop.getRequestsPerSecond()) * 1000000000);
//        System.out.println("Request rate is: " + requestTimeStep + " nanos");
        LocalDateTime nextRequest = lastRequestTimeForShop.plusNanos(requestTimeStep);
//        System.out.println("Next request is possible at " + lastRequestTimeForShop.format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss")));
        return nextRequest;
    }

    public long getMillisecondsToNexRequest(Shop shop) {

        LocalDateTime nextRequestDateTime = getNextRequestDateTime(shop);

        LocalDateTime now = LocalDateTime.now();
        if(nextRequestDateTime.isBefore(now)) {
            return 0;
        }
        return (long)Math.ceil(
                now.until(nextRequestDateTime, ChronoUnit.MILLIS)
        );
    }
}
