package com.github.silexrr.yandex_market_api.shop.model;

import com.github.silexrr.yandex_market_api.api.model.APIToken;
import com.github.silexrr.yandex_market_api.auth.model.User;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Document(collection = "shop")
public class Shop {
    @Id
    private String id;

    private String name;
    private String ymLogin;
    private Integer ymCompanyId;
    private Integer ymRegionId;
    private Double requestsPerSecond;
    private boolean enable;

    @ElementCollection(targetClass = User.class, fetch = FetchType.LAZY)
    @ManyToMany
    @DBRef
    private List<User> userOwners;

    private List<YMToken> YMTokens;

    public Shop() {
        this.name = "";
        this.ymLogin = "";
        this.ymCompanyId = 0;
        this.ymRegionId = 0;
        this.requestsPerSecond = 2.0;
        this.userOwners = new ArrayList<User>();
        this.YMTokens = new ArrayList<YMToken>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYmLogin() {
        return ymLogin;
    }

    public void setYmLogin(String ymLogin) {
        this.ymLogin = ymLogin;
    }

    public Integer getYmCompanyId() {
        return ymCompanyId;
    }

    public void setYmCompanyId(Integer ymCompanyId) {
        this.ymCompanyId = ymCompanyId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<User> getUserOwners() {
        return userOwners;
    }

    public void setUserOwners(List<User> userOwners) {
        this.userOwners = userOwners;
    }

    public Integer getYmRegionId() {
        return ymRegionId;
    }

    public void setYmRegionId(Integer ymRegionId) {
        this.ymRegionId = ymRegionId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<YMToken> getYMTokens() {
        return YMTokens;
    }

    public void setYMTokens(List<YMToken> YMTokens) {
        this.YMTokens = YMTokens;
    }

    public Double getRequestsPerSecond() {
        return requestsPerSecond;
    }

    public void setRequestsPerSecond(Double requestsPerSecond) {
        this.requestsPerSecond = requestsPerSecond;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ymLogin='" + ymLogin + '\'' +
                ", ymCompanyId=" + ymCompanyId +
                ", ymRegionId=" + ymRegionId +
                ", requestsPerSecond=" + requestsPerSecond +
                ", enable=" + enable +
                ", userOwners=" + userOwners +
                ", YMTokens=" + YMTokens +
                '}';
    }
}
