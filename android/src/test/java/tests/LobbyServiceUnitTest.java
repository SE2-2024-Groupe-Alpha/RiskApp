package tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import se2.alpha.riskapp.activities.LobbyList;
import se2.alpha.riskapp.data.LobbyArrayAdapter;
import se2.alpha.riskapp.model.game.CreateLobbyRequest;
import se2.alpha.riskapp.model.game.GameSession;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.service.LobbyService;

public class LobbyServiceUnitTest {
    BackendService backendService;
    GameService gameService;
    LobbyService lobbyService;
    Context context;

    @BeforeEach
    void setUp()
    {
        gameService = mock(GameService.class);
        backendService = mock(BackendService.class);
        context = mock(Context.class);
        lobbyService = new LobbyService(context, gameService, backendService);
    }

    @Test
    void testJoinLobby()
    {
        UUID sessionId = UUID.randomUUID();
        lobbyService.joinLobby(sessionId);
        verify(backendService, times(1)).sendMessage(any());
        verify(gameService, times(1)).setSessionId(sessionId);
    }

    @Test
    void testLeaveLobby()
    {
        lobbyService.leaveLobby();
        verify(backendService, times(1)).sendMessage(any());
    }

    @Test
    void testCreateLobby()
    {
        String lobbyTitle = "test";
        LobbyService.LobbyCreationResultCallback lobbyCreationResultCallback = mock(LobbyService.LobbyCreationResultCallback.class);
        lobbyService.createLobby(lobbyTitle, lobbyCreationResultCallback);
        try {
            verify(backendService, times(1)).createLobbyRequest(any(), any());
        }
        catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    };

    @Test
    void testGetAvailableLobbies()
    {
        LobbyService.GetAvailableLobbiesCallback getAvailableLobbiesCallback = mock(LobbyService.GetAvailableLobbiesCallback.class);
        lobbyService.getAvailableLobbies(getAvailableLobbiesCallback);
        verify(backendService, times(1)).makeLobbyRequest(any());
    };

    @Test
    void testUpdatePlayerStatus()
    {
        lobbyService.updatePlayerStatus(true);
        verify(backendService, times(1)).sendMessage(any());
    };
}