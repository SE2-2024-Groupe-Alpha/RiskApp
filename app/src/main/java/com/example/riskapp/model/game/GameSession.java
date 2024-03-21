package com.example.riskapp.model.game;

import java.util.UUID;

public class GameSession {
    public UUID getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }

    public Integer getUsers() {
        return users;
    }

    public GameState getState() {
        return state;
    }

    private UUID sessionId;

    private String name;

    private Integer users;

    private GameState state;
}
