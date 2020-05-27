package com.github.silexrr.yandex_market_api.shop.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.util.Date;
import java.util.UUID;

@Entity
public class YMToken {

    private String id;
    private String name;
    private String oauthToken;
    private String oauthClientId;
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireTo;
    private YMTokenType type;

    private boolean enable;

    public YMToken() {
        this.id = UUID.randomUUID().toString();
        this.name = "";
        this.oauthClientId = "";
        this.password = "";
        this.oauthToken = "";
        this.expireTo = new Date();
        this.type = YMTokenType.BEARER;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getOauthClientId() {
        return oauthClientId;
    }

    public void setOauthClientId(String oauthClientId) {
        this.oauthClientId = oauthClientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpireTo() {
        return expireTo;
    }

    public void setExpireTo(Date expireTo) {
        this.expireTo = expireTo;
    }

    public YMTokenType getType() {
        return type;
    }

    public void setType(YMTokenType type) {
        this.type = type;
    }

    public void setType(String type) {
        this.setType(YMTokenType.valueOf(type));
    }

    @Override
    public String toString() {
        return "Token{" +
                "name='" + name + '\'' +
                ", oauthToken='" + oauthToken + '\'' +
                ", oauthClientId='" + oauthClientId + '\'' +
                ", expireTo=" + expireTo +
                ", type=" + type +
                ", enable=" + enable +
                '}';
    }
}
