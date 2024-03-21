package se2.alpha.riskapp.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;


import android.os.IBinder;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import se2.alpha.riskapp.R;
import se2.alpha.riskapp.data.LobbyArrayAdapter;
import se2.alpha.riskapp.model.game.GameSession;
import se2.alpha.riskapp.model.game.GameState;
import se2.alpha.riskapp.service.BackendService;

import java.util.List;
import java.util.stream.Collectors;

public class LobbyList extends AppCompatActivity {
    ListView lobbyList;
    ProgressBar progressBar;
    BackendService backendService;


    private boolean isBound = false;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackendService.LocalBinder binder = (BackendService.LocalBinder) service;
            backendService = binder.getService();
            isBound = true;

            makeLobbyRequest();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_list_activity);

        lobbyList = findViewById(R.id.lobby_list);
        progressBar = findViewById(R.id.progressBar);

        Intent connectBackend = new Intent(this, BackendService.class);
        bindService(connectBackend, connection, Context.BIND_AUTO_CREATE);
    }

    private void makeLobbyRequest() {
        if (backendService != null) {
            backendService.makeLobbyRequest(new BackendService.LobbyCallback() {
                @Override
                public void onSuccess(List<GameSession> response) {
                    List<GameSession> filteredLobbies = response.stream()
                            .filter(gameSession -> gameSession.getState() == GameState.Lobby || gameSession.getState() == GameState.Setup)
                            .collect(Collectors.toList());

                    runOnUiThread(() -> {
                        LobbyArrayAdapter adapter = new LobbyArrayAdapter(LobbyList.this, filteredLobbies);
                        lobbyList.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> {
                        Toast.makeText(LobbyList.this, "Could not retrieve Lobbylist", Toast.LENGTH_SHORT).show();
                        // Hide the ProgressBar on error as well
                        progressBar.setVisibility(View.GONE);
                    });
                }
            });
        } else {
            Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT).show();
            // Hide the ProgressBar if the service is not bound
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }
}
