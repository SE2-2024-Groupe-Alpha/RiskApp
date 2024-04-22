package se2.alpha.riskapp.model.game;

import java.util.UUID;

public class GameSession {
    private UUID sessionId;
    private String name;
    private Integer users;
    private GameState state;
    private boolean isPrivate;

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


    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
