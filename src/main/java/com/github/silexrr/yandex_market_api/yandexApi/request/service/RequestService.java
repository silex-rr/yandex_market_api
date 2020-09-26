package com.github.silexrr.yandex_market_api.yandexApi.request.service;

import com.github.silexrr.yandex_market_api.shop.model.YMToken;
import com.github.silexrr.yandex_market_api.yandexApi.Method;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class RequestService {

    public String send(Method method)
    {
        method.execute();

        Query query = method.getQuery();

        WebClient webClient = WebClient.create();
        HttpMethod type = HttpMethod.GET;
        switch (query.getType()) {
            case GET:
                type = HttpMethod.GET;
                break;
            case PUT:
                type = HttpMethod.PUT;
                break;
            case POST:
                type = HttpMethod.POST;
                break;
            case DELETE:
                type = HttpMethod.DELETE;
                break;
        }
        String uri = this.makeURI(query);
        YMToken YMToken = query.getYMToken();

        String OAuthString = "OAuth oauth_token=\"" + YMToken.getOauthToken()
                + "\", oauth_client_id=\"" + YMToken.getOauthClientId() + "\"";

        String block = "";
        try {
            block = webClient
                    .method(type)
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, OAuthString)
                    .body(method.getBody())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.out.println(e.getRequest().getHeaders().toString());
            System.out.println(e.getRequest().getURI());
            System.out.println(e.getHeaders());
            System.out.println(e.getRawStatusCode());
            System.out.println(e.getResponseBodyAsString());
            System.out.println(e.getStatusText());
        }

        return block;
    }

    public String makeURI(Query query)
    {
        return query.getUrl() + '/' + query.getUrn();
    }
}
//Authorization: OAuth oauth_token="авторизационный_токен", oauth_client_id="идентификатор_приложения"