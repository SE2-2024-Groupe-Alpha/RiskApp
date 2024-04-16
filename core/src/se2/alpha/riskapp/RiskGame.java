package se2.alpha.riskapp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;


public class RiskGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background, waterTexture;

	float waterSpeedX = 0.1f, waterSpeedY = 0.05f, waterOffsetX = 0, waterOffsetY = 0;

	int screenHeight;
	int screenWidth;
	float screenScaleFactor;
	GestureHandler gestureHandler;

	OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("map.png");
		waterTexture = new Texture("woah.png");
		waterTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);


		screenScaleFactor = (float) Gdx.graphics.getHeight() / background.getHeight();


		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();

		camera = new OrthographicCamera(screenWidth, screenHeight);
		camera.setToOrtho(false);

		gestureHandler = new GestureHandler(camera, background, screenScaleFactor);

		Gdx.input.setInputProcessor(new GestureDetector(gestureHandler));
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		waterOffsetX += waterSpeedX * Gdx.graphics.getDeltaTime();
		waterOffsetY += waterSpeedY * Gdx.graphics.getDeltaTime();

		float bgWidthScaled = background.getWidth() * screenScaleFactor;
		float bgHeightScaled = Gdx.graphics.getHeight();

		batch.begin();
		batch.draw(waterTexture, 0, 0, bgWidthScaled, bgHeightScaled,
				waterOffsetX, waterOffsetY,
                (float) ((bgWidthScaled / waterTexture.getWidth() + waterOffsetX)*1.5),
                (float) ((bgHeightScaled / waterTexture.getHeight() + waterOffsetY)*1.5));

		batch.draw(background, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight());
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
