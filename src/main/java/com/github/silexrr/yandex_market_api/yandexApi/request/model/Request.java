package com.github.silexrr.yandex_market_api.yandexApi.request.model;

import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.Token;
import org.springframework.util.MultiValueMap;


public class Request {

    private RequestType type;
    private String version;
    private String url;
    private String urn;
    private String resource;
    private RequestResponseFormat requestResponseFormat;
    private MultiValueMap parameters;
    private Shop shop;
    private Token token;


    public Request() {
        this.version = "2";
        this.requestResponseFormat = RequestResponseFormat.JSON;
        this.url = "https://api.partner.market.yandex.ru";
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public RequestResponseFormat getRequestResponseFormat() {
        return requestResponseFormat;
    }

    public void setRequestResponseFormat(RequestResponseFormat requestResponseFormat) {
        this.requestResponseFormat = requestResponseFormat;
    }

    public MultiValueMap getParameters() {
        return parameters;
    }

    public void setParameters(MultiValueMap parameters) {
        this.parameters = parameters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", version=" + version +
                ", url='" + url + '\'' +
                ", resource='" + resource + '\'' +
                ", requestResponseFormat=" + requestResponseFormat +
                ", parameters=" + parameters +
                ", shop=" + shop +
                ", token=" + token +
                '}';
    }
}