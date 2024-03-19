package com.example.riskapp.model;

import java.io.Serializable;

public class SignInRequest implements Serializable {
    private String username;
    private String password;

    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
