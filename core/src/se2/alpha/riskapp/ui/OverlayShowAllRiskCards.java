package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

import se2.alpha.riskapp.inputs.GestureHandlerShowAllRiskCards;

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
    private final float relativeButtonWidth = 0.1f;
    private final float relativeButtonHeight = 0.1f;

    public OverlayShowAllRiskCards(Stage stage, InputMultiplexer multiplexer, OrthographicCamera camera) {
        this.stage = stage;
        this.camera = camera;
        this.multiplexer = multiplexer;

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

    public void show(ArrayList<RiskCard> riskCards) {
        if(!riskCards.isEmpty()) {
            this.riskCards = riskCards;
            visible = true;
            buttonLeft.setVisible(true);
            buttonRight.setVisible(true);
            InputMultiplexer riskCardMultiplexer = new InputMultiplexer();
            riskCardMultiplexer.addProcessor(stage);
            riskCardMultiplexer.addProcessor(new GestureDetector(riskCardGestureHandler));
            Gdx.input.setInputProcessor(riskCardMultiplexer);
            riskCard = riskCards.get(0);
        }
        else
            hide();
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
        buttonLeft.setSize(Gdx.graphics.getWidth() * relativeButtonWidth, Gdx.graphics.getHeight() * relativeButtonHeight);
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
        buttonRight.setSize(Gdx.graphics.getWidth() * relativeButtonWidth, Gdx.graphics.getHeight() * relativeButtonHeight);
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
