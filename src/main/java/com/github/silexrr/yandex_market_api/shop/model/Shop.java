package com.github.silexrr.yandex_market_api.shop.model;

import com.github.silexrr.yandex_market_api.auth.model.User;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.Annotation;
import java.util.List;

@Entity
@Document(collection = "shop")
public class Shop {
    @Id
    private String id;

    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "YM login cannot be empty")
    private String ymLogin;
    @NotBlank(message = "YM company ID cannot be empty")
    private Integer ymCompanyId;
    private Integer ymRegionId;
    private boolean enable;

    private List<User> moderators;

    private List<Token> tokens;

    private List<PriceList> priceLists;

    @ElementCollection(targetClass = User.class, fetch = FetchType.EAGER)
    @ManyToMany
    private User userOwner;

    public Shop() {
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

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public Integer getYmRegionId() {
        return ymRegionId;
    }

    public void setYmRegionId(Integer ymRegionId) {
        this.ymRegionId = ymRegionId;
    }

    public String getId() {
        return id;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<PriceList> getPriceLists() {
        return priceLists;
    }

    public void setPriceLists(List<PriceList> priceLists) {
        this.priceLists = priceLists;
    }

    public List<User> getModerators() {
        return moderators;
    }

    public void setModerators(List<User> moderators) {
        this.moderators = moderators;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ymLogin='" + ymLogin + '\'' +
                ", ymCompanyId=" + ymCompanyId +
                ", ymRegionId=" + ymRegionId +
                ", enable=" + enable +
                ", moderators=" + moderators +
                ", tokens=" + tokens +
                ", priceLists=" + priceLists +
                ", userOwner=" + userOwner +
                '}';
    }
}
