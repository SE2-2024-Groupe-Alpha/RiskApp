package com.example.riskapp.activities;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.riskapp.R;
import com.example.riskapp.data.SecurePreferences;
import com.example.riskapp.model.JwtAuthenticationResponse;
import com.example.riskapp.model.SignInRequest;
import com.example.riskapp.model.SignUpRequest;
import com.example.riskapp.service.BackendService;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;

public class Register extends AppCompatActivity {
    TextInputEditText usernameText;
    TextInputEditText passwordText;

    Button buttonRegister;
    BackendService backendService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        buttonRegister = findViewById(R.id.btn_register);

        backendService = new BackendService();

        buttonRegister.setOnClickListener(view -> {
            String username;
            String password;

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

            hideKeyboard();

            SignUpRequest signUpRequest = new SignUpRequest(username, password);

            try {
                backendService.makeSignUpRequest(signUpRequest, new BackendService.SignUpCallback() {
                    @Override
                    public void onSuccess(JwtAuthenticationResponse response) {
                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();

                        new SecurePreferences(Register.this).saveSessionToken(response.getToken());

                        Intent intent = new Intent(Register.this, MainMenu.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(Register.this, "Registration failed: " + error, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        View currentFocusedView = getCurrentFocus();
        if (currentFocusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
