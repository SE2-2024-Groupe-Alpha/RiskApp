package se2.alpha.riskapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import se2.alpha.riskapp.R;
import se2.alpha.riskapp.data.PlayerArrayAdapter;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.dol.Player;
import se2.alpha.riskapp.dol.RiskCard;
import se2.alpha.riskapp.model.game.UserState;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameLogicService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.service.LobbyService;
import se2.alpha.riskapp.service.RiskCardService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lobby extends AppCompatActivity {
    ListView playerList;
    ProgressBar progressBar;
    Button buttonReady;
    Button buttonLeave;
    Button buttonCreateGame;
    boolean isReady = false;
    @Inject
    BackendService backendService;
    @Inject
    GameService gameService;
    @Inject
    LobbyService lobbyService;
    @Inject
    GameLogicService gameLogicService;

    @Inject
    RiskCardService riskCardService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.lobby_activity);

        playerList = findViewById(R.id.player_list);
        progressBar = findViewById(R.id.progressBar);
        buttonReady = findViewById(R.id.btn_ready);
        buttonLeave = findViewById(R.id.btn_leave_lobby);
        buttonCreateGame = findViewById(R.id.btn_create_game);
        buttonCreateGame.setEnabled(false);

        progressBar.setVisibility(View.VISIBLE);

        buttonReady.setOnClickListener(this::playerReadyClick);
        buttonLeave.setOnClickListener(this::playerLeaveLobby);
        buttonCreateGame.setOnClickListener(this::createGameClick);

        PlayerArrayAdapter adapter = new PlayerArrayAdapter(Lobby.this, new ArrayList<>());
        playerList.setAdapter(adapter);

        gameService.getUserStates().observe(this, newData -> {
            progressBar.setVisibility(View.GONE);
            List<UserState> userStateList = new ArrayList<>();

            for (Map.Entry<String, Boolean> entry : newData.entrySet()) {
                UserState userState = new UserState(entry.getKey(), entry.getValue());
                userStateList.add(userState);
            }

            if(userStateList.size() >= 1 && userStateList.stream().allMatch(UserState::getIsReady))
                buttonCreateGame.setEnabled(true);
            else
                buttonCreateGame.setEnabled(false);
            adapter.clear();
            adapter.addAll(userStateList);
            adapter.notifyDataSetChanged();
        });

        gameService.getGameStarted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gameStarted) {
                if(Boolean.TRUE.equals(gameStarted))
                {
                    Intent intent = new Intent(Lobby.this, Game.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        lobbyService.leaveLobby();
        Intent intent = new Intent(this, LobbyList.class);
        Toast.makeText(Lobby.this, "Left the lobby", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        backendService.closeWebSocket();
        finish();
    }

    private void playerLeaveLobby(View view) {
        lobbyService.leaveLobby();
        Intent intent = new Intent(this, LobbyList.class);
        Toast.makeText(Lobby.this, "Left the lobby", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        backendService.closeWebSocket();
        finish();
    }

    public void playerReadyClick(View view) {
        isReady = !isReady;

        if (isReady) {
            buttonReady.setText("Not Ready");
        } else{
            buttonReady.setText("Ready");
        }

        lobbyService.updatePlayerStatus(isReady);
    }

    public void createGameClick(View view) {
        gameLogicService.createGame();
    }
}
