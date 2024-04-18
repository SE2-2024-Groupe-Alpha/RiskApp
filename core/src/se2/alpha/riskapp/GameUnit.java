package se2.alpha.riskapp;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GameUnit implements Disposable {
    private int strength;
    private final GameUnitType type;
    private Texture texture;
    private final Vector2 position;
    private float scale = 0.1f;

    public GameUnit(GameUnitType type, Vector2 position) {
        this.type = type;
        this.position = position;
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

    public void draw(SpriteBatch batch, float zoom) {
        float width = texture.getWidth() * scale * zoom;
        float height = texture.getHeight() * scale * zoom;
        float x = position.x - width / 2;
        float y = position.y - height / 2;
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
