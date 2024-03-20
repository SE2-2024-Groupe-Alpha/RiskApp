package com.example.riskapp.model;

import java.io.Serializable;

public class SignUpRequest implements Serializable {
    private String username;
    private String password;

    public SignUpRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
