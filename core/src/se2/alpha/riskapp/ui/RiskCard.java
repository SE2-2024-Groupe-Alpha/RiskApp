package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import se2.alpha.riskapp.RiskCardType;

public class RiskCard implements Disposable {
    private Texture texture;
    private Texture unit;
    private final Vector2 position;
    private float scale = 0.5f;
    private String country;
    private RiskCardType type;

    public RiskCard(RiskCardType type, Vector2 position, String country) {
        this.texture = new Texture("riskcard.png");
        this.position = position;
        this.country = country;
        this.type = type;
        if (type == RiskCardType.ARTILLERY) {
            this.unit = new Texture("artillery.png");
        } else if(type == RiskCardType.CAVALRY) {
            this.unit =new Texture("cavalry.png");
        } else if(type == RiskCardType.INFANTRY) {
            this.unit = new Texture("infantry.png");
        }
    }

    public void draw(SpriteBatch batch, float zoom, OrthographicCamera camera) {
        if(this.type == RiskCardType.JOKER)
            drawJokerRiskCard(batch, zoom, camera);
        else
            drawBasicRiskCard(batch, zoom, camera);
    }

    private void drawBasicRiskCard(SpriteBatch batch, float zoom, OrthographicCamera camera)
    {
        //float width = texture.getWidth() * scale * zoom;
        //float height = texture.getHeight() * scale * zoom;
        float screenWidth = Gdx.graphics.getWidth() * zoom;
        float screenHeight = Gdx.graphics.getHeight() * zoom;
        float cardWidth = screenWidth / 5 * 4;
        float cardHeight = screenHeight / 5 * 4;
        float cardX = (screenWidth - cardWidth) / 2 - screenWidth / 2 + camera.position.x;
        float cardY = (screenHeight - cardHeight) / 2 - screenHeight / 2 + camera.position.y;

        BitmapFont font = new BitmapFont(Gdx.files.internal("riskcardFont.fnt"));
        GlyphLayout glyphLayout = new GlyphLayout();
        font.getData().setScale(Gdx.graphics.getDensity()); //scale * zoom * 2
        glyphLayout.setText(font, this.country, Color.BLACK, cardWidth / 5 * 3, Align.center, true);
        batch.draw(texture, cardX, cardY, cardWidth, cardHeight);
        batch.draw(unit, cardX + cardWidth / 5, cardY + cardWidth / 10 * 2, cardWidth / 5 * 3, cardHeight / 3);
        font.draw(batch, glyphLayout, (screenWidth - cardWidth) / 2 + cardWidth / 5, cardY + cardHeight / 10 * 7);
    }

    private void drawJokerRiskCard(SpriteBatch batch, float zoom, OrthographicCamera camera)
    {
        float width = texture.getWidth() * scale * zoom;
        float height = texture.getHeight() * scale * zoom;
        Texture artillery = new Texture("artillery.png");
        Texture cavalry = new Texture("cavalry.png");
        Texture infantry = new Texture("infantry.png");

        batch.draw(texture, position.x, position.y, width, height);
        batch.draw(artillery, position.x + width / 5, position.y + height / 10 * 1, width / 5 * 3, height / 5);
        batch.draw(cavalry, position.x + width / 5, position.y + height / 10 * 4, width / 5 * 3, height / 5);
        batch.draw(infantry, position.x + width / 5, position.y + height / 10 * 7, width / 5 * 3, height / 5);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
