package com.github.silexrr.yandex_market_api.yandexApi.method.models;

import com.github.silexrr.yandex_market_api.yandexApi.Method;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.QueryType;

///rest/request/add?method=models.Offers&shop=5e54db6cee0efe0494c8afa0&param=%7B%22modelId%22%3A7352155%7D
public class Offers extends Method {
    private String orderByPrice;
    private Integer count;

    public Offers() {
        super();
        count = 30;
        orderByPrice = "ASC";
    }

    @Override
    public void execute() {
        if(query == null) {
            return;
        }
        if (query.getParameters().containsKey("modelId") == false) {
            return;
        }
        String modelId = ((Integer)query.getParameters().get("modelId")).toString();
        if (modelId.isEmpty()) {
            return;
        }
        if (query.getParameters().containsKey("count") != false) {
            count = (Integer)query.getParameters().get("count");
        }

        if (query.getParameters().containsKey("orderByPrice") != false) {
            orderByPrice = (String)query.getParameters().get("orderByPrice");
        }

        String uri = "v"
                + query.getVersion()
                + "/models/"
                + modelId
                + "/offers."
                + query.getQueryResponseFormat().getName()
                + "?regionId=" + query.getShop().getYmRegionId()
                + "&count=" + count
                + "&orderByPrice=" + orderByPrice;
        this.getQuery().setUrn(uri);
        this.getQuery().setType(QueryType.GET);
    }
}
