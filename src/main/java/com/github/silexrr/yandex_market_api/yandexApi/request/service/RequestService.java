package com.github.silexrr.yandex_market_api.yandexApi.request.service;

import com.github.silexrr.yandex_market_api.shop.model.Token;
import com.github.silexrr.yandex_market_api.yandexApi.Method;
import com.github.silexrr.yandex_market_api.yandexApi.request.model.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class RequestService {

    public String send(Method method)
    {
        Request request = method.getRequest();
        WebClient webClient = WebClient.create();
        HttpMethod type = HttpMethod.GET;
        switch (request.getType()) {
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
        String uri = this.makeURI(request);
        Token token = request.getToken();

        String OAuthString = "OAuth oauth_token=\"" + token.getOauthToken()
                + "\", oauth_client_id=\"" + token.getOauthClientId() + "\"";

        String block = "";
        try {
            block = webClient
                    .method(type)
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, OAuthString)
//                .body(BodyInserters.fromFormData(request.getParameters()))
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

    public String makeURI(Request request)
    {
        return request.getUrl() + '/' + request.getUrn();
    }
}
//Authorization: OAuth oauth_token="авторизационный_токен", oauth_client_id="идентификатор_приложения"