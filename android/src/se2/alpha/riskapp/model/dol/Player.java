package se2.alpha.riskapp.model.logic;

import java.util.ArrayList;

public class Player {
    private String name;
    private String color;
    private ArrayList<Troop> army;
    private ArrayList<RiskCard> cards;
    private boolean eliminated;
    private ArrayList<Country> controlledCountries;
    private ArrayList<Continent> controlledContinents;

    public Player(String name, String color, ArrayList<Troop> army, ArrayList<RiskCard> cards) {
        this.name = name;
        this.color = color;
        this.army = army;
        this.cards = cards;
    }

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        cards = new ArrayList<RiskCard>();
        army = new ArrayList<Troop>();
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<Troop> getArmy() {
        return army;
    }

    public void setArmy(ArrayList<Troop> army) {
        this.army = army;
    }

    public ArrayList<RiskCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<RiskCard> cards) {
        this.cards = cards;
    }

    public void addArmy(Troop troop)
    {
        army.add(troop);
        troop.setOwner(this);
    }

    public void removeArmy(Troop troop)
    {
        army.remove(troop);
        troop.setOwner(null);
    }

    public void addRiskCard(RiskCard card)
    {
        cards.add(card);
    }

    public void removeRiskCard(RiskCard card)
    {
        cards.remove(card);
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    public void controlCountry(Country c)
    {
        controlledCountries.add(c);
        c.setOwner(this);
    }

    public void loseControlOverCountry(Country c)
    {
        controlledCountries.remove(c);
        c.setOwner(null);
    }

    public void controlContinent(Continent c)
    {
        controlledContinents.add(c);
        c.setOwner(this);
    }

    public void loseControlOverContinent(Continent c)
    {
        controlledContinents.remove(c);
        c.setOwner(null);
    }

    public ArrayList<Country> getControlledCountries() {
        return controlledCountries;
    }

    public ArrayList<Continent> getControlledContinents() {
        return controlledContinents;
    }
}