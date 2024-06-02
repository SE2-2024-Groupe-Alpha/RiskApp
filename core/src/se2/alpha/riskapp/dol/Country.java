package se2.alpha.riskapp.dol;

import java.util.ArrayList;

import lombok.Getter;

@Getter
public class Country extends Area{
    private ArrayList<Country> attackableCountries;
    private Continent continent;
    private int numberOfTroops;

    public Country(String name, Player owner, Continent continent) {
        super(name, owner);
        this.continent = continent;
        numberOfTroops = 0;
        attackableCountries = new ArrayList<Country>();
    }

    public Country() {
        super();
    }

    public void setAttackableCountries(ArrayList<Country> attackableCountries) {
        this.attackableCountries = attackableCountries;
    }

    public void addArmy(int newTroops)
    {
        numberOfTroops += newTroops;
    }

    public void removeArmy(int removedTroops)
    {
        numberOfTroops -= removedTroops;
    }

    public void addAttackableCountry(Country country)
    {
        attackableCountries.add(country);
    }
}
