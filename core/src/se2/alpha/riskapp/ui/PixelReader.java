package se2.alpha.riskapp.ui;

import static se2.alpha.riskapp.utils.Territories.colorsToTerritories;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import se2.alpha.riskapp.utils.Territories;
import se2.alpha.riskapp.utils.TerritoryNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PixelReader {

    Texture pixelMap;
    float screenScaleFactor;
    Map<Color, Texture> colorsToTextures = new HashMap<>();

    public PixelReader(float screenScaleFactor) {
        this.pixelMap = new Texture("riskMapColored.png");
        this.screenScaleFactor = screenScaleFactor;

        for (String key : colorsToTerritories.keySet()) {
            Color color = Color.valueOf(key);
            colorsToTextures.put(color, createTextureMaskByColor(color));
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

        return color;
    }

    public String getTerritory(Color color) {
        String colorKey = color.toString().substring(0, 6).toUpperCase();
        TerritoryNode territoryNode = Territories.getTerritoryByColor(colorKey);
        System.out.println(territoryNode.getAdjTerritories());
        return territoryNode.name;
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

    public Texture getTextureMaskByColor(Color color) {
        return colorsToTextures.get(color);
    }
}
