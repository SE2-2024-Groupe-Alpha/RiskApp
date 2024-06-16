package se2.alpha.riskapp.dol;

import java.util.ArrayList;

import lombok.Getter;

public class Player {
    private String id;
    private String name;
    private int color;
    private transient final ArrayList<RiskCard> cards;
    private boolean eliminated;
    private int cntRiskCardsTraded;
    private int totalNumberOfTroops;
    private int freeNumberOfTroops;
    private boolean currentTurn;
    private transient final ArrayList<Country> controlledCountries;
    private transient final ArrayList<Continent> controlledContinents;
    private static final int TROOPSFORFIRSTTRADE = 4;
    private static final int TROOPSFORSECONDTRADE = 6;
    private static final int TROOPSFORTHIRDTRADE = 8;
    private static final int TROOPSFORFOURTHTRADE = 10;
    private static final int TROOPSFORFIFTHTRADE = 12;
    private static final int TROOPSFORSIXTHTRADE = 15;

    public int getColor() {
        return color;
    }

    public Player(String id, String name, int color, int numberOfTroops) {
        this.id = id;
        this.name = name;
        this.color = color;
        cards = new ArrayList<RiskCard>();
        controlledContinents = new ArrayList<Continent>();
        controlledCountries = new ArrayList<Country>();
        freeNumberOfTroops = numberOfTroops;
        totalNumberOfTroops = numberOfTroops;
        currentTurn = false;
    }

    public Player(String id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
        cards = new ArrayList<RiskCard>();
        controlledContinents = new ArrayList<Continent>();
        controlledCountries = new ArrayList<Country>();
        freeNumberOfTroops = 0;
        totalNumberOfTroops = 0;
        currentTurn = false;
    }

    public Player() {
        cards = new ArrayList<RiskCard>();
        controlledContinents = new ArrayList<Continent>();
        controlledCountries = new ArrayList<Country>();
        currentTurn = false;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getFreeNumberOfTroops() {
        return freeNumberOfTroops;
    }
}