package se2.alpha.riskapp.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.IBinder;
import android.text.TextUtils;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import se2.alpha.riskapp.R;
import se2.alpha.riskapp.data.SecurePreferences;
import se2.alpha.riskapp.model.auth.JwtAuthenticationResponse;
import se2.alpha.riskapp.model.auth.SignUpRequest;
import se2.alpha.riskapp.service.BackendService;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import android.content.ServiceConnection;

public class Register extends AppCompatActivity {
    TextInputEditText usernameText;
    TextInputEditText passwordText;

    Button buttonRegister;
    BackendService backendService;

    private boolean isBound = false;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackendService.LocalBinder binder = (BackendService.LocalBinder) service;
            backendService = binder.getService();
            isBound = true;
            buttonRegister.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        buttonRegister = findViewById(R.id.btn_register);

        buttonRegister.setEnabled(false);

        Intent connectBackend = new Intent(this, BackendService.class);
        bindService(connectBackend, connection, Context.BIND_AUTO_CREATE);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }
}
