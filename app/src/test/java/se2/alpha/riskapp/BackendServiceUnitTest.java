package se2.alpha.riskapp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import android.content.Context;

import okhttp3.*;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.*;

import se2.alpha.riskapp.model.auth.SignInRequest;
import se2.alpha.riskapp.model.auth.SignUpRequest;
import se2.alpha.riskapp.model.auth.ValidationRequest;
import se2.alpha.riskapp.model.game.CreateLobbyRequest;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.service.SecurePreferencesService;

public class BackendServiceUnitTest {

    private BackendService backendServiceSuccessSpy;
    private BackendService backendServiceErrorSpy;

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

        backendServiceSuccessSpy = Mockito.spy(backendService);
        doAnswer(invocation -> {
            Request capturedRequest = requestCaptor.getValue();
            BackendService.NetworkCallback capturedCallback = callbackCaptor.getValue();
            if(capturedRequest.url().toString().endsWith("signin")) {
                capturedCallback.onResult("{ \"token\": \"tokenTest\"}");
            } else if(capturedRequest.url().toString().endsWith("signup")) {
                capturedCallback.onResult("{ \"token\": \"tokenTest\"}");
            } else if(capturedRequest.url().toString().endsWith("validate")) {
                capturedCallback.onResult("true");
            } else if(capturedRequest.url().toString().endsWith("lobby")) {
                capturedCallback.onResult("{\"gameSessionId\": \"7d7ef1d5-89d2-4c8a-8166-7c273b6b5202\"}");
            }
            return null;
        }).when(backendServiceSuccessSpy).executeRequest(requestCaptor.capture(), callbackCaptor.capture(), any());

    backendServiceErrorSpy = Mockito.spy(backendService);
    doAnswer(invocation -> {
        Request capturedRequest = requestCaptor.getValue();
        BackendService.NetworkCallback capturedCallback = callbackCaptor.getValue();
        if(capturedRequest.url().toString().endsWith("signin")) {
            capturedCallback.onResult("{ }");
        } else if(capturedRequest.url().toString().endsWith("signup")) {
            capturedCallback.onResult("{ }");
        } else if(capturedRequest.url().toString().endsWith("validate")) {
            capturedCallback.onResult("{ }");
        } else if(capturedRequest.url().toString().endsWith("lobby")) {
            capturedCallback.onResult("{}");
        }
        return null;
    }).when(backendServiceErrorSpy).executeRequest(requestCaptor.capture(), any(), callbackCaptor.capture());
}

    @Test
    void testMakeSignInRequestSuccess() throws JSONException {
        SignInRequest signInRequest = new SignInRequest("test", "test");
        BackendService.SignInCallback signInCallback = mock(BackendService.SignInCallback.class);

        backendServiceSuccessSpy.makeSignInRequest(signInRequest, signInCallback);

        verify(signInCallback, Mockito.timeout(1000)).onSuccess(any());
    }

    @Test
    void testMakeSignInRequestError() throws JSONException {
        SignInRequest signInRequest = new SignInRequest("test", "test");
        BackendService.SignInCallback signInCallback = mock(BackendService.SignInCallback.class);

        backendServiceErrorSpy.makeSignInRequest(signInRequest, signInCallback);

        verify(signInCallback, Mockito.timeout(1000)).onError(any());
    }

    @Test
    void testMakeSignUpRequestSuccess() throws JSONException {
        SignUpRequest signUpRequest = new SignUpRequest("test", "test");
        BackendService.SignUpCallback signUpCallback = mock(BackendService.SignUpCallback.class);

        backendServiceSuccessSpy.makeSignUpRequest(signUpRequest, signUpCallback);

        verify(signUpCallback, Mockito.timeout(1000)).onSuccess(any());
    }

    @Test
    void testMakeSignUpRequestError() throws JSONException {
        SignUpRequest signUpRequest = new SignUpRequest("test", "test");
        BackendService.SignUpCallback signUpCallback = mock(BackendService.SignUpCallback.class);

        backendServiceErrorSpy.makeSignUpRequest(signUpRequest, signUpCallback);

        verify(signUpCallback, Mockito.timeout(1000)).onError(any());
    }

    @Test
    void testMakeValidationRequestSuccess() throws JSONException {
        ValidationRequest validationRequest = new ValidationRequest("");
        BackendService.ValidationCallback validationCallback = mock(BackendService.ValidationCallback.class);

        backendServiceSuccessSpy.makeValidationRequest(validationRequest, validationCallback);

        verify(validationCallback, Mockito.timeout(1000)).onSuccess(true);
    }

    @Test
    void testMakeValidationRequestError() throws JSONException {
        ValidationRequest validationRequest = new ValidationRequest("");
        BackendService.ValidationCallback validationCallback = mock(BackendService.ValidationCallback.class);

        backendServiceErrorSpy.makeValidationRequest(validationRequest, validationCallback);

        verify(validationCallback, Mockito.timeout(1000)).onError(any());
    }

    @Test
    void testCreateLobbyRequestSuccess() throws JSONException {
        CreateLobbyRequest createLobbyRequest = new CreateLobbyRequest();
        BackendService.CreateLobbyCallback createLobbyCallback = mock(BackendService.CreateLobbyCallback.class);

        backendServiceSuccessSpy.createLobbyRequest(createLobbyRequest, createLobbyCallback);

        verify(createLobbyCallback, Mockito.timeout(1000)).onSuccess(any());
    }

    @Test
    void testCreateLobbyRequestError() throws JSONException {
        CreateLobbyRequest createLobbyRequest = new CreateLobbyRequest();
        BackendService.CreateLobbyCallback createLobbyCallback = mock(BackendService.CreateLobbyCallback.class);

        backendServiceErrorSpy.createLobbyRequest(createLobbyRequest, createLobbyCallback);

        verify(createLobbyCallback, Mockito.timeout(1000)).onError(any());
    }
}
