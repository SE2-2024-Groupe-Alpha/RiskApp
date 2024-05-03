package se2.alpha.riskapp.model.dol;

import java.util.ArrayList;

public class Country extends Area{
    private ArrayList<Country> attackableCountries;
    private Continent continent;
    private ArrayList<Troop> army;

    public Country(String name, Player owner, Continent continent) {
        super(name, owner);
        this.continent = continent;
        this.continent.addCountry(this);
        army = new ArrayList<Troop>();
        attackableCountries = new ArrayList<Country>();
    }

    public Country() {
        super();
    }

    public ArrayList<Country> getAttackableCountries() {
        return attackableCountries;
    }

    public void setAttackableCountries(ArrayList<Country> attackableCountries) {
        this.attackableCountries = attackableCountries;
    }

    public void addArmy(Troop t)
    {
        army.add(t);
        t.setLocation(this);
    }

    public void removeArmy(Troop t)
    {
        army.remove(t);
        t.setLocation(null);
    }
}
