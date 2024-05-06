package se2.alpha.riskapp.utils;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class TerritoryNode {
    public String name;
    private final List<TerritoryNode> adjTerritories;

    public TerritoryNode(String name) {
        this.name = name;
        this.adjTerritories = new ArrayList<>();
    }

    public void addAdjTerritory(TerritoryNode territory) {
        this.adjTerritories.add(territory);
    }

    public void addAdjTerritory(List<TerritoryNode> territory) {
        this.adjTerritories.addAll(territory);
    }

    public List<TerritoryNode> getAdjTerritories() {
        return adjTerritories;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
