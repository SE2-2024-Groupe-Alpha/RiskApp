package se2.alpha.riskapp.activities;

import android.os.Bundle;


import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import se2.alpha.riskapp.R;
import se2.alpha.riskapp.data.LobbyArrayAdapter;
import se2.alpha.riskapp.data.PlayerArrayAdapter;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.model.game.UserState;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Lobby extends AppCompatActivity {
    ListView playerList;
    ProgressBar progressBar;
    Button buttonReady;
    Button buttonLeave;
    private ArrayAdapter<String> adapter;

    @Inject
    BackendService backendService;

    @Inject
    GameService gameService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
        setContentView(R.layout.lobby_activity);

        playerList = findViewById(R.id.player_list);
        progressBar = findViewById(R.id.progressBar);


        progressBar.setVisibility(View.VISIBLE);


        PlayerArrayAdapter adapter = new PlayerArrayAdapter(Lobby.this, new ArrayList<>());
        playerList.setAdapter(adapter);

        gameService.getUserStates().observe(this, newData -> {
            progressBar.setVisibility(View.GONE);
            List<UserState> userStateList = new ArrayList<>();

            for (Map.Entry<String, Boolean> entry : newData.entrySet()) {
                UserState userState = new UserState(entry.getKey(), entry.getValue());
                userStateList.add(userState);
            }

            adapter.clear();
            adapter.addAll(userStateList);
            adapter.notifyDataSetChanged();
        });
    }
}
