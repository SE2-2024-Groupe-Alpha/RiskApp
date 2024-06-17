package se2.alpha.riskapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import se2.alpha.riskapp.R;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameLogicService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.service.LobbyService;

public class EndOfGame extends AppCompatActivity {
    Button buttonGoToLobby;
    TextView textViewWinner;
    @Inject
    BackendService backendService;
    @Inject
    GameService gameService;
    @Inject
    LobbyService lobbyService;
    @Inject
    GameLogicService gameLogicService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.end_of_game_activity);

//        buttonGoToLobby = findViewById(R.id.btn_back_to_lobby);
//        buttonGoToLobby.setOnClickListener(this::backToLobby);
//
//        textViewWinner = findViewById(R.id.textview_winner);
//        textViewWinner.setText(gameService.getWinner().getValue().getName() + " won the game");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void backToLobby(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
