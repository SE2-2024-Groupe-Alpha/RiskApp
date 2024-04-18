package se2.alpha.riskapp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;


public class RiskGame extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	SpriteBatch uiBatch;
	OrthographicCamera uiCamera;
	ShapeRenderer shapeRenderer;
	Texture background, waterTexture;
	float waterSpeedX = 0.1f, waterSpeedY = 0.05f, waterOffsetX = 0, waterOffsetY = 0;
	int screenHeight;
	int screenWidth;
	float screenScaleFactor;
	GestureHandler gestureHandler;
	BitmapFont font;
	GlyphLayout layout;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(screenWidth, screenHeight);
		camera.setToOrtho(false);

		uiBatch = new SpriteBatch();
		uiCamera = new OrthographicCamera(screenWidth, screenHeight);
		uiCamera.setToOrtho(false);

		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.getData().setScale(4.0f);
		layout = new GlyphLayout();

		background = new Texture("map.png");
		waterTexture = new Texture("woah.png");
		waterTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

		screenScaleFactor = (float) Gdx.graphics.getHeight() / background.getHeight();
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
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

		// Water
		batch.begin();
		batch.draw(waterTexture, 0, 0, bgWidthScaled, bgHeightScaled, waterOffsetX, waterOffsetY, (float) ((bgWidthScaled / waterTexture.getWidth() + waterOffsetX)*1.5), (float) ((bgHeightScaled / waterTexture.getHeight() + waterOffsetY)*1.5));
		batch.draw(background, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight());
		batch.end();


		shapeRenderer.setProjectionMatrix(uiCamera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.rect(0, (float) (screenHeight * 0.95), screenWidth, (float) (screenHeight * 0.95));
		shapeRenderer.end();

		uiBatch.setProjectionMatrix(uiCamera.combined);
		uiBatch.begin();
		font.setColor(Color.WHITE);
		layout.setText(font, "Setup");
		float textWidth = layout.width;
		float textX = (screenWidth - textWidth) / 2;
		float textY = (float) (screenHeight * 0.975 + layout.height / 2);
		font.draw(uiBatch, layout, textX, textY);

		uiBatch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		uiBatch.dispose();
		background.dispose();
		font.dispose();
		shapeRenderer.dispose();
	}
}
