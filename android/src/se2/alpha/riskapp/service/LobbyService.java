package se2.alpha.riskapp.service;

import android.content.Context;
import org.json.JSONException;
import se2.alpha.riskapp.model.game.CreateLobbyRequest;
import se2.alpha.riskapp.model.game.CreateLobbyResponse;
import se2.alpha.riskapp.model.game.GameSession;
import se2.alpha.riskapp.model.game.GameState;
import se2.alpha.riskapp.model.websocket.JoinWebsocketMessage;
import se2.alpha.riskapp.model.websocket.UserLeaveWebsocketMessage;
import se2.alpha.riskapp.model.websocket.UserReadyWebsocketMessage;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LobbyService {

    @Inject
    BackendService backendService;
    @Inject
    GameService gameService;

    @Inject
    public LobbyService(Context context, GameService gameService, BackendService backendService) {
        this.gameService = gameService;
        this.backendService = backendService;
    }

    public void joinLobby(UUID sessionID){
        JoinWebsocketMessage joinWebsocketMessage = new JoinWebsocketMessage(sessionID);
        backendService.sendMessage(joinWebsocketMessage);
        gameService.setSessionId(sessionID);
    }

    public void leaveLobby(){
        UserLeaveWebsocketMessage userLeaveWebsocketMessage = new UserLeaveWebsocketMessage(gameService.getSessionId());
        backendService.sendMessage(userLeaveWebsocketMessage);
    }

    public interface LobbyCreationResultCallback {
        void onResult(boolean success);
    }

    public void createLobby(String lobbyTitle, LobbyCreationResultCallback resultCallback) {
        CreateLobbyRequest createLobbyRequest = new CreateLobbyRequest(lobbyTitle);
        try {
            backendService.createLobbyRequest(createLobbyRequest, new BackendService.CreateLobbyCallback() {
                @Override
                public void onSuccess(CreateLobbyResponse response) {
                    joinLobby(response.getGameSessionId());
                    resultCallback.onResult(true);
                }

                @Override
                public void onError(String error) {
                    resultCallback.onResult(false);
                }
            });
        } catch (JSONException e) {
            resultCallback.onResult(false);
        }
    }

    public interface GetAvailableLobbiesCallback {
        void onResult(List<GameSession> gameSessions);
        void onError(String error);
    }

    public void getAvailableLobbies(GetAvailableLobbiesCallback getAvailableLobbiesCallback) {
        backendService.makeLobbyRequest(new BackendService.LobbyCallback() {
            @Override
            public void onSuccess(List<GameSession> response) {
                List<GameSession> filteredLobbies = response.stream()
                        .filter(gameSession -> gameSession.getState() == GameState.LOBBY && gameSession.getUsers() < 6)
                        .collect(Collectors.toList());

                getAvailableLobbiesCallback.onResult(filteredLobbies);
            }

            @Override
            public void onError(String error) {
                getAvailableLobbiesCallback.onError(error);
            }
        });
    }

    public void updatePlayerStatus(boolean isReady){
        UserReadyWebsocketMessage rdyMsg = new UserReadyWebsocketMessage(gameService.getSessionId(), isReady);
        backendService.sendMessage(rdyMsg);
    }

}
