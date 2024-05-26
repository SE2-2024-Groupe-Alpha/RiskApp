package se2.alpha.riskapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import se2.alpha.riskapp.R;
import se2.alpha.riskapp.data.LobbyArrayAdapter;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.model.game.GameSession;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.LobbyService;

import javax.inject.Inject;
import java.util.List;

public class LobbyList extends AppCompatActivity {
    ListView availableLobbies;
    ProgressBar progressBar;
    List<GameSession> filteredLobbies;
    Button joinByIdButton;
    Button buttonCreateLobby;
    @Inject
    BackendService backendService;
    @Inject
    LobbyService lobbyService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);
        setContentView(R.layout.lobby_list_activity);
        availableLobbies = findViewById(R.id.lobby_list);
        progressBar = findViewById(R.id.progressBar);
        joinByIdButton = findViewById(R.id.btn_join_by_id);
        buttonCreateLobby = findViewById(R.id.btn_create_lobby);

        getLobbies();
        setupUIElements();
    }

    private void setupUIElements(){
        availableLobbies.setOnItemClickListener((parent, view, position, id) -> {
            GameSession sessionToJoin = filteredLobbies.get(position);
            lobbyService.joinLobby(sessionToJoin.getSessionId());
            Intent intent = new Intent(this, Lobby.class);
            startActivity(intent);
            finish();
        });

        joinByIdButton.setOnClickListener(view -> createJoinByIdDialog());
        buttonCreateLobby.setOnClickListener(view -> createLobbyDialog());
    }

    @Override
    protected void onStart() {
        getLobbies();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    private void getLobbies() {
        lobbyService.getAvailableLobbies(new LobbyService.GetAvailableLobbiesCallback() {
            @Override
            public void onResult(List<GameSession> result) {
                filteredLobbies = result;
                LobbyArrayAdapter adapter = new LobbyArrayAdapter(LobbyList.this, filteredLobbies);
                runOnUiThread(() -> {
                    availableLobbies.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                });
            }
            @Override
            public void onError(String error) {
                Toast.makeText(LobbyList.this, "Could not retrieve Lobbylist" + error, Toast.LENGTH_SHORT).show();
                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }
        });
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
                                Intent intent = new Intent(LobbyList.this, Lobby.class);
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("host", true);
                                intent.putExtras(bundle);
                                Toast.makeText(LobbyList.this, "Lobby " + lobbyTitle + " created", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(LobbyList.this, "Lobby creation failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Toast.makeText(LobbyList.this, "Lobby title cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> {});

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void createJoinByIdDialog(){
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_join_by_id, null);
        EditText editTextLobbyTitle = dialogView.findViewById(R.id.dialogue_join_by_id_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("Confirm", (dialog, id) -> {
                    String lobbyId = editTextLobbyTitle.getText().toString().trim();

                    if (!lobbyId.isEmpty()) {
                        Toast.makeText(LobbyList.this, "TODO ON BACKEND", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LobbyList.this, "Please enter an Id", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> {});

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
