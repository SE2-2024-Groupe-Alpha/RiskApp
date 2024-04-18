package se2.alpha.riskapp;

import com.badlogic.gdx.graphics.Texture;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GameUnit {
    private int strength;
    private final GameUnitType type;
    private Texture texture;

    public GameUnit(GameUnitType type) {
        this.type = type;
        if (type == GameUnitType.ARTILLERY) {
            this.strength = 10;
            this.texture = new Texture("artillery.png");
        } else if(type == GameUnitType.CAVALRY) {
            this.strength = 5;
            this.texture =new Texture("cavalry.png");
        } else if(type == GameUnitType.INFANTRY) {
            this.strength = 1;
            this.texture = new Texture("infantry.png");
        }
    }
}
