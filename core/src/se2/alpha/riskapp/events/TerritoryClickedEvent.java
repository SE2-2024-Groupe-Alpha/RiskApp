package se2.alpha.riskapp.events;

import se2.alpha.riskapp.utils.TerritoryNode;

public class TerritoryClickedEvent {
    String name;

    public TerritoryClickedEvent(TerritoryNode territoryNode){
        this.name = territoryNode.name;
    }

    public String getName() {
        return name;
    }
}
