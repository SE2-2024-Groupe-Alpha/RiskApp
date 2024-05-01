package se2.alpha.riskapp.model.logic;

import java.util.ArrayList;

public class Board {
    private ArrayList<Continent> continents;
    private ArrayList<RiskCard> cards;

    public Board(ArrayList<Continent> continents, ArrayList<RiskCard> cards) {
        this.continents = continents;
        this.cards = cards;
    }

    public Board() {
        cards = new ArrayList<RiskCard>();
        continents = new ArrayList<Continent>();
    }

    public ArrayList<Continent> getContinents() {
        return continents;
    }

    public void setContinents(ArrayList<Continent> continents) {
        this.continents = continents;
    }

    public ArrayList<RiskCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<RiskCard> cards) {
        this.cards = cards;
    }
}
