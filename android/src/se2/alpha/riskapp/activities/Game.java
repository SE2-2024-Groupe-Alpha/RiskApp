package se2.alpha.riskapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import se2.alpha.riskapp.RiskGame;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.dol.Player;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.service.LobbyService;

import javax.inject.Inject;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ProcessLifecycleOwner;

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

        try {
            ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);

            backendService.checkBackendReachability(isReachable -> {
                if (!isReachable) {
                    showToast("Backend is unreachable");
                    Log.d(TAG, "Backend is unreachable");
                } else {
                    Log.d(TAG, "Backend is reachable!");
                }
            });

            initialize(gameService.startGame(), config);

            gameService.getWinner().observe(ProcessLifecycleOwner.get(), player -> {
                System.out.println("risklog game activity player won " + player.getName());
                Intent intent = new Intent(Game.this, EndOfGame.class);
                startActivity(intent);
            });
        } catch (Exception e) {
            Log.e(TAG, "Error during onCreate in Game activity", e);
            showToast("Error initializing the game: " + e.getMessage());
        }
    }


    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Game.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
