package se2.alpha.riskapp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

public class RiskCard implements Disposable {
    private Texture texture;
    private Texture unit;
    private final Vector2 position;
    private float scale = 0.5f;
    private String country;

    public RiskCard(GameUnitType type, Vector2 position, String country) {
        this.texture = new Texture("riskcard.png");
        this.position = position;
        this.country = country;
        if (type == GameUnitType.ARTILLERY) {
            this.unit = new Texture("artillery.png");
        } else if(type == GameUnitType.CAVALRY) {
            this.unit =new Texture("cavalry.png");
        } else if(type == GameUnitType.INFANTRY) {
            this.unit = new Texture("infantry.png");
        }
    }

    public void draw(SpriteBatch batch, float zoom) {
        float width = texture.getWidth() * scale * zoom;
        float height = texture.getHeight() * scale * zoom;

        BitmapFont font = new BitmapFont(Gdx.files.internal("riskcardFont.fnt"));
        GlyphLayout glyphLayout = new GlyphLayout();
        font.getData().setScale(scale * zoom * 2);
        glyphLayout.setText(font, this.country, Color.BLACK, width / 5 * 3, Align.center, true);
        batch.draw(texture, position.x, position.y, width, height);
        batch.draw(unit, position.x + width / 5, position.y + height / 10 * 2, width / 5 * 3, height / 5);
        font.draw(batch, glyphLayout, position.x + width / 5, position.y + height / 10 * 7);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
