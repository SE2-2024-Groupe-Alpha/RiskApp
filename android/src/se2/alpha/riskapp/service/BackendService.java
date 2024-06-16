package se2.alpha.riskapp.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import se2.alpha.riskapp.BuildConfig;
import se2.alpha.riskapp.events.EndTurnEvent;
import se2.alpha.riskapp.events.ShowAllRiskCardsEvent;
import se2.alpha.riskapp.events.TerritoryAttackEvent;
import se2.alpha.riskapp.events.TerritoryClickedClearEvent;
import se2.alpha.riskapp.events.TerritoryClickedEvent;
import se2.alpha.riskapp.logic.EventBus;
import se2.alpha.riskapp.model.auth.JwtAuthenticationResponse;
import se2.alpha.riskapp.model.auth.SignInRequest;
import se2.alpha.riskapp.model.auth.SignUpRequest;
import se2.alpha.riskapp.model.auth.ValidationRequest;
import se2.alpha.riskapp.model.game.CreateLobbyRequest;
import se2.alpha.riskapp.model.game.CreateLobbyResponse;
import se2.alpha.riskapp.model.game.GameSession;
import se2.alpha.riskapp.dol.RiskCard;
import se2.alpha.riskapp.model.websocket.AttackWebsocketMessage;
import se2.alpha.riskapp.model.websocket.EndTurnWebsocketMessage;
import se2.alpha.riskapp.model.websocket.ICustomWebsocketMessage;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BackendService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final Gson gson = new Gson();
    private static final String API_URL = BuildConfig.API_URL;
    private static final String WSS_URL = BuildConfig.WSS_URL;
    private static final String ENCODING = "utf-8";
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";
    private final SecurePreferencesService securePreferencesService;
    private final GameService gameService;
    private final Context context;
    private final OkHttpClient client = new OkHttpClient();
    @Getter
    private WebSocket webSocket;

    @Inject
    public BackendService(Context context, SecurePreferencesService securePreferences, GameService gameService) {
        this.securePreferencesService = securePreferences;
        this.gameService = gameService;
        this.context = context;

        EventBus.registerCallback(TerritoryAttackEvent.class, event -> {
            TerritoryAttackEvent territoryAttackEvent = (TerritoryAttackEvent) event;

            AttackWebsocketMessage attackWebsocketMessage = new AttackWebsocketMessage(
                    gameService.getSessionId(),
                    territoryAttackEvent.getAttackerPlayerId(),
                    territoryAttackEvent.getDefenderPlayerId(),
                    territoryAttackEvent.getAttackingCountryName(),
                    territoryAttackEvent.getDefendingCountryName()
            );

            sendMessage(attackWebsocketMessage);
        });

        EventBus.registerCallback(EndTurnEvent.class, event -> {
            EndTurnWebsocketMessage endTurnWebsocketMessage = new EndTurnWebsocketMessage(
                    gameService.getSessionId()
            );

            sendMessage(endTurnWebsocketMessage);
        });

        EventBus.registerCallback(ShowAllRiskCardsEvent.class, event -> {
            System.out.println("show all riskcards clicked");

            getAllRiskCardsByPlayerRequest(gameService.getPlayers().getValue().stream().filter(player -> securePreferences.getPlayerName().equals(player.getName())).findFirst()
                    .orElse(null).getId(), new BackendService.GetAllRiskCardsByPlayerCallback() {
                @Override
                public void onSuccess(List<RiskCard> response) {
                    System.out.println("show all riskcards response " + response.size());

                    for(RiskCard riskCard : response)
                        System.out.println("riskcard country: " + riskCard.getCountryName());

                    gameService.getRiskGame().showRiskCards(response);
                }

                @Override
                public void onError(String error) {
                    System.out.println("show all riskcards error");
                }
            });
        });
    }

    public String getSessionToken() {
        return securePreferencesService.getSessionToken();
    }

    public void saveSessionToken(String token) {
        securePreferencesService.saveSessionToken(token);
    }

    public void saveUserName(String userName) {
        securePreferencesService.saveUserName(userName);
    }

    public interface ReachabilityCallback {
        void onReachabilityChecked(boolean isReachable);
    }

    public interface HealthCheckCallback {
        void onHealthy();
        void onError(String error);
    }

    public void checkApiHealth(HealthCheckCallback callback) {
        String healthCheckUrl = API_URL + "/health";
        makeGetRequest(healthCheckUrl, result -> {
            if ("Api healthy!".equals(result)) {
                callback.onHealthy();
            } else {
                callback.onError("API is not healthy: Unexpected response");
            }
        }, callback::onError);
    }

    // Asynchronous backend reachability check
    public void checkBackendReachability(ReachabilityCallback callback) {
        executorService.submit(() -> {
            boolean reachable = NetworkUtils.isBackendReachable("http://se2-demo.aau.at:53209/api/v1", 5000);

            new Handler(Looper.getMainLooper()).post(() -> callback.onReachabilityChecked(reachable));
        });
    }
    public interface NetworkCallback {
        void onResult(String result);
    }

    public interface SignInCallback {
        void onSuccess(JwtAuthenticationResponse response);
        void onError(String error);
    }

    public void makeSignInRequest(SignInRequest request, SignInCallback callback) throws JSONException {
        String jsonString = gson.toJson(request);
        JSONObject jsonObject = new JSONObject(jsonString);

        makePostRequest(API_URL + "/auth/signin", jsonObject, result -> {
            JwtAuthenticationResponse tokenResponse = gson.fromJson(result, JwtAuthenticationResponse.class);
            callback.onSuccess(tokenResponse);
        }, callback::onError);
    }

    public interface SignUpCallback {
        void onSuccess(JwtAuthenticationResponse response);
        void onError(String error);
    }

    public void makeSignUpRequest(SignUpRequest request, SignUpCallback callback) throws JSONException {
        String jsonString = gson.toJson(request);
        JSONObject jsonObject = new JSONObject(jsonString);

        makePostRequest(API_URL + "/auth/signup", jsonObject, result -> {
            JwtAuthenticationResponse tokenResponse = gson.fromJson(result, JwtAuthenticationResponse.class);
            callback.onSuccess(tokenResponse);
        }, callback::onError);
    }

    public interface ValidationCallback {
        void onSuccess(boolean response);
        void onError(String error);
    }

    public void makeValidationRequest(ValidationRequest request, ValidationCallback callback) throws JSONException {
        String jsonString = gson.toJson(request);
        JSONObject jsonObject = new JSONObject(jsonString);

        makePostRequest(API_URL + "/auth/validate", jsonObject, result -> {
            boolean validationResponse = gson.fromJson(result, boolean.class);
            callback.onSuccess(validationResponse);
        }, callback::onError);
    }

    public interface LobbyCallback {
        void onSuccess(List<GameSession> response);
        void onError(String error);
    }

    public void makeLobbyRequest(LobbyCallback callback){
        makeGetRequest(API_URL + "/game/lobby", result -> {
            Log.e("Data", result);
            List<GameSession> activeLobbys = gson.fromJson(result, new TypeToken<List<GameSession>>(){}.getType());
            callback.onSuccess(activeLobbys);

        }, callback::onError);
    }

    public interface CreateLobbyCallback {
        void onSuccess(CreateLobbyResponse response);
        void onError(String error);
    }

    public void createLobbyRequest(CreateLobbyRequest request, CreateLobbyCallback callback) throws JSONException {
        String jsonString = gson.toJson(request);
        JSONObject jsonObject = new JSONObject(jsonString);

        makePostRequest(API_URL + "/game/lobby", jsonObject, result -> {
            CreateLobbyResponse createLobbyResponse = gson.fromJson(result, CreateLobbyResponse.class);
            callback.onSuccess(createLobbyResponse);
        }, callback::onError);
    }

    public interface GetNewRiskCardCallback {
        void onSuccess(RiskCard response);
        void onError(String error);
    }

    public void getNewRiskCardRequest(String id, GetNewRiskCardCallback callback) {
        makeGetRequest(API_URL + "/game" + "/" + gameService.getSessionId() + "/player/" + id + "/riskcard", result -> {
            Log.e("Data", result);
            RiskCard riskCard = gson.fromJson(result, new TypeToken<RiskCard>(){}.getType());
            callback.onSuccess(riskCard);

        }, callback::onError);
    }

    public interface GetAllRiskCardsByPlayerCallback {
        void onSuccess(List<RiskCard> response);
        void onError(String error);
    }

    public void getAllRiskCardsByPlayerRequest(String id, GetAllRiskCardsByPlayerCallback callback) {
        makeGetRequest(API_URL + "/game" + "/" + gameService.getSessionId() + "/player/" + id + "/riskcards", result -> {
            Log.e("Data", result);
            List<RiskCard> riskCard = gson.fromJson(result, new TypeToken<List<RiskCard>>(){}.getType());
            callback.onSuccess(riskCard);

        }, callback::onError);
    }

    public interface CanPlayerTradeRiskCardsCallback {
        void onSuccess(boolean canTrade);
        void onError(String error);
    }

    public void getCanPlayerTradeRiskCardsRequest(String id, CanPlayerTradeRiskCardsCallback callback) {
        makeGetRequest(API_URL + "/game" + "/" + gameService.getSessionId() + "/player/" + id + "/riskcards/tradable", result -> {
            Log.e("Data", result);
            boolean canTrade = gson.fromJson(result, boolean.class);
            callback.onSuccess(canTrade);
        }, callback::onError);
    }

    public interface PlayerTradeRiskCardsCallback {
        void onSuccess();
        void onError(String error);
    }

    public void getPlayerTradeRiskCardsRequest(String id, PlayerTradeRiskCardsCallback callback) {
        makeGetRequest(API_URL + "/game" + "/" + gameService.getSessionId() + "/player/" + id + "/riskcards/trade", result -> {
            Log.e("Data", result);
            callback.onSuccess();

        }, callback::onError);
    }

    public void makePostRequest(String urlString, JSONObject postData, NetworkCallback callback, NetworkCallback errorCallback) {
        RequestBody body = RequestBody.create(postData.toString(), MediaType.get("application/json; charset=utf-8"));
        Request.Builder requestBuilder = new Request.Builder().url(urlString).post(body);

        if (getSessionToken()!= null){
            requestBuilder.addHeader(AUTHORIZATION, BEARER + getSessionToken());
        }

        Request request = requestBuilder.build();
        executeRequest(request, callback, errorCallback);
    }

    public void makeGetRequest(String urlString, NetworkCallback callback, NetworkCallback errorCallback) {
        Request.Builder requestBuilder = new Request.Builder().url(urlString);

        if (getSessionToken()!= null){
            requestBuilder.addHeader(AUTHORIZATION, BEARER + getSessionToken());
        }

        Request request = requestBuilder.build();
        executeRequest(request, callback, errorCallback);
    }

    public void executeRequest(Request request, NetworkCallback callback, NetworkCallback errorCallback) {
        executorService.submit(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String result = response.body().string();
                    new Handler(Looper.getMainLooper()).post(() -> callback.onResult(result));
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            } catch (IOException e) {
                new Handler(Looper.getMainLooper()).post(() -> errorCallback.onResult(e.getMessage()));
            }
        });
    }

    public void startWebSocket() {
        Request request = new Request.Builder()
                .url(WSS_URL)
                .addHeader(AUTHORIZATION, BEARER + getSessionToken())
                .build();
        RiskWebsocket listener = new RiskWebsocket(context, gameService);
        webSocket = client.newWebSocket(request, listener);
    }

    public void sendMessage(ICustomWebsocketMessage message) {
        if (webSocket == null){
            startWebSocket();
        }

        executorService.submit(() -> {
            if (webSocket != null) {
                String msg = gson.toJson(message);
                webSocket.send(msg);
            }
        });
    }

    public void closeWebSocket() {
        executorService.submit(() -> {
            if (webSocket != null) {
                webSocket.close(1000, "Closing Connection");
                webSocket = null;
            }
        });
    }
}
