package se2.alpha.riskapp.utils;

import com.badlogic.gdx.graphics.Texture;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class TerritoryNode {
    public String name;
    private final String continent;
    private final List<TerritoryNode> adjTerritories;

    private Texture mask;

    public TerritoryNode(String name, String continent) {
        this.name = name;
        this.continent = continent;
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

    public Texture getMask() {
        return mask;
    }

    public List<Texture> getNeighborMasks() {
        ArrayList<Texture> texturesList = new ArrayList<>(adjTerritories.size());

        for (TerritoryNode territory : adjTerritories) {
            texturesList.add(territory.getMask());
        }

        return texturesList;
    }

    public void setMask(Texture mask) {
        this.mask = mask;
    }

    public String getContinent() {
        return continent;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
