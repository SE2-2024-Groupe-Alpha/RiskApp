package se2.alpha.riskapp.ui;

import static se2.alpha.riskapp.utils.Territories.colorsToTerritories;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import se2.alpha.riskapp.utils.Territories;
import se2.alpha.riskapp.utils.TerritoryNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PixelReader {

    Texture pixelMap;
    float screenScaleFactor;
    Map<TerritoryNode, Texture> colorsToTextures = new HashMap<>();

    public PixelReader(float screenScaleFactor) {
        this.pixelMap = new Texture("riskMapColored.png");
        this.screenScaleFactor = screenScaleFactor;

        for (String key : colorsToTerritories.keySet()) {
            TerritoryNode territoryNode = Territories.getTerritoryByColor(key);
            Color color = Color.valueOf(key);
            colorsToTextures.put(territoryNode, createTextureMaskByColor(color));
        }
    }

    public Color getPixelColor(int x, int y) {

            if (!this.pixelMap.getTextureData().isPrepared()) {
                this.pixelMap.getTextureData().prepare();
            }

            Pixmap pixmap = this.pixelMap.getTextureData().consumePixmap();
            int pixelValue = pixmap.getPixel(x, y);

            Color color = new Color();
            Color.rgba8888ToColor(color, pixelValue);

            pixmap.dispose();

            System.out.println("Extracted color at coordinates (" + x + ", " + y + "): " + color);

            return color;
        }


    // CPU heavy function, to improve performance we will probably need to use libgdx shaders instead.
    private Texture createTextureMaskByColor(Color color) {

        if (!pixelMap.getTextureData().isPrepared()) {
            pixelMap.getTextureData().prepare();
        }
        Pixmap mapPixmap = pixelMap.getTextureData().consumePixmap();
        Pixmap resultPixmap = new Pixmap(mapPixmap.getWidth(), mapPixmap.getHeight(), mapPixmap.getFormat());

        for (int y = 0; y < mapPixmap.getHeight(); y++) {
            for (int x = 0; x < mapPixmap.getWidth(); x++) {
                int mapColor = mapPixmap.getPixel(x, y);
                int maskColor = Color.rgba8888(color);
                int overlayColor = Color.rgba8888(Color.BLACK);

                if (mapColor == maskColor) {
                    resultPixmap.drawPixel(x, y, overlayColor);
                }
            }
        }

        Texture resultTexture = new Texture(resultPixmap);
        mapPixmap.dispose();
        resultPixmap.dispose();

        return resultTexture;
    }

    public Texture getTextureMaskForTerritory(TerritoryNode territoryNode) {
        return colorsToTextures.get(territoryNode);
    }

    public List<Texture> getTextureMasksForTerritories(List<TerritoryNode> territoryNodes) {
        ArrayList<Texture> texturesList = new ArrayList<>(territoryNodes.size());

        for (TerritoryNode territory : territoryNodes) {
            texturesList.add(getTextureMaskForTerritory(territory));
        }

        return texturesList;
    }
}
