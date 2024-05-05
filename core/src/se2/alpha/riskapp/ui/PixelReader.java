package se2.alpha.riskapp.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PixelReader {

    Texture pixelMap;
    float screenScaleFactor;
    Map<String, String> colorsToTerritories = new HashMap<>();
    Map<Color, Texture> colorsToTextures = new HashMap<>();

    public PixelReader(float screenScaleFactor) {
        this.pixelMap = new Texture("riskMapColored.png");
        this.screenScaleFactor = screenScaleFactor;

        colorsToTerritories.put("FF6347", "Alaska");
        colorsToTerritories.put("4682B4", "Northwest Territory");
        colorsToTerritories.put("D8BFD8", "Greenland");
        colorsToTerritories.put("FFD700", "Alberta");
        colorsToTerritories.put("D2691E", "Ontario");
        colorsToTerritories.put("8A2BE2", "Quebec");
        colorsToTerritories.put("DC143C", "Western United States");
        colorsToTerritories.put("00FFFF", "Eastern United States");
        colorsToTerritories.put("00008B", "Central America");
        colorsToTerritories.put("008000", "Venezuela");
        colorsToTerritories.put("FF4500", "Peru");
        colorsToTerritories.put("6A5ACD", "Brazil");
        colorsToTerritories.put("8B4513", "Argentina");
        colorsToTerritories.put("FA8072", "Iceland");
        colorsToTerritories.put("EEE8AA", "Scandinavia");
        colorsToTerritories.put("A0522D", "Russia");
        colorsToTerritories.put("2E8B57", "Great Britain");
        colorsToTerritories.put("FF1493", "Northern Europe");
        colorsToTerritories.put("483D8B", "Western Europe");
        colorsToTerritories.put("B8860B", "Austria");
        colorsToTerritories.put("5F9EA0", "North Africa");
        colorsToTerritories.put("9ACD32", "Egypt");
        colorsToTerritories.put("8B0000", "East Africa");
        colorsToTerritories.put("FFA500", "Congo");
        colorsToTerritories.put("006400", "South Africa");
        colorsToTerritories.put("800080", "Madagascar");
        colorsToTerritories.put("FF00FF", "Ural");
        colorsToTerritories.put("A52A2A", "Siberia");
        colorsToTerritories.put("DEB887", "Yakutsk");
        colorsToTerritories.put("85F8FF", "Kamchatka");
        colorsToTerritories.put("7FFF00", "Irkutsk");
        colorsToTerritories.put("FFDEAD", "Mongolia");
        colorsToTerritories.put("FF0000", "Japan");
        colorsToTerritories.put("0000CD", "Afghanistan");
        colorsToTerritories.put("BA55D3", "China");
        colorsToTerritories.put("4B0082", "India");
        colorsToTerritories.put("F08080", "Siam");
        colorsToTerritories.put("20B2AA", "Indonesia");
        colorsToTerritories.put("FFFAF0", "New Guinea");
        colorsToTerritories.put("228B22", "Western Australia");
        colorsToTerritories.put("ADFF2F", "Eastern Australia");
        colorsToTerritories.put("F0E68C", "Middle East");

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
        return this.colorsToTerritories.get(colorKey);
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
