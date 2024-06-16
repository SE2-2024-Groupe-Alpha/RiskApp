package se2.alpha.riskapp;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;


@Getter
public class GameUnit implements Disposable {

        private final int strength;
        private final GameUnitType type;
        private final Texture texture;
        private final Vector2 position;
        private final float scale = 0.1f;

        private GameUnit(GameUnitType type, int strength, String textureFile, Vector2 position) {
            this.type = type;
            this.strength = strength;
            this.texture = new Texture(textureFile);
            this.position = position;
        }

        public static GameUnit create(GameUnitType type, Vector2 position) {
            switch (type) {
                case ARTILLERY:
                    return new GameUnit(type, 10, "artillery.png", position);
                case CAVALRY:
                    return new GameUnit(type, 5, "cavalry.png", position);
                case INFANTRY:
                    return new GameUnit(type, 1, "infantry.png", position);
                default:
                    throw new IllegalArgumentException("Unknown GameUnitType: " + type);
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


