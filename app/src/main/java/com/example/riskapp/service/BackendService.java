package com.example.riskapp.service;

import android.os.Handler;
import android.os.Looper;
import com.example.riskapp.model.JwtAuthenticationResponse;
import com.example.riskapp.model.SignInRequest;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BackendService {

    private ExecutorService executorService = Executors.newFixedThreadPool(4); // Customize the thread count as needed
    private final String apiUrl = "http://172.23.131.93:8080/api/v1";
    private Gson gson = new Gson();

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

        makePostRequest(apiUrl + "/auth/signin", jsonObject, result -> {
            JwtAuthenticationResponse tokenResponse = gson.fromJson(result, JwtAuthenticationResponse.class);
            callback.onSuccess(tokenResponse);
        }, error -> {
            callback.onError(error);
        });
    }

    public Future<?> makePostRequest(String urlString, JSONObject postData, NetworkCallback callback, NetworkCallback errorCallback) {
        return executorService.submit(() -> {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                try(OutputStream os = urlConnection.getOutputStream()) {
                    byte[] input = postData.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                StringBuilder response = new StringBuilder();
                try(BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                // Use callback on the main thread
                new Handler(Looper.getMainLooper()).post(() -> callback.onResult(response.toString()));
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> errorCallback.onResult(e.toString()));
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        });
    }

    public Future<?> makeGetRequest(String urlString, NetworkCallback callback) {
        return executorService.submit(() -> {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                // Execute callback on the main thread
                new Handler(Looper.getMainLooper()).post(() -> callback.onResult(response.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        });
    }
}
