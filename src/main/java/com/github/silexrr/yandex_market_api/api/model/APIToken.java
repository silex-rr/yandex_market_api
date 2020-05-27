package com.github.silexrr.yandex_market_api.api.model;

import com.github.silexrr.yandex_market_api.auth.model.User;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@Document(collection = "api_token")
public class APIToken {

    @Id
    private String id;
    private Date expireTo;
    private String token;
    private boolean enable;

    @ElementCollection(targetClass = User.class, fetch = FetchType.EAGER)
    @DBRef
    private User user;

    public APIToken() {
        this.id = UUID.randomUUID().toString();
        this.token = "";
        this.expireTo = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getExpireTo() {
        return expireTo;
    }

    public void setExpireTo(Date expireTo) {
        this.expireTo = expireTo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "APIToken{" +
                "id='" + id + '\'' +
                ", expireTo=" + expireTo +
                ", token='" + token + '\'' +
                ", enable=" + enable +
                ", user=" + user +
                '}';
    }
}
