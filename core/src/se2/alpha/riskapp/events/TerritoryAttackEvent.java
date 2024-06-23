package se2.alpha.riskapp.events;


public class TerritoryAttackEvent {
    private final String attackerPlayerId;
    private final String defenderPlayerId;
    private final String attackingCountryName;
    private final String defendingCountryName;

    public TerritoryAttackEvent(String attackerPlayerId, String defenderPlayerId, String attackingCountryName, String defendingCountryName) {
        this.attackerPlayerId = attackerPlayerId;
        this.defenderPlayerId = defenderPlayerId;
        this.attackingCountryName = attackingCountryName;
        this.defendingCountryName = defendingCountryName;
    }

    public String getAttackerPlayerId() {
        return attackerPlayerId;
    }

    public String getDefenderPlayerId() {
        return defenderPlayerId;
    }

    public String getAttackingCountryName() {
        return attackingCountryName;
    }

    public String getDefendingCountryName() {
        return defendingCountryName;
    }
}
