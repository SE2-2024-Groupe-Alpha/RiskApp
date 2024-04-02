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
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.service.LobbyService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Game extends AppCompatActivity {
    @Inject
    BackendService backendService;
    @Inject
    GameService gameService;
    @Inject
    LobbyService lobbyService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
        setContentView(R.layout.game_activity);
    }
}
