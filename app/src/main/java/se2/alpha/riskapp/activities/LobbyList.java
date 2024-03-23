package se2.alpha.riskapp.activities;

import android.content.Intent;
import android.os.Bundle;


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
import se2.alpha.riskapp.model.websocket.JoinWebsocketMessage;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class LobbyList extends AppCompatActivity {
    ListView availableLobbies;
    ProgressBar progressBar;
    List<GameSession> filteredLobbies;

    @Inject
    BackendService backendService;
    @Inject
    GameService gameService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
        setContentView(R.layout.lobby_list_activity);

        availableLobbies = findViewById(R.id.lobby_list);
        progressBar = findViewById(R.id.progressBar);

        makeLobbyRequest();


        availableLobbies.setOnItemClickListener((parent, view, position, id) -> {
            GameSession sessionToJoin = filteredLobbies.get(position);
            JoinWebsocketMessage joinWebsocketMessage = new JoinWebsocketMessage(sessionToJoin.getSessionId());
            backendService.sendMessage(joinWebsocketMessage);
            gameService.setSessionId(sessionToJoin.getSessionId());
            Intent intent = new Intent(this, Lobby.class);
            Toast.makeText(LobbyList.this, "Joined Lobby", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onStart() {
        backendService.startWebSocket();
        super.onStart();
        makeLobbyRequest();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backendService.closeWebSocket();
    }

    private void makeLobbyRequest() {
        backendService.makeLobbyRequest(new BackendService.LobbyCallback() {
            @Override
            public void onSuccess(List<GameSession> response) {
                filteredLobbies = response.stream()
                        .filter(gameSession -> gameSession.getState() == GameState.LOBBY && gameSession.getUsers() < 6)
                        .collect(Collectors.toList());

                LobbyArrayAdapter adapter = new LobbyArrayAdapter(LobbyList.this, filteredLobbies);
                availableLobbies.setAdapter(adapter);

                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(LobbyList.this, "Could not retrieve Lobbylist", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                });
            }
        });
    }
}
