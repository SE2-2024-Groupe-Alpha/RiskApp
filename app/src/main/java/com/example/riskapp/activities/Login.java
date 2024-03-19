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
import com.example.riskapp.model.ValidationRequest;
import com.example.riskapp.service.BackendService;

import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;

public class Login extends AppCompatActivity {
    TextInputEditText usernameText;
    TextInputEditText passwordText;

    Button buttonLogin;
    Button buttonRegisterNow;
    BackendService backendService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        buttonRegisterNow = findViewById(R.id.btn_register_now);

        backendService = new BackendService();

        SecurePreferences securePreferences = new SecurePreferences(this);

        if (securePreferences.getSessionToken() != null){
            ValidationRequest validationRequest = new ValidationRequest(securePreferences.getSessionToken());

            try {
                backendService.makeValidationRequest(validationRequest, new BackendService.ValidationCallback() {
                    @Override
                    public void onSuccess(boolean response) {
                        if (response){
                            Toast.makeText(Login.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainMenu.class);
                            startActivity(intent);
                        }else {
                            securePreferences.saveSessionToken(null);
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

            return;
        }

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

                        new SecurePreferences(Login.this).saveSessionToken(response.getToken());

                        Intent intent = new Intent(Login.this, MainMenu.class);
                        startActivity(intent);
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


}
