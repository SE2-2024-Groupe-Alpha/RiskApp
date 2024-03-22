package se2.alpha.riskapp.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import se2.alpha.riskapp.BuildConfig;

import javax.inject.Inject;


public class BackendService {

    private ExecutorService executorService = Executors.newFixedThreadPool(4); // Customize the thread count as needed
    private Gson gson = new Gson();
    private static final String API_URL = BuildConfig.API_URL;
    private static final String ENCODING = "utf-8";
    private final SecurePreferencesService securePreferencesService;
    private OkHttpClient client = new OkHttpClient();

    @Inject
    public BackendService(Context context, SecurePreferencesService securePreferences) {
        this.securePreferencesService = securePreferences;
    }

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
        makeGetRequest(API_URL + "/game/lobby", result -> {
            Log.e("Data", result);
            List<GameSession> activeLobbys = gson.fromJson(result, new TypeToken<List<GameSession>>(){}.getType());
            callback.onSuccess(activeLobbys);

        }, callback::onError);
    }

    public void makePostRequest(String urlString, JSONObject postData, NetworkCallback callback, NetworkCallback errorCallback) {
        RequestBody body = RequestBody.create(postData.toString(), MediaType.get("application/json; charset=utf-8"));
        Request.Builder requestBuilder = new Request.Builder().url(urlString).post(body);

        if (getSessionToken()!= null){
            requestBuilder.addHeader("Authorization", "Bearer " + getSessionToken());
        }

        Request request = requestBuilder.build();
        executeRequest(request, callback, errorCallback);
    }

    public void makeGetRequest(String urlString, NetworkCallback callback, NetworkCallback errorCallback) {
        Request.Builder requestBuilder = new Request.Builder().url(urlString);

        if (getSessionToken()!= null){
            requestBuilder.addHeader("Authorization", "Bearer " + getSessionToken());
        }

        Request request = requestBuilder.build();

        executeRequest(request, callback, errorCallback);
    }

    private void executeRequest(Request request, NetworkCallback callback, NetworkCallback errorCallback) {
        executorService.submit(() -> {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    new Handler(Looper.getMainLooper()).post(() -> errorCallback.onResult(e.getMessage()));
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            try {
                                assert response.body() != null;
                                callback.onResult(response.body().string());
                            } catch (IOException e) {
                                Log.e("OH", "OH NO");
                            }
                        });
                    } else {
                        new Handler(Looper.getMainLooper()).post(() -> errorCallback.onResult(response.message()));
                    }
                }
            });
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
