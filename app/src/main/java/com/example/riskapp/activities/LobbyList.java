package com.example.riskapp.activities;

import android.os.Bundle;


import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.riskapp.R;
import com.example.riskapp.data.SecurePreferences;
import com.example.riskapp.service.BackendService;

public class LobbyList extends AppCompatActivity {
    ListView lobbyList;
    BackendService backendService;
    SecurePreferences securePreferences;

    String[] tutorials
            = { "Algorithms", "Data Structures",
            "Languages", "Interview Corner",
            "GATE", "ISRO CS",
            "UGC NET CS", "CS Subjects",
            "Web Technologies" };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_list);

        lobbyList = findViewById(R.id.lobby_list);
        backendService = new BackendService();

//        backendService.makeLobbyRequest(new BackendService.LobbyCallback() {
//            @Override
//            public void onSuccess(String response) {
//
//            }
//
//            @Override
//            public void onError(String error) {
//                Toast.makeText(LobbyList.this, "Could not retrieve Lobbylist", Toast.LENGTH_SHORT).show();
//            }
//        });

        ArrayAdapter<String> test = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tutorials);

        lobbyList.setAdapter(test);
    }
}
