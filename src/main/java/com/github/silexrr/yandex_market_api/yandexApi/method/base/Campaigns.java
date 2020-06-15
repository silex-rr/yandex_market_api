package com.github.silexrr.yandex_market_api.yandexApi.method.base;

import com.github.silexrr.yandex_market_api.yandexApi.Method;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.QueryType;

/**
 * Request information about campaigns
 */
public class Campaigns extends Method {
    @Override
    public void execute() {
        if (query == null) {
            return;
        }

        String uri = "v"
                + query.getVersion()
                + "/campaigns."
                + query.getQueryResponseFormat().getName();
        this.getQuery().setUrn(uri);
        this.getQuery().setType(QueryType.GET);
    }

}
