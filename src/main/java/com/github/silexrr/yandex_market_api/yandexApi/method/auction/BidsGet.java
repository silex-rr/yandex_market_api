package com.github.silexrr.yandex_market_api.yandexApi.method.auction;

import com.github.silexrr.yandex_market_api.yandexApi.Method;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.Query;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.QueryType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Map;

/**
 * Request information about Bid for shops goods
 * https://yandex.ru/dev/market/partner/doc/dg/reference/post-campaigns-id-auction-bids-docpage/
 * Params
 *   offers - String contains json like a "{"offers": "{\"offers\":[{\"feedId\": 666,\"offerId\": 666999666}]}"}"
 */
public class BidsGet extends Method {

    @Override
    public void execute() {
        Query query = getQuery();
        Integer ymCompanyId = query.getShop().getYmCompanyId();
        Map parameters = query.getParameters();

        if (!parameters.containsKey("offers")) {
            return;
        }

        Object offersJson = parameters.get("offers");

        BodyInserter<Object, ReactiveHttpOutputMessage> objectReactiveHttpOutputMessageBodyInserter = BodyInserters.fromValue(offersJson);
        this.setBody(BodyInserters.fromValue(offersJson));

        String uri = "v"
                + this.query.getVersion()
                + "/campaigns/"
                + ymCompanyId
                + "/auction/"
                + "/bids."
                + this.query.getQueryResponseFormat().getName()
                + "?regionId=" + this.query.getShop().getYmRegionId();

        query.setUrn(uri);
        query.setType(QueryType.POST);
    }
}
