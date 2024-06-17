package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

import se2.alpha.riskapp.dol.RiskCardType;

public class RiskCard implements Disposable {
    private Texture texture;
    private Texture unit;
    private float scale = 0.5f;
    private String country;
    private RiskCardType type;

    public RiskCard(RiskCardType type, String country) {
        this.country = country;
        this.type = type;
    }

    public void draw(SpriteBatch batch, float zoom, OrthographicCamera camera, Viewport viewport) {
        if(this.type == RiskCardType.JOKER)
            drawJokerRiskCard(batch, zoom, camera);
        else
            drawBasicRiskCard(batch, zoom, camera, viewport);
    }

    private void drawBasicRiskCard(SpriteBatch batch, float zoom, OrthographicCamera camera, Viewport viewport)
    {
        final float relativeCardWidth = 0.8f;
        final float relativeCardHeight = 0.8f;
        final float relativeTroopBeginWidth = 0.2f;
        final float relativeTroopHeight = 0.4f;
        final float relativeFontHeight = 0.7f;
        final float relativeTroopWidth = 0.6f;
        final float relativeCardBeginWidth = 0.2f;

        float screenWidth = viewport.getScreenWidth() * zoom;
        float screenHeight = viewport.getScreenHeight() * zoom;
        float cardWidth = screenWidth * relativeCardWidth;
        float cardHeight = screenHeight * relativeCardHeight;
        float cardX = (screenWidth - cardWidth) / 2 - screenWidth / 2 + camera.position.x;
        float cardY = (screenHeight - cardHeight) / 2 - screenHeight / 2 + camera.position.y;

        this.texture = new Texture("riskcard.png");
        if (type == RiskCardType.ARTILLERY) {
            this.unit = new Texture("artillery.png");
        } else if(type == RiskCardType.CAVALRY) {
            this.unit =new Texture("cavalry.png");
        } else if(type == RiskCardType.INFANTRY) {
            this.unit = new Texture("infantry.png");
        }

        BitmapFont font = new BitmapFont(Gdx.files.internal("riskcardFont.fnt"));
        GlyphLayout glyphLayout = new GlyphLayout();
        font.getData().setScale(Gdx.graphics.getDensity()); //scale * zoom * 2
        glyphLayout.setText(font, this.country, Color.BLACK, cardWidth * relativeTroopWidth, Align.center, true);
        batch.draw(texture, cardX, cardY, cardWidth, cardHeight);
        batch.draw(unit, cardX + cardWidth * relativeCardBeginWidth, cardY + cardWidth * relativeTroopBeginWidth, cardWidth * relativeTroopWidth, cardHeight * relativeTroopHeight);
        font.draw(batch, glyphLayout, (screenWidth - cardWidth) / 2 + cardWidth * relativeCardBeginWidth, cardY + cardHeight * relativeFontHeight);
    }

    private void drawJokerRiskCard(SpriteBatch batch, float zoom, OrthographicCamera camera)
    {
        final float relativeCardWidth = 0.8f;
        final float relativeCardHeight = 0.8f;
        final float relativeTroopHeight = 0.2f;
        final float relativeTroopWidth = 0.4f;
        final float relativeCardBeginWidth = 0.3f;
        float screenWidth = Gdx.graphics.getWidth() * zoom;
        float screenHeight = Gdx.graphics.getHeight() * zoom;
        float cardWidth = screenWidth * relativeCardWidth;
        float cardHeight = screenHeight * relativeCardHeight;
        float cardX = (screenWidth - cardWidth) / 2 - screenWidth / 2 + camera.position.x;
        float cardY = (screenHeight - cardHeight) / 2 - screenHeight / 2 + camera.position.y;
        Texture artillery = new Texture("artillery.png");
        Texture cavalry = new Texture("cavalry.png");
        Texture infantry = new Texture("infantry.png");
        this.texture = new Texture("riskcard.png");

        batch.draw(texture, cardX, cardY, cardWidth, cardHeight);
        batch.draw(artillery, cardX + cardWidth * relativeCardBeginWidth, cardY + cardHeight * 0.1f, cardWidth * relativeTroopWidth, cardHeight * relativeTroopHeight);
        batch.draw(cavalry, cardX + cardWidth * relativeCardBeginWidth, cardY + cardHeight * 0.4f, cardWidth * relativeTroopWidth, cardHeight * relativeTroopHeight);
        batch.draw(infantry, cardX + cardWidth * relativeCardBeginWidth, cardY + cardHeight * 0.7f, cardWidth * relativeTroopWidth, cardHeight * relativeTroopHeight);
    }

    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
        if (unit != null) {
            unit.dispose();
        }
    }
}
