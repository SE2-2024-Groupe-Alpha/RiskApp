package com.example.riskapp.model.auth;

import java.io.Serializable;

public class ValidationRequest implements Serializable {
    private String token;

    public ValidationRequest(String token) {
        this.token = token;
    }
}
