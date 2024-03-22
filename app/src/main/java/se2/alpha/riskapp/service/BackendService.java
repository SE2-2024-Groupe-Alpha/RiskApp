package se2.alpha.riskapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.Nullable;
import se2.alpha.riskapp.model.auth.JwtAuthenticationResponse;
import se2.alpha.riskapp.model.auth.SignInRequest;
import se2.alpha.riskapp.model.auth.SignUpRequest;
import se2.alpha.riskapp.model.auth.ValidationRequest;
import se2.alpha.riskapp.model.game.GameSession;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import se2.alpha.riskapp.BuildConfig;

import javax.inject.Inject;


public class BackendService {

    private ExecutorService executorService = Executors.newFixedThreadPool(4); // Customize the thread count as needed
    private Gson gson = new Gson();
    private static final String API_URL = BuildConfig.API_URL;
    private static final String ENCODING = "utf-8";
    private final SecurePreferencesService securePreferencesService;
//    private final IBinder binder = new LocalBinder();

    @Inject
    public BackendService(Context context, SecurePreferencesService securePreferences) {
        this.securePreferencesService = securePreferences;
    }

//    public class LocalBinder extends Binder {
//        public BackendService getService() {
//            return BackendService.this;
//        }
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return binder;
//    }

    public String getSessionToken() {
        return securePreferencesService.getSessionToken();
    }

    public void saveSessionToken(String token) {
        securePreferencesService.saveSessionToken(token);
    }

    public interface NetworkCallback {
        void onResult(String result);
    }

    public interface SignInCallback {
        void onSuccess(JwtAuthenticationResponse response);
        void onError(String error);
    }

    public void makeSignInRequest(SignInRequest request, SignInCallback callback) throws JSONException {
        String jsonString = gson.toJson(request);
        JSONObject jsonObject = new JSONObject(jsonString);

        makePostRequest(API_URL + "/auth/signin", jsonObject, result -> {
            JwtAuthenticationResponse tokenResponse = gson.fromJson(result, JwtAuthenticationResponse.class);
            callback.onSuccess(tokenResponse);
        }, callback::onError);
    }

    public interface SignUpCallback {
        void onSuccess(JwtAuthenticationResponse response);
        void onError(String error);
    }

    public void makeSignUpRequest(SignUpRequest request, SignUpCallback callback) throws JSONException {
        String jsonString = gson.toJson(request);
        JSONObject jsonObject = new JSONObject(jsonString);

        makePostRequest(API_URL + "/auth/signup", jsonObject, result -> {
            JwtAuthenticationResponse tokenResponse = gson.fromJson(result, JwtAuthenticationResponse.class);
            callback.onSuccess(tokenResponse);
        }, callback::onError);
    }

    public interface ValidationCallback {
        void onSuccess(boolean response);
        void onError(String error);
    }

    public void makeValidationRequest(ValidationRequest request, ValidationCallback callback) throws JSONException {
        String jsonString = gson.toJson(request);
        JSONObject jsonObject = new JSONObject(jsonString);

        makePostRequest(API_URL + "/auth/validate", jsonObject, result -> {
            boolean validationResponse = gson.fromJson(result, boolean.class);
            callback.onSuccess(validationResponse);
        }, callback::onError);
    }

    public interface LobbyCallback {
        void onSuccess(List<GameSession> response);
        void onError(String error);
    }

    public void makeLobbyRequest(LobbyCallback callback){
        makeGetRequest(API_URL + "/game/lobby", getSessionToken(), result -> {
            Log.e("Data", result);
            List<GameSession> activeLobbys = gson.fromJson(result, new TypeToken<List<GameSession>>(){}.getType());
            callback.onSuccess(activeLobbys);

        }, callback::onError);
    }

    public Future<?> makePostRequest(String urlString, JSONObject postData, NetworkCallback callback, NetworkCallback errorCallback) {
        return executorService.submit(() -> {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; " + ENCODING);
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                try(OutputStream os = urlConnection.getOutputStream()) {
                    byte[] input = postData.toString().getBytes(ENCODING);
                    os.write(input, 0, input.length);
                }

                StringBuilder response = new StringBuilder();
                try(BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), ENCODING))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                new Handler(Looper.getMainLooper()).post(() -> callback.onResult(response.toString()));
            } catch (Exception e) {
                handleRequestError(urlConnection, errorCallback, e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        });
    }

    public Future<?> makeGetRequest(String urlString, String JwtToken, NetworkCallback callback, NetworkCallback errorCallback) {
        return executorService.submit(() -> {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Authorization", "Bearer " + JwtToken);

                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), ENCODING))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                new Handler(Looper.getMainLooper()).post(() -> callback.onResult(response.toString()));
            } catch (Exception e) {
                handleRequestError(urlConnection, errorCallback, e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        });
    }

    private void handleRequestError(HttpURLConnection urlConnection, NetworkCallback errorCallback ,Exception e){
        StringBuilder errorResponse = new StringBuilder();

        if (urlConnection != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), ENCODING))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    errorResponse.append(responseLine.trim());
                }
            } catch (Exception ex) {
                Log.e("Communication Error", ex.toString());
                errorResponse = new StringBuilder("Error reading error stream");
            }
        }

        final String errorResult = errorResponse.toString();

        new Handler(Looper.getMainLooper()).post(() -> errorCallback.onResult(errorResult));
        Log.e("BackendService-Error", errorResult);
    }
}
