package se2.alpha.riskapp.activities;

import android.os.Bundle;


import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import se2.alpha.riskapp.R;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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

        List<String> initialUserNames = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, initialUserNames);
        playerList.setAdapter(adapter);



        gameService.getUserNames().observe(this, newData -> {
            progressBar.setVisibility(View.GONE);
            adapter.clear();
            adapter.addAll(newData);
            adapter.notifyDataSetChanged();
        });
    }
}
