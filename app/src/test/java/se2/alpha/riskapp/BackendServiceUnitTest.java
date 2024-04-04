package se2.alpha.riskapp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.content.Context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import se2.alpha.riskapp.model.auth.SignInRequest;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.service.LobbyService;
import se2.alpha.riskapp.service.SecurePreferencesService;

public class BackendServiceUnitTest {
    BackendService backendService;
    GameService gameService;
    Context context;
    SecurePreferencesService securePreferencesService;

    @BeforeEach
    void setUp()
    {
        gameService = mock(GameService.class);
        context = mock(Context.class);
        securePreferencesService = mock(SecurePreferencesService.class);
        backendService = new BackendService(context, securePreferencesService, gameService);
    }

    @Test
    void testMakeSignInRequest() throws InterruptedException {
        SignInRequest signInRequest = new SignInRequest("test", "test");
        BackendService.SignInCallback signInCallback = mock(BackendService.SignInCallback.class);
        assertDoesNotThrow(() -> backendService.makeSignInRequest(signInRequest, signInCallback));
        Thread.sleep(2000);
        verify(signInCallback, times(1)).onSuccess(any());
        //verify(signInCallback, times(1)).onError(any());
    }
}
