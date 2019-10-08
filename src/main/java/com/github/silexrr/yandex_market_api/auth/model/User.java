package com.github.silexrr.yandex_market_api.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String login;
    private String password;
    @Transient
    private String passwordConfirm;
    private String name;
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @ManyToMany
    private Set<Role> roles;

    public User() {
        this.login = "";
        this.name = "";
    }

    public User(String id, String login, String name, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(String login, String name) {
        this(UUID.randomUUID().toString(), login, name,"");
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + id + '\'' +
                ", login='" + login + '\'' +
//                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
