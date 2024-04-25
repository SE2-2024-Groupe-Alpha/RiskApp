package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import se2.alpha.riskapp.GestureHandlerShowNewRiskCard;
import se2.alpha.riskapp.RiskCardType;

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

        riskCard = new RiskCard(RiskCardType.ARTILLERY, new Vector2(50, 50), "Democratic Republic of Congo");
        visible = false;
        gestureHandlerShowNewRiskCard = new GestureHandlerShowNewRiskCard(this);
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
