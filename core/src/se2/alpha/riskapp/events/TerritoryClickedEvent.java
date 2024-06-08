package se2.alpha.riskapp.events;

import se2.alpha.riskapp.utils.TerritoryNode;

public class TerritoryClickedEvent {
    String name;
    TerritoryNode territory;

    public TerritoryClickedEvent(TerritoryNode territoryNode){
        this.name = territoryNode.name;
        this.territory = territoryNode;
    }

    public String getName() {
        return name;
    }

    public TerritoryNode getTerritory() {
        return territory;
    }
}
