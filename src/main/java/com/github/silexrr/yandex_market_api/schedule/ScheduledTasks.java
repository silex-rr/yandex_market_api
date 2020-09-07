package com.github.silexrr.yandex_market_api.schedule;

import com.github.silexrr.yandex_market_api.api.service.APIStatistic;
import com.github.silexrr.yandex_market_api.yandexApi.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private ResponseService responseService;

    @Autowired
    private APIStatistic apiStatistic;

    @Scheduled(fixedRate = 1800000, initialDelay = 1800000)
    public void apiResponseCleaner() {
        responseService.deleteAllByDeliveredIsTrue();
    }
    @Scheduled(fixedRate = 3000, initialDelay = 3000)
    public void AMQQueueUpdate() { apiStatistic.statisticUpdate(); }
}
