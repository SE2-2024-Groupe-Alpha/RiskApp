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
	Texture background;
	int screenHeight;
	int screenWidth;
	float screenScaleFactor;

	OrthographicCamera camera;
	Vector3 touchStart = new Vector3();

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("map.png");

		screenScaleFactor = (float) Gdx.graphics.getHeight() / background.getHeight();


		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();

		camera = new OrthographicCamera(screenWidth, screenHeight);
		camera.setToOrtho(false);

		GestureHandler gestureHandler = new GestureHandler(camera, background);

		Gdx.input.setInputProcessor(new GestureDetector(gestureHandler));
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(background, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight());
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
