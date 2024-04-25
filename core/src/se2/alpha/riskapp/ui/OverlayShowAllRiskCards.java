package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

import se2.alpha.riskapp.GestureHandlerShowAllRiskCards;
import se2.alpha.riskapp.RiskCardType;

public class OverlayShowAllRiskCards {
    private Stage stage;
    private ImageButton buttonLeft, buttonRight;
    private RiskCard riskCard;
    private boolean visible;
    private OrthographicCamera camera;
    private ArrayList<RiskCard> riskCards;
    private int idxCards = 0;
    private GestureHandlerShowAllRiskCards riskCardGestureHandler;
    private InputMultiplexer multiplexer;

    public OverlayShowAllRiskCards(Stage stage, InputMultiplexer multiplexer, OrthographicCamera camera) {
        this.stage = stage;
        this.camera = camera;
        this.multiplexer = multiplexer;

        riskCards = new ArrayList<RiskCard>();
        riskCards.add(new RiskCard(RiskCardType.INFANTRY, new Vector2(50, 50), "Germany"));
        riskCards.add(new RiskCard(RiskCardType.CAVALRY, new Vector2(50, 50), "Argentina"));
        riskCards.add(new RiskCard(RiskCardType.ARTILLERY, new Vector2(50, 50), "Democratic Republic of Congo"));
        riskCard = riskCards.get(idxCards);

        createButtonLeft();
        createButtonRight();

        stage.addActor(buttonLeft);
        stage.addActor(buttonRight);

        visible = false;

        riskCardGestureHandler = new GestureHandlerShowAllRiskCards(this, buttonLeft, buttonRight);
    }

    public void render(SpriteBatch batch) {
        if (visible) {
            riskCard.draw(batch, 1f, camera);

            //stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.act();
            stage.draw();
        }
    }

    public void show() {
        visible = true;
        buttonLeft.setVisible(true);
        buttonRight.setVisible(true);
        InputMultiplexer riskCardMultiplexer = new InputMultiplexer();
        riskCardMultiplexer.addProcessor(stage);
        riskCardMultiplexer.addProcessor(new GestureDetector(riskCardGestureHandler));
        Gdx.input.setInputProcessor(riskCardMultiplexer);
    }

    public void hide() {
        visible = false;
        buttonLeft.setVisible(false);
        buttonRight.setVisible(false);
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void dispose() {
        stage.dispose();
    }

    private void changeImageRight() {
        if(++idxCards >= riskCards.size())
            idxCards = 0;
        riskCard = riskCards.get(idxCards);
    }

    private void changeImageLeft() {
        if(--idxCards < 0)
            idxCards = riskCards.isEmpty() ? 0 : riskCards.size() - 1;
        riskCard = riskCards.get(idxCards);
    }

    private void createButtonLeft()
    {
        ImageButton.ImageButtonStyle styleButtonLeft = new ImageButton.ImageButtonStyle();
        styleButtonLeft.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("button_left.png"))));

        buttonLeft = new ImageButton(styleButtonLeft);
        buttonLeft.setSize(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getHeight() / 2f);
        buttonLeft.setPosition(0, Gdx.graphics.getHeight() / 2f - buttonLeft.getHeight() / 2f);
        buttonLeft.setVisible(false);
        buttonLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeImageLeft();
            }
        });
    }

    private void createButtonRight()
    {
        ImageButton.ImageButtonStyle styleButtonRight = new ImageButton.ImageButtonStyle();
        styleButtonRight.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("button_right.jpg"))));

        buttonRight = new ImageButton(styleButtonRight);
        buttonRight.setSize(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getHeight() / 2f);
        buttonRight.setPosition(Gdx.graphics.getWidth() - buttonRight.getWidth(), Gdx.graphics.getHeight() / 2f - buttonRight.getHeight() / 2f);
        buttonRight.setVisible(false);
        buttonRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeImageRight();
            }
        });
    }
}
