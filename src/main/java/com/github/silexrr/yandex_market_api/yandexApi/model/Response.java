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
    private String requestId;
    private String method;
    private Date date;
    private Query query;
    private String response;
    private boolean delivered;

    public Response(Query query, String response, String method, String requestId) {
        this.query = query;
        this.response = response;
        this.method = method;
        this.requestId = requestId;
        this.date = new Date();
        this.delivered = false;
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

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id='" + id + '\'' +
                ", requestId='" + requestId + '\'' +
                ", method='" + method + '\'' +
                ", date=" + date +
                ", query=" + query +
                ", response='" + response + '\'' +
                ", delivered=" + delivered +
                '}';
    }

}

