package se2.alpha.riskapp.events;

import se2.alpha.riskapp.utils.TerritoryNode;

public class SelectCountryEvent {

    TerritoryNode selctedCountry;

    public SelectCountryEvent(TerritoryNode country){
        selctedCountry = country;
    }

    public TerritoryNode getSelctedCountry() {
        return selctedCountry;
    }
}
