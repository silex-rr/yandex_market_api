package com.github.silexrr.yandex_market_api.yandexApi.request.model;

import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.Token;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.MultiValueMap;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.Map;


public class Query {

    private String id;
    private QueryType type;
    private String version;
    private String url;
    private String urn;
    private String resource;
    private QueryResponseFormat queryResponseFormat;
    private Map parameters;
    @DBRef
    @ElementCollection(targetClass = Shop.class, fetch = FetchType.LAZY)
    private Shop shop;
    @DBRef
    @ElementCollection(targetClass = Token.class, fetch = FetchType.LAZY)
    private Token token;


    public Query(String id) {
        this.id = id;
        this.version = "2";
        this.queryResponseFormat = QueryResponseFormat.JSON;
        this.url = "https://api.partner.market.yandex.ru";
    }

    public QueryType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(QueryType type) {
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

    public QueryResponseFormat getQueryResponseFormat() {
        return queryResponseFormat;
    }

    public void setQueryResponseFormat(QueryResponseFormat queryResponseFormat) {
        this.queryResponseFormat = queryResponseFormat;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
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
        return "Query{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", urn='" + urn + '\'' +
                ", resource='" + resource + '\'' +
                ", queryResponseFormat=" + queryResponseFormat +
                ", parameters=" + parameters +
                ", shop=" + shop +
                ", token=" + token +
                '}';
    }
}
