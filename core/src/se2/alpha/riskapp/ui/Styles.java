package se2.alpha.riskapp.ui;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.TextureRegion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Styles {

    public static TextButton.TextButtonStyle TextButtonStyle() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("riskcardFont.fnt"));
        textButtonStyle.fontColor = Color.BLACK;

        // Button Hintergrund
        NinePatchDrawable npd = new NinePatchDrawable();
        npd.setPatch(new NinePatch(new Texture(createBackgroundTexture()), 10, 10, 10, 10));
        textButtonStyle.up = npd;

        // Button Hintergrund, wenn er gedrückt wird
        npd = new NinePatchDrawable();
        npd.setPatch(new NinePatch(new Texture(createBackgroundDownTexture()), 10, 10, 10, 10));
        textButtonStyle.down = npd;

        return textButtonStyle;
    }

    private static Pixmap createBackgroundTexture() {
        Pixmap background = new Pixmap(300, 300, Pixmap.Format.RGB888);
        background.setColor(Color.LIGHT_GRAY);
        background.fill();
        return background;
    }

    private static Pixmap createBackgroundDownTexture() {
        Pixmap backgroundDown = new Pixmap(300, 300, Pixmap.Format.RGB888);
        backgroundDown.setColor(Color.DARK_GRAY);
        backgroundDown.fill();
        return backgroundDown;
    }
}
