package com.example.demo.repository.domain;

import org.springframework.data.annotation.Id;

import java.util.Set;

public class Account {

    @Id
    private String id;
    private String username;
    private String password;
    private Set<String> roles;

    public Account(){}

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
