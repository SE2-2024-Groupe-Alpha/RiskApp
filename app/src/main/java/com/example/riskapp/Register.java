package com.example.riskapp;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {

    TextInputEditText usernameText, passwordText;
    Button buttonRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        buttonRegister = findViewById(R.id.btn_register);

        buttonRegister.setOnClickListener(view -> {
            String username, password;
            username = String.valueOf(usernameText.getText());
            password = String.valueOf(passwordText.getText());

            if (TextUtils.isEmpty(username)){
                Toast.makeText(Register.this, "Enter username", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)){
                Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            setContentView(R.layout.activity_login);
        });
    }
}
