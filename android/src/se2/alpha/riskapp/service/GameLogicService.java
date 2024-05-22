package se2.alpha.riskapp.service;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.UUID;

import javax.inject.Inject;

import se2.alpha.riskapp.model.dol.Player;
import se2.alpha.riskapp.model.websocket.AttackWebsocketMessage;
import se2.alpha.riskapp.model.websocket.CreateGameWebsocketMessage;
import se2.alpha.riskapp.model.websocket.EndTurnWebsocketMessage;
import se2.alpha.riskapp.model.websocket.MoveTroopsWebsocketMessage;
import se2.alpha.riskapp.model.websocket.SeizeCountryWebsocketMessage;
import se2.alpha.riskapp.model.websocket.StrengthenCountryWebsocketMessage;

public class GameLogicService {
    @Inject
    BackendService backendService;
    @Inject
    GameService gameService;

    @Inject
    public GameLogicService(Context context, GameService gameService, BackendService backendService) {
        this.gameService = gameService;
        this.backendService = backendService;
    }

    public void createGame(){
        CreateGameWebsocketMessage createGameWebsocketMessage = new CreateGameWebsocketMessage(gameService.getSessionId(), getPlayers());
        backendService.sendMessage(createGameWebsocketMessage);
    }

    public void endTurn(){
        EndTurnWebsocketMessage endTurnWebsocketMessage = new EndTurnWebsocketMessage(gameService.getSessionId());
        backendService.sendMessage(endTurnWebsocketMessage);
    }

    public void seizeCountry(String playerId, String countryName, int numberOfTroops){
        SeizeCountryWebsocketMessage seizeCountryWebsocketMessage = new SeizeCountryWebsocketMessage(gameService.getSessionId(), playerId, countryName, numberOfTroops);
        backendService.sendMessage(seizeCountryWebsocketMessage);
    }

    public void strengthenCountry(String playerId, String countryName, int numberOfTroops){
        StrengthenCountryWebsocketMessage strengthenCountryWebsocketMessage = new StrengthenCountryWebsocketMessage(gameService.getSessionId(), playerId, countryName, numberOfTroops);
        backendService.sendMessage(strengthenCountryWebsocketMessage);
    }

    public void moveTroops(String playerId, String moveFromCountryName, String moveToCountryName, int numberOfTroops){
        MoveTroopsWebsocketMessage moveTroopsWebsocketMessage = new MoveTroopsWebsocketMessage(gameService.getSessionId(), playerId, moveFromCountryName, moveToCountryName, numberOfTroops);
        backendService.sendMessage(moveTroopsWebsocketMessage);
    }

    public void attack(String attackPlayerId, String defenderPlayerId, String attackingCountryName, String defendingCountryName){
        AttackWebsocketMessage attackWebsocketMessage = new AttackWebsocketMessage(gameService.getSessionId(), attackPlayerId, defenderPlayerId, attackingCountryName, defendingCountryName);
        backendService.sendMessage(attackWebsocketMessage);
    }

    private ArrayList<Player> getPlayers()
    {
        ArrayList<Player> players = new ArrayList<Player>();
        int playerCount = 0;
        for(String player: gameService.getUserStates().getValue().keySet())
        {
            players.add(new Player(UUID.randomUUID().toString(), player, getPlayerColor(++playerCount)));
        }
        return players;
    }
    private int getPlayerColor(int playerCount)
    {
        switch(playerCount)
        {
            case 1:
                return Color.RED;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.MAGENTA;
            case 6:
                return Color.CYAN;
        }
        return 0;
    }
}
