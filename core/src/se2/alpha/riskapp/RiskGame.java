package se2.alpha.riskapp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import se2.alpha.riskapp.dol.Board;
import se2.alpha.riskapp.dol.Country;
import se2.alpha.riskapp.dol.Player;
import se2.alpha.riskapp.dol.RiskCard;
import se2.alpha.riskapp.events.TerritoryClickedEvent;
import se2.alpha.riskapp.logic.EventBus;
import se2.alpha.riskapp.ui.*;
import se2.alpha.riskapp.utils.TerritoryNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RiskGame extends ApplicationAdapter {
	private TopBar topBar;
	private BottomBar bottomBar;
	private PlayerList playerList;
	private TroopCardList troopCardList;
	private GameMap gameMap;
	private int screenHeight;
	private int screenWidth;
	private static RiskGame riskGameInstance;
	private Board board = new Board();
	private List<Player> players = new ArrayList<>();
	private static TerritoryNode selectedTerritory;
	private String playerName;
	private boolean isActive = true;


	public Color getPlayerColor(String id) {
		for (Player player : players) {
			if (player.getId().equals(id)) {
				switch(player.getColor())
				{
					case -65536:
						return Color.RED;
					case -16776961:
						return Color.BLUE;
					case -256:
						return Color.YELLOW;
					case -16711936:
						return Color.GREEN;
					case -65281:
						return Color.MAGENTA;
					case -16711681:
						return Color.CYAN;
				}
			}
		}
		return Color.WHITE;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	private RiskGame(){
		EventBus.registerCallback(TerritoryClickedEvent.class, event -> {
			TerritoryClickedEvent territoryClickedEvent = (TerritoryClickedEvent) event;
			RiskGame.selectedTerritory = territoryClickedEvent.getTerritory();
		});
	}

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
		playerList.draw();
		troopCardList.draw();
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
		gameMap = new GameMap(screenHeight, screenWidth, board);
		playerList = new PlayerList(screenHeight, screenWidth, skin);
		troopCardList = new TroopCardList(screenHeight, screenWidth, skin);
		bottomBar = new BottomBar(screenHeight, screenWidth, skin);

		Map<String, Color> playerNamesColorMap = new HashMap<>();

		for (Player player : players) {
			playerNamesColorMap.put(player.getName(), getPlayerColor(player.getId()));
		}

		playerList.initializePlayerLabels(playerNamesColorMap);

		troopCardList.initializeInfoLabels();

		bottomBar.configureInput(multiplexer);
		gameMap.configureInput(multiplexer);

		Gdx.app.log("RiskGame", "Game components initialized.");

		Gdx.input.setInputProcessor(multiplexer);
		bottomBar.disableButtons(!isActive);
	}

	public void syncMap(List<Country> countryList){
		board.updateCountries(countryList);
	}

	public void showRiskCards(List<RiskCard> riskCards)
	{
		gameMap.showAllRiskCards(riskCards);
	}

	public void setActive(boolean active) {
		isActive = active;
		if(bottomBar != null)
			bottomBar.disableButtons(!isActive);
	}

	public void setPlayerName(String playerName) {
		this.board.playerName = playerName;
	}
	public static TerritoryNode getSelectedTerritory() {
		return selectedTerritory;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public boolean isActive() {
		return this.isActive;
	}
	public String getPlayerName() {
		return playerName;
	}
}
