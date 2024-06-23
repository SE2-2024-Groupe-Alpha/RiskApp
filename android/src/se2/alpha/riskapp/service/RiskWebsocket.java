package se2.alpha.riskapp.service;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import se2.alpha.riskapp.RiskGame;
import se2.alpha.riskapp.activities.Game;
import se2.alpha.riskapp.dol.Board;
import se2.alpha.riskapp.dol.Country;
import se2.alpha.riskapp.model.websocket.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RiskWebsocket extends WebSocketListener {
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final Gson gson = new Gson();
    private final Context context;
    GameService gameService;


    public RiskWebsocket(Context context, GameService gameService) {
        this.context = context;
        this.gameService = gameService;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        // Connection opened
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        // Handle text messages
        executorService.submit(() -> {
            IGameWebsocketMessage gameWebsocketMessage = gson.fromJson(text, GameWebsocketMessage.class);
            GameWebsocketMessageAction action = Objects.requireNonNull(gameWebsocketMessage.getAction());
            if (action == GameWebsocketMessageAction.USER_SYNC) {
                handleSyncUsers(text);
            } else if (action == GameWebsocketMessageAction.GAME_SYNC) {
                handleGameSync(text);
            } else if (action == GameWebsocketMessageAction.GAME_STARTED) {
                handleGameStarted(text);
            } else if (action == GameWebsocketMessageAction.NEW_TURN) {
                handleNewTurn(text);
            } else if (action == GameWebsocketMessageAction.COUNTRY_CHANGED) {
                handleCountryChanged(text);
            } else if (action == GameWebsocketMessageAction.PLAYER_ELIMINATED) {
                handlePlayerEliminated(text);
            } else if (action == GameWebsocketMessageAction.PLAYER_WON) {
                handlePlayerWon(text);
            }
        });

        Log.d("THE SOCKET IS TALKING", text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        // Handle binary messages
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        // Handle closing events
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        // Handle failure events
        Log.wtf("?!?", "HELP");
    }

    public void handleSyncUsers(String text) {
        UserSyncWebsocketMessage userSyncWebsocketMessage = gson.fromJson(text, UserSyncWebsocketMessage.class);
        gameService.updateUsers(userSyncWebsocketMessage.getUserStates());
    }

    public void handleGameStarted(String text) {
        GameStartedWebsocketMessage gameStartedWebsocketMessage = gson.fromJson(text, GameStartedWebsocketMessage.class);
        gameService.getGameStarted().postValue(true);
        gameService.updatePlayers(gameStartedWebsocketMessage.getPlayers());
        gameService.setActivePlayer(gameStartedWebsocketMessage.getActivePlayer());
    }

    public void handleGameSync(String text) {
        System.out.println("GAME SYNC RECEIVED");
        GameSyncWebsocketMessage gameSyncWebsocketMessage = gson.fromJson(text, GameSyncWebsocketMessage.class);
        gameService.getRiskGame().syncMap(gameSyncWebsocketMessage.getCountries());
        gameService.updatePlayers(gameSyncWebsocketMessage.getPlayers());
        gameService.setActivePlayer(gameSyncWebsocketMessage.getActivePlayer());
    }

    public void handleNewTurn(String text) {
        NewTurnWebsocketMessage newTurnWebsocketMessage = gson.fromJson(text, NewTurnWebsocketMessage.class);
        gameService.setActivePlayer(newTurnWebsocketMessage.getActivePlayer());
    }

    public void handleCountryChanged(String text) {
        CountryChangedWebsocketMessage countryChangedWebsocketMessage = gson.fromJson(text, CountryChangedWebsocketMessage.class);
        System.out.println("country changed - " + countryChangedWebsocketMessage.getCountryName() + " " + countryChangedWebsocketMessage.getOwnerId() + " " + countryChangedWebsocketMessage.getNumberOfTroops());


    }

    public void handlePlayerEliminated(String text) {
        PlayerEliminatedWebsocketMessage playerEliminatedWebsocketMessage = gson.fromJson(text, PlayerEliminatedWebsocketMessage.class);
    }

    public void handlePlayerWon(String text) {
        PlayerWonWebsocketMessage playerWonWebsocketMessage = gson.fromJson(text, PlayerWonWebsocketMessage.class);
        System.out.println("risklog player won " + playerWonWebsocketMessage.getWinningPlayerId());
        gameService.updateWinner(playerWonWebsocketMessage.getWinningPlayerId());
    }
}