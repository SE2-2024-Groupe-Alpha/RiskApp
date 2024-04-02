package se2.alpha.riskapp.service;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import lombok.var;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import se2.alpha.riskapp.model.websocket.GameWebsocketMessage;
import se2.alpha.riskapp.model.websocket.GameWebsocketMessageAction;
import se2.alpha.riskapp.model.websocket.IGameWebsocketMessage;
import se2.alpha.riskapp.model.websocket.UserSyncWebsocketMessage;

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

        public void handleSyncUsers(String text){
                UserSyncWebsocketMessage userSyncWebsocketMessage = gson.fromJson(text, UserSyncWebsocketMessage.class);
                gameService.updateUsers(userSyncWebsocketMessage.getUserStates());
        }
}