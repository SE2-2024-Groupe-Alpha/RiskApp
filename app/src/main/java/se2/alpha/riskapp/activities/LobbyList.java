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
import se2.alpha.riskapp.service.BackendService;

import se2.alpha.riskapp.service.LobbyService;

import javax.inject.Inject;
import java.util.List;

public class LobbyList extends AppCompatActivity {
    ListView availableLobbies;
    ProgressBar progressBar;
    List<GameSession> filteredLobbies;
    @Inject
    BackendService backendService;
    @Inject
    LobbyService lobbyService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
        setContentView(R.layout.lobby_list_activity);
        availableLobbies = findViewById(R.id.lobby_list);
        progressBar = findViewById(R.id.progressBar);

        getLobbies();

        availableLobbies.setOnItemClickListener((parent, view, position, id) -> {
            GameSession sessionToJoin = filteredLobbies.get(position);
            lobbyService.joinLobby(sessionToJoin.getSessionId());
            Intent intent = new Intent(this, Lobby.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onStart() {
        getLobbies();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    private void getLobbies() {
        lobbyService.getAvailableLobbies(new LobbyService.GetAvailableLobbiesCallback() {
            @Override
            public void onResult(List<GameSession> result) {
                filteredLobbies = result;
                LobbyArrayAdapter adapter = new LobbyArrayAdapter(LobbyList.this, filteredLobbies);
                runOnUiThread(() -> {
                    availableLobbies.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                });
            }
            @Override
            public void onError(String error) {
                Toast.makeText(LobbyList.this, "Could not retrieve Lobbylist" + error, Toast.LENGTH_SHORT).show();
                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }
        });
    }
}
