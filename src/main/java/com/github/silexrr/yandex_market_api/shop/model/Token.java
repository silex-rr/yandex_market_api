package com.github.silexrr.yandex_market_api.shop.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Document(collection = "shopToken")
public class Token {

    @Id
    private String id;

    private String name;

    private String oauthToken;
    private String oauthClientId;
    private Date expireTo;
    private Enum<TokenType> type;

    @ElementCollection(targetClass = Shop.class, fetch = FetchType.EAGER)
    private Shop shop;

    private boolean enable;

    public Token() {
        this.name = "";
        this.oauthClientId = "";
        this.oauthToken = "";
        this.expireTo = new Date();
        this.type = TokenType.bearer;
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

    public Enum<TokenType> getType() {
        return type;
    }

    public void setType(Enum<TokenType> type) {
        this.type = type;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", oauthToken='" + oauthToken + '\'' +
                ", oauthClientId='" + oauthClientId + '\'' +
                ", expireTo=" + expireTo +
                ", type=" + type +
                ", shop=" + shop +
                ", enable=" + enable +
                '}';
    }
}
