package se2.alpha.riskapp.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import se2.alpha.riskapp.R;
import se2.alpha.riskapp.RiskGame;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.service.LobbyService;

import javax.inject.Inject;

public class Game extends AndroidApplication {
    @Inject
    BackendService backendService;
    @Inject
    GameService gameService;
    @Inject
    LobbyService lobbyService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
        initialize(new RiskGame(), config);
    }
}
