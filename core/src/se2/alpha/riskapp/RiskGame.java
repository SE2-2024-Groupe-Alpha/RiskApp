package se2.alpha.riskapp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;

import se2.alpha.riskapp.dol.Board;
import se2.alpha.riskapp.ui.BottomBar;
import se2.alpha.riskapp.ui.GameMap;
import se2.alpha.riskapp.ui.TopBar;


public class RiskGame extends ApplicationAdapter {
	private TopBar topBar;
	private BottomBar bottomBar;
	private GameMap gameMap;
	private int screenHeight;
	private int screenWidth;
	private static RiskGame riskGameInstance;
	private Board board;

	private RiskGame(){}

	public static RiskGame getInstance(){
		if (riskGameInstance == null){
			riskGameInstance = new RiskGame();
		}

		return riskGameInstance;
	}

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
		bottomBar.draw();
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
		Skin skin = new Skin(Gdx.files.internal("defaultUIskin/uiskin.json"));

		InputMultiplexer multiplexer = new InputMultiplexer();

		topBar = new TopBar(screenHeight, screenWidth);
		gameMap = new GameMap(screenHeight, screenWidth);
		bottomBar = new BottomBar(screenHeight, screenWidth, skin);

		bottomBar.configureInput(multiplexer);
		gameMap.configureInput(multiplexer);

		Gdx.app.log("RiskGame", "Game components initialized.");

		Gdx.input.setInputProcessor(multiplexer);
	}

	public void updateBoard(Board board){
		this.board = board;
		gameMap.drawPlayerColors(board);
	}
}
