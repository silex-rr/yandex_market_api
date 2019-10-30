package com.github.silexrr.yandex_market_api.shop.model;

public class Token {
    private Shop shop;
    private String oauthToken;
    private String oauthClientId;
    private boolean enable;

    public Token() {
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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

    @Override
    public String toString() {
        return "Token{" +
                "shop=" + shop +
                ", oauthToken='" + oauthToken + '\'' +
                ", oauthClientId='" + oauthClientId + '\'' +
                ", enable=" + enable +
                '}';
    }
}
