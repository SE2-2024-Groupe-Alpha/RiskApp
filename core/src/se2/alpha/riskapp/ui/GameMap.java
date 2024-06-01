package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

import se2.alpha.riskapp.GameUnit;
import se2.alpha.riskapp.inputs.GestureHandlerMap;

public class GameMap implements Disposable {
    SpriteBatch batch;
    OrthographicCamera camera;
    float waterSpeedX = 0.1f, waterSpeedY = 0.05f, waterOffsetX = 0, waterOffsetY = 0;
    public Texture background, backgroundCountryMask, waterTexture;
    public List<Texture> neighbouringCountriesMasks;
    float screenScaleFactor;
    GestureHandlerMap gestureHandlerMap;
    private Array<GameUnit> units;
    Stage stage;
    InputMultiplexer multiplexer;
    OverlayShowNewRiskCard overlayShowNewRiskCard;
    OverlayShowAllRiskCards overlayShowAllRiskCards;
    float bgWidthScaled ;
    float bgHeightScaled;

    public GameMap(int screenHeight, int screenWidth) {
        initializeComponents(screenWidth, screenHeight);
        initializeOverlays();
    }

    private void initializeComponents(int screenWidth, int screenHeight) {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.setToOrtho(false);
        stage = new Stage(new ScreenViewport(camera));

        background = new Texture("riskMapWhite.png");
        waterTexture = new Texture("woah.png");
        waterTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        screenScaleFactor = (float) Gdx.graphics.getHeight() / background.getHeight();

        bgHeightScaled = Gdx.graphics.getHeight();
        bgWidthScaled = background.getWidth() * screenScaleFactor * 4;

        units = new Array<>();
        neighbouringCountriesMasks = new ArrayList<>();

        gestureHandlerMap = new GestureHandlerMap(camera, background, screenScaleFactor, this);
    }

    public void configureInput(InputMultiplexer multiplexer) {
        this.multiplexer = multiplexer;
        multiplexer.addProcessor(new GestureDetector(gestureHandlerMap));
        stage = new Stage(new ScreenViewport());
        multiplexer.addProcessor(stage);
    }

    private void initializeOverlays() {
        overlayShowNewRiskCard = new OverlayShowNewRiskCard(stage, multiplexer, camera);
        overlayShowAllRiskCards = new OverlayShowAllRiskCards(stage, multiplexer, camera);
    }

    public void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        drawBackground();
        drawTerritories();

        for (GameUnit unit : units) {
            unit.draw(batch, camera.zoom);
        }

        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void drawTerritories() {
        if (backgroundCountryMask != null) {
            batch.setColor(1, 1, 1, 0.5f);  // Selected Territory
            batch.draw(backgroundCountryMask, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight());
        }

        if (neighbouringCountriesMasks != null) {
            batch.setColor(1, 1, 1, 0.3f);  // Neighbor Selected Territory
            neighbouringCountriesMasks.forEach(mask -> batch.draw(mask, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight()));
        }

        batch.setColor(Color.WHITE);  // Reset color to default after drawing
    }

    private void drawTerritoryColors() {
//        batch.setColor(Color.RED);
//        if (backgroundCountryMask != null) {
//            batch.draw(backgroundCountryMask, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight());
//        }
    }

    private void drawBackground() {
        waterOffsetX += waterSpeedX * Gdx.graphics.getDeltaTime();
        waterOffsetY += waterSpeedY * Gdx.graphics.getDeltaTime();
        float bgWidthScaled = background.getWidth() * screenScaleFactor * 4;
        float bgHeightScaled = Gdx.graphics.getHeight();

        batch.draw(waterTexture, 0, 0, bgWidthScaled, bgHeightScaled, waterOffsetX, waterOffsetY, (float) ((bgWidthScaled / waterTexture.getWidth() + waterOffsetX)*1.5), (float) ((bgHeightScaled / waterTexture.getHeight() + waterOffsetY)*1.5));
        batch.draw(background, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight());
    }

    public void addUnit(GameUnit unit) {
        units.add(unit);
    }

    public void onCountryClickedApplyTextureMask(Texture textureMask) {
        backgroundCountryMask = textureMask;
    }

    public void onCountryClickedApplyTextureMaskToNeighbouringCountries(List<Texture> textureMasks) {
        neighbouringCountriesMasks = textureMasks;
    }

    public void clearCountryTextureMasks() {
        backgroundCountryMask = null;
        neighbouringCountriesMasks = null;
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        waterTexture.dispose();
        stage.dispose();
        if (backgroundCountryMask != null)
            backgroundCountryMask.dispose();
        neighbouringCountriesMasks.forEach(Texture::dispose);
        units.forEach(GameUnit::dispose);
    }

    public InputMultiplexer getInputMultiplexer() {
        return multiplexer;
    }
}