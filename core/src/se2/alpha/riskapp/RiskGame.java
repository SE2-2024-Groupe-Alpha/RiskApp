package se2.alpha.riskapp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import se2.alpha.riskapp.ui.GameMap;
import se2.alpha.riskapp.ui.TopBar;


public class RiskGame extends ApplicationAdapter {
	private TopBar topBar;
	private GameMap gameMap;
	private int screenHeight;
	private int screenWidth;

	@Override
	public void create() {
		initializeScreenDimensions();
		initializeGameComponents();
		topBar.setTopBarText("Game Started!");
		Gdx.app.log("RiskGame", "Game created.");
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);
		gameMap.draw();
		topBar.draw();
	}

	@Override
	public void dispose() {
		if (gameMap != null) {
			gameMap.dispose();
		}

		if (topBar != null) {
			topBar.dispose();
		}

		Gdx.app.log("RiskGame", "Game disposed.");
	}

	private void initializeScreenDimensions() {
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		Gdx.app.log("RiskGame", "Screen dimensions initialized: width=" + screenWidth + ", height=" + screenHeight);
	}

	private void initializeGameComponents() {
		topBar = new TopBar(screenHeight, screenWidth);
		gameMap = new GameMap(screenHeight, screenWidth);
		Gdx.app.log("RiskGame", "Game components initialized.");
	}
}
