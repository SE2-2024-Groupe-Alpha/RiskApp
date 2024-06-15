package tests;

import android.content.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.GameLogicService;
import se2.alpha.riskapp.service.GameService;
import se2.alpha.riskapp.model.websocket.AttackWebsocketMessage;
import se2.alpha.riskapp.model.websocket.CreateGameWebsocketMessage;
import se2.alpha.riskapp.model.websocket.EndTurnWebsocketMessage;
import se2.alpha.riskapp.model.websocket.MoveTroopsWebsocketMessage;
import se2.alpha.riskapp.model.websocket.SeizeCountryWebsocketMessage;
import se2.alpha.riskapp.model.websocket.StrengthenCountryWebsocketMessage;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class GameLogicServiceTest {

    @Mock
    private BackendService mockBackendService;
    @Mock
    private GameService mockGameService;
    @Mock
    private Context mockContext;

    private GameLogicService gameLogicService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(mockGameService.getSessionId()).thenReturn(UUID.randomUUID());
        gameLogicService = new GameLogicService(mockContext, mockGameService, mockBackendService);
    }

    @Test
    public void testEndTurn() {
        gameLogicService.endTurn();
        verify(mockBackendService).sendMessage(any(EndTurnWebsocketMessage.class));
    }


    @Test
    public void testSeizeCountry() {
        gameLogicService.seizeCountry("player1", "CountryX", 5);
        verify(mockBackendService).sendMessage(any(SeizeCountryWebsocketMessage.class));
    }

    @Test
    public void testStrengthenCountry() {
        gameLogicService.strengthenCountry("player1", "CountryY", 3);
        verify(mockBackendService).sendMessage(any(StrengthenCountryWebsocketMessage.class));
    }
    @Test
    public void testMoveTroops() {
        gameLogicService.moveTroops("player1", "CountryA", "CountryB", 10);
        verify(mockBackendService).sendMessage(any(MoveTroopsWebsocketMessage.class));
    }

    @Test
    public void testAttack() {
        gameLogicService.attack("attacker", "defender", "CountryA", "CountryB");
        verify(mockBackendService).sendMessage(any(AttackWebsocketMessage.class));
    }


}
