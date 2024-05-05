package se2.alpha.riskapp.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import se2.alpha.riskapp.utils.Territories;
import se2.alpha.riskapp.utils.TerritoryNode;

import java.util.HashMap;
import java.util.Map;

public class PixelReader {

    Texture pixelMap;
    float screenScaleFactor;

    public PixelReader(float screenScaleFactor) {
        this.pixelMap = new Texture("riskMapColored.png");
        this.screenScaleFactor = screenScaleFactor;
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
}
