package com.github.silexrr.yandex_market_api.schedule;

import com.github.silexrr.yandex_market_api.yandexApi.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private ResponseService responseService;

    @Scheduled(fixedRate = 1800000, initialDelay = 1800000)
    public void apiResponseCleaner() {
        responseService.deleteAllByDeliveredIsTrue();
    }
}
