package se2.alpha.riskapp.dol;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Country extends Area{
    private transient ArrayList<Country> attackableCountries;
    private transient Continent continent;
    private int numberOfTroops;

    private Texture mask;

    public Country(String name, Player owner, Continent continent) {
        super(name, owner);
        this.continent = continent;
        numberOfTroops = 0;
        attackableCountries = new ArrayList<Country>();
    }

    public Texture getMask() {
        return mask;
    }

    public void setMask(Texture mask) {
        this.mask = mask;
    }

    public Country() {
        super();
    }

    public void setAttackableCountries(ArrayList<Country> attackableCountries) {
        this.attackableCountries = attackableCountries;
    }

    public void setNumberOfTroops(int numberOfTroops) {
        this.numberOfTroops = numberOfTroops;
    }

    public int getNumberOfTroops() {
        return numberOfTroops;
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
