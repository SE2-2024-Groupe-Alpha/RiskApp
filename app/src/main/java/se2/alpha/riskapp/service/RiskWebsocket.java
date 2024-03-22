package se2.alpha.riskapp.service;

import android.util.Log;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RiskWebsocket extends WebSocketListener {
        private final ExecutorService executorService = Executors.newFixedThreadPool(4);

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            // Connection opened
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            // Handle text messages
                executorService.submit(() -> {
                        Log.e("ANSWER",text);
                });

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
}