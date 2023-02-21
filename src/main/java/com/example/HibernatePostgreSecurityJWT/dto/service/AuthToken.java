package com.example.HibernatePostgreSecurityJWT.dto.service;

public class AuthToken {

    private String token;

    private AuthToken(){

    }

    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
