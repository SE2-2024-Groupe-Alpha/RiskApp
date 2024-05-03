package se2.alpha.riskapp.model.dol;

import java.util.ArrayList;

public class Continent extends Area{
    private ArrayList<Country> countries;

    public Continent(String name, Player owner) {
        super(name, owner);
        this.countries = new ArrayList<Country>();
    }

    public Continent() {
        super();
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public void addCountry(Country c)
    {
        countries.add(c);
    }
}
