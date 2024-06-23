package se2.alpha.riskapp.events;

import java.util.UUID;

public class MoveTroopEvent {
    private String playerId;
    private String moveFromCountryName;
    private String moveToCountryName;
    private int numberOfTroops;

    public MoveTroopEvent(String playerId, String moveFromCountryName, String moveToCountryName, int numberOfTroops) {
        this.playerId = playerId;
        this.moveFromCountryName = moveFromCountryName;
        this.moveToCountryName = moveToCountryName;
        this.numberOfTroops = numberOfTroops;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getMoveFromCountryName() {
        return moveFromCountryName;
    }

    public String getMoveToCountryName() {
        return moveToCountryName;
    }

    public int getNumberOfTroops() {
        return numberOfTroops;
    }
}
