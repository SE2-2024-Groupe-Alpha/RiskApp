package se2.alpha.riskapp.service;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.UUID;

import javax.inject.Inject;

import se2.alpha.riskapp.model.dol.Player;
import se2.alpha.riskapp.model.websocket.CreateGameWebsocketMessage;

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

    private ArrayList<Player> getPlayers()
    {
        ArrayList<Player> players = new ArrayList<Player>();
        for(String player: gameService.getUserStates().getValue().keySet())
            players.add(new Player(UUID.randomUUID().toString(), player, Color.RED));
        return players;
    }
}
