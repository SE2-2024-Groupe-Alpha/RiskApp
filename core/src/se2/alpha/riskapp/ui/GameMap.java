package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import se2.alpha.riskapp.GameUnit;
import se2.alpha.riskapp.RiskGame;
import se2.alpha.riskapp.dol.Board;
import se2.alpha.riskapp.dol.Continent;
import se2.alpha.riskapp.dol.Country;
import se2.alpha.riskapp.dol.RiskCard;
import se2.alpha.riskapp.inputs.GestureHandlerMap;
import se2.alpha.riskapp.utils.Territories;
import se2.alpha.riskapp.utils.TerritoryNode;

public class GameMap implements Disposable {
    SpriteBatch batch;
    OrthographicCamera camera;
    float waterSpeedX = 0.1f, waterSpeedY = 0.05f, waterOffsetX = 0, waterOffsetY = 0;
    public Texture background, backgroundCountryMask, waterTexture;
    public List<Texture> neighbouringCountriesMasks;
    float screenScaleFactor;
    GestureHandlerMap gestureHandlerMap;
    Stage stage;
    InputMultiplexer multiplexer;
    OverlayShowNewRiskCard overlayShowNewRiskCard;
    OverlayShowAllRiskCards overlayShowAllRiskCards;
    float bgWidthScaled ;
    float bgHeightScaled;
    public Board board;

    private Skin skin;

    public GameMap(int screenHeight, int screenWidth, Board board, Skin skin) {
        initializeComponents(screenWidth, screenHeight);
        initializeOverlays();
        this.board = board;
        this.skin = skin;
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

        neighbouringCountriesMasks = new ArrayList<>();

        gestureHandlerMap = new GestureHandlerMap(camera, background, screenScaleFactor, this);
    }

    public void configureInput(InputMultiplexer multiplexer) {
        this.multiplexer = multiplexer;
        multiplexer.addProcessor(new GestureDetector(gestureHandlerMap));
        stage = new Stage(new ScreenViewport());
        multiplexer.addProcessor(stage);
        overlayShowAllRiskCards.configureInput(multiplexer);
    }

    private void initializeOverlays() {
        overlayShowNewRiskCard = new OverlayShowNewRiskCard(stage, multiplexer, camera);
        overlayShowAllRiskCards = new OverlayShowAllRiskCards(stage, camera);
    }

    public void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        drawBackground();
        drawPlayerColors();
        drawTerritories();
        drawTroopNumbers();

        overlayShowAllRiskCards.render(batch);

        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void drawTerritories() {
        if (backgroundCountryMask != null) {
            batch.setColor(0, 0, 0, 0.5f);  // Selected Territory
            batch.draw(backgroundCountryMask, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight());
        }

        if (neighbouringCountriesMasks != null) {
            batch.setColor(0, 0, 0, 0.3f);  // Neighbor Selected Territory
            neighbouringCountriesMasks.forEach(mask -> batch.draw(mask, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight()));
        }

        batch.setColor(Color.WHITE);
    }

    private void drawTroopNumbers() {
        Matrix4 originalProjection = new Matrix4(batch.getProjectionMatrix());
        batch.getProjectionMatrix().setToOrtho2D(0, 0, bgWidthScaled, bgHeightScaled);
        BitmapFont font = skin.getFont("default-font");

        font.getData().setScale(4.0f);
        font.setColor(Color.WHITE);

        for (Continent continent : board.getContinents()){
            for (Country country : continent.getCountries()){
                int troops = country.getNumberOfTroops();
                TerritoryNode node = Territories.getTerritoryByName(country.getName());

                float x = (float) (node.getX());
                float y = (float) (node.getY());

                font.draw(batch, Integer.toString(troops), x, y);
            }
        }

        font.dispose();
        batch.setProjectionMatrix(originalProjection);
    }

    public void drawPlayerColors(){
        for (Continent continent: board.getContinents()){
            for (Country country: continent.getCountries()){
                if (country.getOwner()!= null){
                    if (country.getMask() == null){
                        Texture territoryMask = Territories.getTerritoryByName(country.getName()).getMask();
                        country.setMask(territoryMask);
                    }

                    Color color = RiskGame.getInstance().getPlayerColor(country.getOwner().getId());

                    batch.setColor(color);
                    batch.draw(country.getMask(), 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight());
                    batch.setColor(Color.WHITE);
                }
            }
        }
    }

    private void drawBackground() {
        waterOffsetX += waterSpeedX * Gdx.graphics.getDeltaTime();
        waterOffsetY += waterSpeedY * Gdx.graphics.getDeltaTime();
        float bgWidthScaled = background.getWidth() * screenScaleFactor * 4;
        float bgHeightScaled = Gdx.graphics.getHeight();

        batch.draw(waterTexture, 0, 0, bgWidthScaled, bgHeightScaled, waterOffsetX, waterOffsetY, (float) ((bgWidthScaled / waterTexture.getWidth() + waterOffsetX)*1.5), (float) ((bgHeightScaled / waterTexture.getHeight() + waterOffsetY)*1.5));
        batch.draw(background, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight());
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

    public void showAllRiskCards(List<RiskCard> riskCards)
    {
        List<se2.alpha.riskapp.ui.RiskCard> uiRiskCards = new ArrayList<>();
        for(RiskCard riskCard : riskCards)
            uiRiskCards.add(new se2.alpha.riskapp.ui.RiskCard(riskCard.getType(), riskCard.getCountryName()));
        overlayShowAllRiskCards.show(uiRiskCards);
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
    }
}