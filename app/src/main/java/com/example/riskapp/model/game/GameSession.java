package com.example.riskapp.model.game;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GameSession {

    private UUID sessionId;

    private String name;

    private Integer users;

    private GameState state;
}
