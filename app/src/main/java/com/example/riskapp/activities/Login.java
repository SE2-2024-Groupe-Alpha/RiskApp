package com.example.riskapp.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.riskapp.R;
import com.example.riskapp.model.auth.JwtAuthenticationResponse;
import com.example.riskapp.model.auth.SignInRequest;
import com.example.riskapp.model.auth.ValidationRequest;
import com.example.riskapp.service.BackendService;

import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;

public class Login extends AppCompatActivity {
    TextInputEditText usernameText;
    TextInputEditText passwordText;
    Button buttonLogin;
    Button buttonRegisterNow;
    BackendService backendService;
    boolean isBound = false;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BackendService.LocalBinder binder = (BackendService.LocalBinder) service;
            backendService = binder.getService();
            isBound = true;
            buttonLogin.setEnabled(true);

            if (backendService.getSessionToken() != null){
                ValidationRequest validationRequest = new ValidationRequest(backendService.getSessionToken());

                try {
                    backendService.makeValidationRequest(validationRequest, new BackendService.ValidationCallback() {
                        @Override
                        public void onSuccess(boolean response) {
                            if (response){
                                Toast.makeText(Login.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, MainMenu.class);
                                startActivity(intent);
                                finish();
                            } else {
                                backendService.saveSessionToken(null);
                                Toast.makeText(Login.this, "Please login again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(Login.this, "Token validation failed: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        buttonLogin.setEnabled(false);
        buttonRegisterNow = findViewById(R.id.btn_register_now);

        Intent connectBackend = new Intent(this, BackendService.class);
        bindService(connectBackend, connection, Context.BIND_AUTO_CREATE);

        buttonLogin.setOnClickListener(view -> {
            String username;
            String password;

            username = String.valueOf(usernameText.getText());
            password = String.valueOf(passwordText.getText());

            if (TextUtils.isEmpty(username)){
                Toast.makeText(Login.this, "Enter username", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)){
                Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            hideKeyboard();

            SignInRequest signInRequest = new SignInRequest(username, password);

            try {
                backendService.makeSignInRequest(signInRequest, new BackendService.SignInCallback() {
                    @Override
                    public void onSuccess(JwtAuthenticationResponse response) {
                        Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();

                        backendService.saveSessionToken(response.getToken());

                        Intent intent = new Intent(Login.this, MainMenu.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String error) {

                        Toast.makeText(Login.this, "Login failed: " + error, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        });


        buttonRegisterNow.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        View currentFocusedView = getCurrentFocus();
        if (currentFocusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }
}
