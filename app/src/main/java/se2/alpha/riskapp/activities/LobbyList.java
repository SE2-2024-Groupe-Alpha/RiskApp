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
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.model.game.GameSession;
import se2.alpha.riskapp.model.game.GameState;
import se2.alpha.riskapp.service.BackendService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class LobbyList extends AppCompatActivity {
    ListView lobbyList;
    ProgressBar progressBar;

    @Inject
    BackendService backendService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
        setContentView(R.layout.lobby_list_activity);

        lobbyList = findViewById(R.id.lobby_list);
        progressBar = findViewById(R.id.progressBar);

        makeLobbyRequest();
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
                        progressBar.setVisibility(View.GONE);
                    });
                }
            });
        } else {
            Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT).show();

            progressBar.setVisibility(View.GONE);
        }
    }
}
