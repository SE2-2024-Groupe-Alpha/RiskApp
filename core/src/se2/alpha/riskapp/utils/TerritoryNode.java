package se2.alpha.riskapp.utils;

import com.badlogic.gdx.graphics.Texture;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class TerritoryNode {
    public String name;
    private final List<TerritoryNode> adjTerritories;

    private Texture mask;

    private int unitCnt;
    private String ownedBy;

    public TerritoryNode(String name) {
        this.name = name;
        this.adjTerritories = new ArrayList<>();
        this.unitCnt = 0;
        this.ownedBy = null;
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

    @Override
    public String toString() {
        return this.name;
    }
}
