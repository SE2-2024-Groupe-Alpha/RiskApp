package se2.alpha.riskapp.activities;

import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import se2.alpha.riskapp.R;
import se2.alpha.riskapp.data.LobbyArrayAdapter;
import se2.alpha.riskapp.data.PlayerArrayAdapter;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.model.game.UserState;
import se2.alpha.riskapp.model.websocket.JoinWebsocketMessage;
import se2.alpha.riskapp.model.websocket.UserLeaveWebsocketMessage;
import se2.alpha.riskapp.model.websocket.UserReadyWebsocketMessage;
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
    boolean isReady = false;
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
        buttonReady = findViewById(R.id.btn_ready);
        buttonLeave = findViewById(R.id.btn_leave_lobby);

        progressBar.setVisibility(View.VISIBLE);

        buttonReady.setOnClickListener(this::playerReadyClick);
        buttonLeave.setOnClickListener(this::playerLeaveLobby);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sendLeaveMessage();
        Intent intent = new Intent(this, LobbyList.class);
        Toast.makeText(Lobby.this, "Left the lobby", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    private void playerLeaveLobby(View view) {
        sendLeaveMessage();
        Intent intent = new Intent(this, LobbyList.class);
        Toast.makeText(Lobby.this, "Left the lobby", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    private void sendLeaveMessage(){
        UserLeaveWebsocketMessage userLeaveWebsocketMessage = new UserLeaveWebsocketMessage(gameService.getSessionId());
        backendService.sendMessage(userLeaveWebsocketMessage);
    }

    public void playerReadyClick(View view) {
        isReady = !isReady;

        if (isReady)
            buttonReady.setText("Not Ready");
        else
            buttonReady.setText("Ready");

        UserReadyWebsocketMessage rdyMsg = new UserReadyWebsocketMessage(gameService.getSessionId(), isReady);
        backendService.sendMessage(rdyMsg);
    }
}
