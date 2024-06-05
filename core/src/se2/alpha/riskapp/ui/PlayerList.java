package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerList implements Disposable {
    private Stage stage;
    private OrthographicCamera uiCamera;
    private int screenHeight;
    private int screenWidth;
    private List<Label> playerLabels;
    private Skin skin;

    public PlayerList(int screenHeight, int screenWidth, Skin skin) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.playerLabels = new ArrayList<>();
        this.skin = skin;

        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, screenWidth, screenHeight);
        stage = new Stage(new ScreenViewport(uiCamera));
    }

    public void initializePlayerLabels(Map<String, Color> playerNamesColorMap) {
        int cnt = 1;

        for (Map.Entry<String, Color> entry : playerNamesColorMap.entrySet()) {
            Label.LabelStyle style = new Label.LabelStyle();
            style.font = skin.getFont("default-font");
            style.font.getData().setScale(3f);
            style.background = skin.newDrawable("white", entry.getValue());
            Label playerLabel = new Label(entry.getKey(), style);
            playerLabel.setSize(250, 100); // Set the desired size
            playerLabel.setPosition(screenWidth - 200, screenHeight - (cnt * 100 + 250)); // Adjust position vertically
            playerLabels.add(playerLabel);
            stage.addActor(playerLabel);
            cnt++;
        }
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
