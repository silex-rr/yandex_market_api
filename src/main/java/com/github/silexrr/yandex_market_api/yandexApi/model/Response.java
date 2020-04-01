package com.github.silexrr.yandex_market_api.yandexApi.model;

import com.github.silexrr.yandex_market_api.yandexApi.request.model.Query;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Document(collection = "apiResponse")
public class Response {

    @Id
    private String id;
    private String method;
    private Date date;
    private Query query;
    private String response;

    public Response(Query query, String response, String method) {
        this.query = query;
        this.response = response;
        this.method = method;
        this.date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id='" + id + '\'' +
                ", request=" + query +
                ", response='" + response + '\'' +
                ", method='" + method + '\'' +
                ", date=" + date +
                '}';
    }

}

