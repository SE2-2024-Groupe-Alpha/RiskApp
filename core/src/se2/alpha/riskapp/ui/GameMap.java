package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import se2.alpha.riskapp.GameUnit;
import se2.alpha.riskapp.GestureHandler;

public class GameMap implements Disposable {
    SpriteBatch batch;
    OrthographicCamera camera;
    float waterSpeedX = 0.1f, waterSpeedY = 0.05f, waterOffsetX = 0, waterOffsetY = 0;
    int screenHeight;
    int screenWidth;
    Texture background, waterTexture;
    float screenScaleFactor;
    GestureHandler gestureHandler;
    private Array<GameUnit> units;
    Stage stage;
    InputMultiplexer multiplexer;
    TextButton buttonShowNewRiskCard, buttonShowAllRiskCards;
    OverlayShowNewRiskCard overlayShowNewRiskCard;
    OverlayShowAllRiskCards overlayShowAllRiskCards;

    public GameMap(int screenHeight, int screenWidth) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        this.camera.setToOrtho(false);

        background = new Texture("map.png");
        waterTexture = new Texture("woah.png");
        waterTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        screenScaleFactor = (float) Gdx.graphics.getHeight() / background.getHeight();
        gestureHandler = new GestureHandler(camera, background, screenScaleFactor, this);
        units = new Array<>();

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(gestureHandler));

        stage = new Stage(new ScreenViewport());
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        this.overlayShowNewRiskCard = new OverlayShowNewRiskCard(stage, multiplexer, camera);
        this.overlayShowAllRiskCards = new OverlayShowAllRiskCards(stage, multiplexer, camera);
    }

    public void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        waterOffsetX += waterSpeedX * Gdx.graphics.getDeltaTime();
        waterOffsetY += waterSpeedY * Gdx.graphics.getDeltaTime();
        float bgWidthScaled = background.getWidth() * screenScaleFactor;
        float bgHeightScaled = Gdx.graphics.getHeight();
        batch.begin();
        batch.draw(waterTexture, 0, 0, bgWidthScaled, bgHeightScaled, waterOffsetX, waterOffsetY, (float) ((bgWidthScaled / waterTexture.getWidth() + waterOffsetX)*1.5), (float) ((bgHeightScaled / waterTexture.getHeight() + waterOffsetY)*1.5));
        batch.draw(background, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight());

        for (GameUnit unit : units) {
            unit.draw(batch, camera.zoom);
        }

        buttonShowNewRiskCard = new TextButton("Show New Risk Card", Styles.TextButtonStyle());
        buttonShowNewRiskCard.setPosition(600, 20);
        buttonShowNewRiskCard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                overlayShowNewRiskCard.show();
                buttonShowNewRiskCard.setVisible(false);
                buttonShowAllRiskCards.setVisible(false);
            }
        });

        buttonShowAllRiskCards = new TextButton("Show all Risk Cards", Styles.TextButtonStyle());
        buttonShowAllRiskCards.setPosition(600, 400);
        buttonShowAllRiskCards.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                overlayShowAllRiskCards.show();
                buttonShowAllRiskCards.setVisible(false);
                buttonShowNewRiskCard.setVisible(false);
            }
        });

        stage.addActor(buttonShowNewRiskCard);
        stage.addActor(buttonShowAllRiskCards);
        overlayShowAllRiskCards.render(batch);
        overlayShowNewRiskCard.render(batch);
        stage.act();
        stage.draw();


        batch.end();
    }

    public void addUnit(GameUnit unit) {
        units.add(unit);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        for (GameUnit unit : units) {
            unit.dispose();
        }
        stage.dispose();
    }
}
