package se2.alpha.riskapp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import se2.alpha.riskapp.ui.GameMap;
import se2.alpha.riskapp.ui.TopBar;


public class RiskGame extends ApplicationAdapter {
	TopBar topBar;
	GameMap gameMap;
	int screenHeight;
	int screenWidth;


	@Override
	public void create () {
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();

		topBar = new TopBar(screenHeight, screenWidth);
		gameMap = new GameMap(screenHeight, screenWidth);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		gameMap.draw();
		topBar.draw();
	}

	@Override
	public void dispose () {
		gameMap.dispose();
		topBar.dispose();
	}
}
