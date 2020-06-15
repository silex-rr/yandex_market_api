package com.github.silexrr.yandex_market_api.yandexApi.method.models;

import com.github.silexrr.yandex_market_api.yandexApi.Method;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.QueryType;

/**
 * Search the goods model in Yandex Market
 * https://yandex.ru/dev/market/partner/doc/dg/reference/get-models-docpage/
 * Params
 *   searchQuery - Set request string
 *   pageSize [1, 50] - Set the number of returned results
 */
public class Search extends Method {
    private String searchQuery;
    private Integer pageSize;

    public Search() {
        super();
        pageSize = 50;
    }

    @Override
    public void execute() {
        if(query == null) {
            return;
        }

        if (query.getParameters().containsKey("searchQuery") != false) {
            searchQuery = (String)query.getParameters().get("searchQuery");
        }

        if(searchQuery == null) {
            return;
        }

        if (query.getParameters().containsKey("pageSize") != false) {
            pageSize = (Integer)query.getParameters().get("pageSize");
        }

        String uri = "v"
                + query.getVersion()
                + "/models."
                + query.getQueryResponseFormat().getName()
                + "?regionId=" + query.getShop().getYmRegionId()
                + "&pageSize=" + pageSize
                + "&query=" + searchQuery;
        this.getQuery().setUrn(uri);
        this.getQuery().setType(QueryType.GET);

    }
}
