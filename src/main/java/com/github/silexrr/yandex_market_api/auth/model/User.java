package com.github.silexrr.yandex_market_api.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Document(collection = "user")
public class User implements UserDetails {

    @Id
    private String id;
    @NotBlank(message = "Username cannot be empty")
    private String login;
    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private String email;
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
        this.email = "";
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

    public String getUid() {
        return  "asd";
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

    public void savePrepare() {
        passwordConfirm = "";
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public String toString() {
//        Method[] methods = User.class.getDeclaredMethods();
//        int nMethod = 1;
//        String method_str = "";
//        for (Method method : methods) {
//            method_str += method;
//        }

        return "User{" +
                "_id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
