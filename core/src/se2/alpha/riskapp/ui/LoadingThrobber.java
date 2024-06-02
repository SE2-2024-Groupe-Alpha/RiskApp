package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LoadingThrobber extends Actor {
    private Stage stage;
    private Image throbberImage;

    public LoadingThrobber(Stage stage) {
        this.stage = stage;
        initializeThrobber();
    }

    private void initializeThrobber() {
        Texture throbberTexture = new Texture(Gdx.files.internal("loading.png"));
        throbberImage = new Image(throbberTexture);

        float scaleFactor = 0.2f;
        throbberImage.setSize(throbberImage.getWidth() * scaleFactor, throbberImage.getHeight() * scaleFactor);

        throbberImage.setPosition(Gdx.graphics.getWidth() / 2f - throbberImage.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - throbberImage.getHeight() / 2f);
        throbberImage.addAction(Actions.forever(Actions.rotateBy(360, 1)));
        stage.addActor(throbberImage);
    }

    public void setVisible(boolean visible) {
        throbberImage.setVisible(visible);
    }
}

