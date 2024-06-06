package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import se2.alpha.riskapp.events.InitiateAttackEvent;
import se2.alpha.riskapp.events.TerritoryAttackEvent;
import se2.alpha.riskapp.events.TerritoryClickedClearEvent;
import se2.alpha.riskapp.events.TerritoryClickedEvent;
import se2.alpha.riskapp.logic.EventBus;

public class BottomBar implements Disposable {
    private Stage stage;
    private OrthographicCamera uiCamera;
    private TextButton buttonRiskCards, buttonDiceRoll;
    private TextButton buttonAttack, buttonReinforce;
    private int screenHeight;
    private int screenWidth;

    public BottomBar(int screenHeight, int screenWidth, Skin skin) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Set up the camera
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, screenWidth, screenHeight);
        stage = new Stage(new ScreenViewport(uiCamera));

        // Initialize buttons
        initializeButtons(skin);

        EventBus.registerCallback(TerritoryClickedEvent.class, event -> {
            disableButtons(false);
        });

        EventBus.registerCallback(TerritoryClickedClearEvent.class, event -> {
            disableButtons(true);
        });
    }

    public void configureInput(InputMultiplexer multiplexer) {
        multiplexer.addProcessor(stage);
    }

    private void initializeButtons(Skin skin) {
        buttonRiskCards = new TextButton("Show Cards", skin);
        buttonRiskCards.setSize(400, 100);
        buttonRiskCards.setPosition(20, 70);

        buttonDiceRoll = new TextButton("Roll Dice", skin);
        buttonDiceRoll.setSize(400, 100);
        buttonDiceRoll.setPosition(screenWidth - buttonDiceRoll.getWidth() - 20, 70); // Adjust position

        buttonRiskCards.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked CARDS");
                event.stop();
            }
        });

        buttonDiceRoll.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked ROLL");
                event.stop();
            }
        });

        buttonAttack = new TextButton("Attack", skin);
        buttonAttack.setSize(400, 100);
        buttonAttack.setPosition(20, 200);

        buttonReinforce = new TextButton("Reinforce", skin);
        buttonReinforce.setSize(400, 100);
        buttonReinforce.setPosition(screenWidth - buttonDiceRoll.getWidth() - 20, 200); // Adjust position

        buttonAttack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EventBus.invoke(new InitiateAttackEvent());
                System.out.println("Clicked ATTACK");
                event.stop();
            }
        });

        buttonReinforce.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked REINFORCE");
                event.stop();
            }
        });

        stage.addActor(buttonRiskCards);
        stage.addActor(buttonDiceRoll);
        stage.addActor(buttonAttack);
        stage.addActor(buttonReinforce);
    }

    public void draw() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void disableButtons(boolean active) {
        buttonRiskCards.setDisabled(active);
        buttonDiceRoll.setDisabled(active);
        buttonAttack.setDisabled(active);
        buttonReinforce.setDisabled(active);
    }
}
