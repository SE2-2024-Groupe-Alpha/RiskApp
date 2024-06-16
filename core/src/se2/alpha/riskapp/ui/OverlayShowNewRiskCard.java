package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;

import se2.alpha.riskapp.inputs.GestureHandlerShowNewRiskCard;

public class OverlayShowNewRiskCard {
    private Stage stage;
    private RiskCard riskCard;
    private boolean visible;
    private OrthographicCamera camera;
    private InputMultiplexer multiplexer;
    private GestureHandlerShowNewRiskCard gestureHandlerShowNewRiskCard;

    public OverlayShowNewRiskCard(Stage stage, InputMultiplexer multiplexer, OrthographicCamera camera) {
        this.stage = stage;
        this.camera = camera;
        this.multiplexer = multiplexer;

        visible = false;
        gestureHandlerShowNewRiskCard = new GestureHandlerShowNewRiskCard(this);
    }

    public void render(SpriteBatch batch) {
        if (visible) {
            riskCard.draw(batch, 1f, camera, stage.getViewport());

            //stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.act();
            stage.draw();
        }
    }

    public void show(RiskCard riskCard) {
        this.riskCard = riskCard;
        visible = true;
        InputMultiplexer riskCardMultiplexer = new InputMultiplexer();
        riskCardMultiplexer.addProcessor(stage);
        riskCardMultiplexer.addProcessor(new GestureDetector(gestureHandlerShowNewRiskCard));
        Gdx.input.setInputProcessor(riskCardMultiplexer);
    }

    public void hide() {
        visible = false;
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void dispose() {
        stage.dispose();
    }
}
