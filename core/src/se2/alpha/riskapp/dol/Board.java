package se2.alpha.riskapp.dol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import se2.alpha.riskapp.utils.TerritoryNode;
import se2.alpha.riskapp.utils.Territories;

@Getter
public class Board {
    private ArrayList<Continent> continents;
    private ArrayList<RiskCard> cards;

    public Board(){
        setupContinents();
    }

    public ArrayList<Continent> getContinents() {
        return continents;
    }

    public void setCards(ArrayList<RiskCard> cards) {
        this.cards = cards;
    }

    public RiskCard getNewRiskCard() throws Exception
    {
        if(cards.isEmpty())
            throw new Exception("No available risk cards anymore");
        return cards.remove(0);
    }

    private void setupContinents() {
        HashMap<String, TerritoryNode> territories = (HashMap<String, TerritoryNode>) Territories.getAllTerritories();
        continents = new ArrayList<>();
        for (Map.Entry<String, TerritoryNode> entry : territories.entrySet()) {
            String territoryName = entry.getKey();
            TerritoryNode territoryNode = entry.getValue();
            if (isNewContinent(territoryNode.getContinent()))
                continents.add(new Continent(territoryNode.getContinent(), null));
            setupCountry(territoryNode, territoryName);
        }

        setAttackableCountries(territories);
    }

    private void setupCountry(TerritoryNode territoryNode, String territoryName) {
        Continent continent = getContinentByName(territoryNode.getContinent());
        continent.addCountry(new Country(territoryName, null, continent));
    }

    private Continent getContinentByName(String name) {
        for (Continent continent : continents) {
            if (continent.getName().equals(name))
                return continent;
        }
        return null;
    }

    private void setAttackableCountries(HashMap<String, TerritoryNode> territories) {
        for (Country country : getAllCountries()) {
            for (TerritoryNode territoryNode : territories.get(country.getName()).getAdjTerritories()) {
                country.addAttackableCountry(getCountryByName(territoryNode.getName()));
            }

        }
    }

    private Country getCountryByName(String name) {
        for (Country country : getAllCountries()) {
            if (country.getName().equals(name))
                return country;
        }
        return null;
    }

    private ArrayList<Country> getAllCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        for (Continent continent : continents) {
            countries.addAll(continent.getCountries());
        }
        return countries;
    }

    private boolean isNewContinent(String name) {
        for (Continent continent : continents) {
            if (continent.getName().equals(name))
                return false;
        }
        return true;
    }
}
