package se2.alpha.riskapp.activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import se2.alpha.riskapp.R;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.service.LobbyService;
import se2.alpha.riskapp.service.SecurePreferencesService;

import javax.inject.Inject;

public class MainMenu extends AppCompatActivity {
    Button buttonLogout;
    Button buttonLobbyList;
    Button buttonCreateLobby;
    @Inject
    SecurePreferencesService securePreferencesService;
    @Inject
    LobbyService lobbyService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
        setContentView(R.layout.main_menu_activity);

        buttonLogout = findViewById(R.id.btn_logout);
        buttonLobbyList = findViewById(R.id.btn_join_lobby);
        buttonCreateLobby = findViewById(R.id.btn_create_lobby);

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

        buttonCreateLobby.setOnClickListener(view -> createLobbyDialog());
    }

    private void createLobbyDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_lobby, null);
        EditText editTextLobbyTitle = dialogView.findViewById(R.id.et_lobby_title);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("Confirm", (dialog, id) -> {
                    String lobbyTitle = editTextLobbyTitle.getText().toString().trim();

                    if (!lobbyTitle.isEmpty()) {
                        lobbyService.createLobby(lobbyTitle, success -> {
                            if (success){
                                Intent intent = new Intent(MainMenu.this, Lobby.class);
                                Toast.makeText(MainMenu.this, "Lobby " + lobbyTitle + " created", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(MainMenu.this, "Lobby creation failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Toast.makeText(MainMenu.this, "Lobby title cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> {});

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}