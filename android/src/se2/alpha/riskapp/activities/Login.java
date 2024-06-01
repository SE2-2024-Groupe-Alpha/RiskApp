package se2.alpha.riskapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import se2.alpha.riskapp.R;
import se2.alpha.riskapp.data.RiskApplication;
import se2.alpha.riskapp.model.auth.JwtAuthenticationResponse;
import se2.alpha.riskapp.model.auth.SignInRequest;
import se2.alpha.riskapp.model.auth.ValidationRequest;
import se2.alpha.riskapp.service.BackendService;

import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    TextInputEditText usernameText;
    TextInputEditText passwordText;
    Button buttonLogin;
    Button buttonRegisterNow;

    @Inject
    BackendService backendService;

    private ScheduledExecutorService scheduler;

    public void isApiHealthy() {
        backendService.checkApiHealth(new BackendService.HealthCheckCallback() {
            @Override
            public void onHealthy() {
                buttonLogin.setEnabled(true);
                buttonRegisterNow.setEnabled(true);
                scheduler.shutdown();
//                Toast.makeText(Login.this, "Backend reached!", Toast.LENGTH_LONG).show();
                isLoggedIn();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(Login.this, "Server currently unreachable!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void isLoggedIn() {
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
                        backendService.saveSessionToken(null);
                        Toast.makeText(Login.this, "Token validation failed, please login again", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (JSONException e) {
                backendService.saveSessionToken(null);
                Toast.makeText(Login.this, "Token validation request error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RiskApplication) getApplication()).getRiskAppComponent().inject(this);

        setContentView(R.layout.login_activity);

        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        buttonRegisterNow = findViewById(R.id.btn_register_now);

        buttonLogin.setEnabled(false);
        buttonRegisterNow.setEnabled(false);

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(this::isApiHealthy, 0, 5, TimeUnit.SECONDS);

        setupButtons();
    }

    private void setupButtons(){
        buttonLogin.setOnClickListener(view -> attemptLogin());
        buttonRegisterNow.setOnClickListener(view -> startRegistrationActivity());
    }

    private void attemptLogin() {
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
                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            Log.e("LOGIN", e.getMessage());
            Toast.makeText(Login.this, "Login failed catastrophic", Toast.LENGTH_SHORT).show();
        }
    }

    private void startRegistrationActivity() {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        View currentFocusedView = getCurrentFocus();
        if (currentFocusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
