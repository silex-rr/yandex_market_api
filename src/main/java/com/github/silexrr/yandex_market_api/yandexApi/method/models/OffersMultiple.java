package com.github.silexrr.yandex_market_api.yandexApi.method.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.silexrr.yandex_market_api.yandexApi.Method;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.QueryType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OffersMultiple extends Method {
    private String orderByPrice;
    private ArrayList<Integer> models;

    private static List<String> ordersValid = new ArrayList<String>(List.of("ASC", "DESC"));

    public OffersMultiple() {
        orderByPrice = "ASC";
    }

    @Override
    public void execute() {


        Map parameters = query.getParameters();
        if (parameters.containsKey("models") != false) {
            models = (ArrayList<Integer>) parameters.get("models");
        }

        if (models == null
            || models.size() == 0
        ) {
            return;
        }

        if (parameters.containsKey("orderByPrice") != false) {
            String orderByPrice = (String) parameters.get("orderByPrice");
            if (ordersValid.contains(orderByPrice)) {
                this.orderByPrice = orderByPrice;
            }
        }

        HashMap<String, ArrayList<Integer>> params = new HashMap<>();
        params.put("models", models);
//        BodyInserter<Object, ReactiveHttpOutputMessage> objectReactiveHttpOutputMessageBodyInserter = BodyInserters.fromValue(models);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode modelsNode = rootNode.putArray("models");
        models.forEach(model -> {
            modelsNode.add(model);
        });
        String json = "";
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            //
        }
        this.setBody(BodyInserters.fromValue(json));

        String uri = "v"
                + query.getVersion()
                + "/models/offers."
                + query.getQueryResponseFormat().getName()
                + "?regionId=" + query.getShop().getYmRegionId()
                + "&orderByPrice=" + orderByPrice;
        this.getQuery().setUrn(uri);
        this.getQuery().setType(QueryType.POST);
    }
}
