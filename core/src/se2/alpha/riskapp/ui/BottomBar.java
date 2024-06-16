package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import se2.alpha.riskapp.events.*;
import se2.alpha.riskapp.logic.EventBus;

public class BottomBar implements Disposable {
    private Stage stage;
    private OrthographicCamera uiCamera;
    private TextButton buttonRiskCards, buttonDiceRoll;
    private TextButton buttonAttack, buttonReinforce, buttonEndTurn;
    private int screenHeight;
    private int screenWidth;

    private boolean reinforceEnabled = true;

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
            disableButtonsTerritoryClicked(false);
        });

        EventBus.registerCallback(TerritoryClickedClearEvent.class, event -> {
            disableButtonsTerritoryClicked(true);
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

        buttonRiskCards.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Clicked CARDS");
                EventBus.invoke(new ShowAllRiskCardsEvent());
                event.stop();
            }
        });

        buttonDiceRoll.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Clicked ROLL");
                event.stop();
            }
        });

        EventBus.registerCallback(UpdateFreeTroopsEvent.class, e -> {
            UpdateFreeTroopsEvent event = (UpdateFreeTroopsEvent) e;
            if (event.getFreeTroops() <= 0) {
                reinforceEnabled = false;
                buttonReinforce.setDisabled(true);
            } else {
                reinforceEnabled = true;
            }
        });

        buttonAttack = new TextButton("Attack", skin);
        buttonAttack.setSize(400, 100);
        buttonAttack.setPosition(20, 200);

        buttonReinforce = new TextButton("Reinforce", skin);
        buttonReinforce.setSize(400, 100);
        buttonReinforce.setPosition(screenWidth - buttonDiceRoll.getWidth() - 20, 200); // Adjust position

        buttonAttack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                EventBus.invoke(new InitiateAttackEvent());
                System.out.println("Clicked ATTACK");
                event.stop();
            }
        });

        buttonReinforce.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Clicked REINFORCE");
                EventBus.invoke(new TerritoryReinforceEvent());
                event.stop();
            }
        });

        buttonEndTurn = new TextButton("End Turn", skin);
        buttonEndTurn.setSize(400, 100);
        buttonEndTurn.setPosition(20, 350);

        buttonEndTurn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                EventBus.invoke(new EndTurnEvent());
                System.out.println("Clicked END TURN");
                event.stop();
            }
        });

        stage.addActor(buttonRiskCards);
        stage.addActor(buttonDiceRoll);
        stage.addActor(buttonAttack);
        stage.addActor(buttonReinforce);
        stage.addActor(buttonEndTurn);
    }

    public void draw() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void disableButtons(boolean inactive) {
        buttonRiskCards.setDisabled(inactive);
        buttonDiceRoll.setDisabled(inactive);
        buttonAttack.setDisabled(inactive);
        if(reinforceEnabled) {
            buttonReinforce.setDisabled(inactive);
        }
        buttonEndTurn.setDisabled(inactive);
    }

    public void disableButtonsTerritoryClicked(boolean inactive) {
        buttonAttack.setDisabled(inactive);
        if(reinforceEnabled) {
            buttonReinforce.setDisabled(inactive);
        }
    }
}
