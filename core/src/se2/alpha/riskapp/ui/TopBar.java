package se2.alpha.riskapp.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import se2.alpha.riskapp.events.TerritoryClickedEvent;
import se2.alpha.riskapp.logic.EventBus;

public class TopBar implements Disposable {
    SpriteBatch uiBatch;
    OrthographicCamera uiCamera;
    ShapeRenderer shapeRenderer;
    BitmapFont font;
    GlyphLayout layout;
    int screenHeight;
    int screenWidth;
    String topBarText = "Setup";

    public TopBar(int screenHeight, int screenWidth) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.uiBatch = new SpriteBatch();
        this.uiCamera = new OrthographicCamera(screenWidth, screenHeight);
        this.uiCamera.setToOrtho(false);
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
        this.font.getData().setScale(4.0f);
        this.layout = new GlyphLayout();
        EventBus.registerCallback(TerritoryClickedEvent.class, event -> {
            TerritoryClickedEvent territoryClickedEvent = (TerritoryClickedEvent) event;
            topBarText = territoryClickedEvent.getName();
        });
    }

    public void draw() {
        shapeRenderer.setProjectionMatrix(uiCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(0, (float) (screenHeight * 0.95), screenWidth, (float) (screenHeight * 0.95));
        shapeRenderer.end();

        uiBatch.setProjectionMatrix(uiCamera.combined);
        uiBatch.begin();
        font.setColor(Color.WHITE);
        layout.setText(font, topBarText);
        float textWidth = layout.width;
        float textX = (screenWidth - textWidth) / 2;
        float textY = (float) (screenHeight * 0.975 + layout.height / 2);
        font.draw(uiBatch, layout, textX, textY);
        uiBatch.end();
    }

    @Override
    public void dispose () {
        uiBatch.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
