package se2.alpha.riskapp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;


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

		Gdx.input.setInputProcessor(getInputProcessor());
	}

	private InputProcessor getInputProcessor() {
		return new InputAdapter() {
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				Vector3 newTouch = new Vector3(screenX, screenY, 0);
				camera.unproject(newTouch);
				Vector3 delta = newTouch.cpy().sub(touchStart);
				camera.position.sub(delta.x, delta.y, 0);
				touchStart.set(newTouch);
				return true;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				camera.unproject(touchStart.set(screenX, screenY, 0));
				return true;
			}

			@Override
			public boolean scrolled(float amountX, float amountY) {
				camera.zoom += (float) (amountY * 0.1);
				camera.zoom = Math.max(0.1f, camera.zoom); // Avoid inverting the camera with too much zoom out
				return true;
			}
		};
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
