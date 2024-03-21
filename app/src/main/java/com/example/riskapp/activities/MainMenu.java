package com.example.riskapp.activities;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.riskapp.R;
import com.example.riskapp.data.SecurePreferences;

public class MainMenu extends AppCompatActivity {
    Button buttonLogout;
    Button buttonLobbyList;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        buttonLogout = findViewById(R.id.btn_logout);
        buttonLobbyList = findViewById(R.id.btn_join_lobby);

        buttonLogout.setOnClickListener(view -> {
            Toast.makeText(MainMenu.this, "Logout successful", Toast.LENGTH_SHORT).show();

            new SecurePreferences(MainMenu.this).saveSessionToken(null);

            Intent intent = new Intent(MainMenu.this, Login.class);
            startActivity(intent);
        });

        buttonLobbyList.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenu.this, LobbyList.class);
            startActivity(intent);
        });
    }
}
