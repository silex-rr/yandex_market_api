package com.github.silexrr.yandex_market_api.api.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Request.class)
public class Request {

    private String id;
    private String method;
    private String param;
    private Date date;

    public Request() {
        this.id = "";
        this.method = "";
        this.param = "";
        this.date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", method='" + method + '\'' +
                ", param='" + param + '\'' +
                ", date=" + date +
                '}';
    }
}
