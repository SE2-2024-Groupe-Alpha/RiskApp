package se2.alpha.riskapp.activities;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import se2.alpha.riskapp.R;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.service.SecurePreferencesService;

import javax.inject.Inject;

public class MainMenu extends AppCompatActivity {
    Button buttonLogout;
    Button buttonLobbyList;

    @Inject
    SecurePreferencesService securePreferencesService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
        setContentView(R.layout.main_menu_activity);

        buttonLogout = findViewById(R.id.btn_logout);
        buttonLobbyList = findViewById(R.id.btn_join_lobby);

        buttonLogout.setOnClickListener(view -> {
            Toast.makeText(MainMenu.this, "Logout successful", Toast.LENGTH_SHORT).show();

           securePreferencesService.saveSessionToken(null);

            Intent intent = new Intent(MainMenu.this, Login.class);
            startActivity(intent);
        });

        buttonLobbyList.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenu.this, LobbyList.class);
            startActivity(intent);
        });
    }
}
