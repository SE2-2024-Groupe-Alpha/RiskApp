package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import se2.alpha.riskapp.GestureHandler;

public class BottomBar implements Disposable {
    private Stage stage;
    private OrthographicCamera uiCamera;
    private TextButton buttonOne, buttonTwo;
    private int screenHeight;
    private int screenWidth;

    public BottomBar(int screenHeight, int screenWidth, Skin skin, InputMultiplexer multiplexer) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Set up the camera
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, screenWidth, screenHeight);
        stage = new Stage(new ScreenViewport(uiCamera));

        // Initialize buttons
        initializeButtons(skin);
        multiplexer.addProcessor(stage);
    }

    private void initializeButtons(Skin skin) {
        buttonOne = new TextButton("Show Cards", skin);
        buttonOne.setSize(400, 100);
        buttonOne.setPosition(20, 70);

        buttonTwo = new TextButton("Roll Dice", skin);
        buttonTwo.setSize(400, 100);
        buttonTwo.setPosition(screenWidth - buttonTwo.getWidth() - 20, 70); // Adjust position

        stage.addActor(buttonOne);
        stage.addActor(buttonTwo);
    }

    public void draw() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
