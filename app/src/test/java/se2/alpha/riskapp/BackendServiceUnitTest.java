package se2.alpha.riskapp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import android.content.Context;

import okhttp3.*;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import org.mockito.*;
import se2.alpha.riskapp.model.auth.JwtAuthenticationResponse;
import se2.alpha.riskapp.model.auth.SignInRequest;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.service.LobbyService;
import se2.alpha.riskapp.service.SecurePreferencesService;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BackendServiceUnitTest {

    private BackendService backendServiceSpy;

    @Captor
    ArgumentCaptor<Request> requestCaptor;

    @Captor
    ArgumentCaptor<BackendService.NetworkCallback> callbackCaptor;

    @Mock
    private GameService gameService;

    @Mock
    private Context context;

    @Mock
    private SecurePreferencesService securePreferencesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        BackendService backendService = new BackendService(context, securePreferencesService, gameService);

        backendServiceSpy = Mockito.spy(backendService);
        doAnswer(invocation -> {
            Request capturedRequest = requestCaptor.getValue();
            BackendService.NetworkCallback capturedCallback = callbackCaptor.getValue();
            if(capturedRequest.url().toString().endsWith("signin")) {
                capturedCallback.onResult("{ \"token\": \"tokenTest\"}");
            }
            return null;
        }).when(backendServiceSpy).executeRequest(requestCaptor.capture(), callbackCaptor.capture(), any());
    }

    @Test
    void testMakeSignInRequest() throws JSONException {
        SignInRequest signInRequest = new SignInRequest("test", "test");
        BackendService.SignInCallback signInCallback = mock(BackendService.SignInCallback.class);

        backendServiceSpy.makeSignInRequest(signInRequest, signInCallback);

        verify(signInCallback, Mockito.timeout(1000)).onSuccess(any());
    }
}
