package tests;

import android.content.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import se2.alpha.riskapp.dol.Country;
import se2.alpha.riskapp.dol.RiskCard;
import se2.alpha.riskapp.dol.RiskCardType;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.RiskCardService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class RiskCardServiceUnitTest {

    @Mock
    private BackendService backendService;

    @Mock
    private Context context;

    @InjectMocks
    private RiskCardService riskCardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNewRiskCard() {
        RiskCardService.GetNewRiskCardCallback callback = mock(RiskCardService.GetNewRiskCardCallback.class);
        RiskCard mockRiskCard = new RiskCard(RiskCardType.INFANTRY, new Country());

        doAnswer(invocation -> {
            ((BackendService.GetNewRiskCardCallback) invocation.getArgument(1)).onSuccess(mockRiskCard);
            return null;
        }).when(backendService).getNewRiskCardRequest(anyString(), any());

        riskCardService.getNewRiskCard("testId", callback);

        verify(callback).onResult(mockRiskCard);
    }

    @Test
    void testGetNewRiskCardError() {
        RiskCardService.GetNewRiskCardCallback callback = mock(RiskCardService.GetNewRiskCardCallback.class);

        doAnswer(invocation -> {
            ((BackendService.GetNewRiskCardCallback) invocation.getArgument(1)).onError("Error occurred");
            return null;
        }).when(backendService).getNewRiskCardRequest(anyString(), any());

        riskCardService.getNewRiskCard("testId", callback);

        verify(callback).onError("Error occurred");
    }

    @Test
    void testGetAllRiskCardsByPlayer() {
        RiskCardService.GetAllRiskCardsByPlayerCallback callback = mock(RiskCardService.GetAllRiskCardsByPlayerCallback.class);
        List<RiskCard> riskCards = Arrays.asList(new RiskCard(RiskCardType.ARTILLERY, new Country()), new RiskCard(RiskCardType.INFANTRY, new Country()));

        doAnswer(invocation -> {
            ((BackendService.GetAllRiskCardsByPlayerCallback) invocation.getArgument(1)).onSuccess(riskCards);
            return null;
        }).when(backendService).getAllRiskCardsByPlayerRequest(anyString(), any());

        riskCardService.getAllRiskCardsByPlayer("playerId", callback);

        verify(callback).onResult(riskCards);
    }

    @Test
    void testGetAllRiskCardsByPlayerError() {
        RiskCardService.GetAllRiskCardsByPlayerCallback callback = mock(RiskCardService.GetAllRiskCardsByPlayerCallback.class);

        doAnswer(invocation -> {
            ((BackendService.GetAllRiskCardsByPlayerCallback) invocation.getArgument(1)).onError("Error occurred");
            return null;
        }).when(backendService).getAllRiskCardsByPlayerRequest(anyString(), any());

        riskCardService.getAllRiskCardsByPlayer("playerId", callback);

        verify(callback).onError("Error occurred");
    }

    @Test
    void testCanPlayerTradeRiskCards() {
        RiskCardService.CanPlayerTradeRiskCardsCallback callback = mock(RiskCardService.CanPlayerTradeRiskCardsCallback.class);

        doAnswer(invocation -> {
            ((BackendService.CanPlayerTradeRiskCardsCallback) invocation.getArgument(1)).onSuccess(true);
            return null;
        }).when(backendService).getCanPlayerTradeRiskCardsRequest(anyString(), any());

        riskCardService.canPlayerTradeRiskCards("playerId", callback);

        verify(callback).onResult(true);
    }

    @Test
    void testCanPlayerTradeRiskCardsError() {
        RiskCardService.CanPlayerTradeRiskCardsCallback callback = mock(RiskCardService.CanPlayerTradeRiskCardsCallback.class);

        doAnswer(invocation -> {
            ((BackendService.CanPlayerTradeRiskCardsCallback) invocation.getArgument(1)).onError("Error occurred");
            return null;
        }).when(backendService).getCanPlayerTradeRiskCardsRequest(anyString(), any());

        riskCardService.canPlayerTradeRiskCards("playerId", callback);

        verify(callback).onError("Error occurred");
    }

    @Test
    void testPlayerTradeRiskCards() {
        RiskCardService.PlayerTradeRiskCardsCallback callback = mock(RiskCardService.PlayerTradeRiskCardsCallback.class);

        doAnswer(invocation -> {
            ((BackendService.PlayerTradeRiskCardsCallback) invocation.getArgument(1)).onSuccess();
            return null;
        }).when(backendService).getPlayerTradeRiskCardsRequest(anyString(), any());

        riskCardService.playerTradeRiskCards("playerId", callback);

        verify(callback).onResult();
    }

    @Test
    void testPlayerTradeRiskCardsError() {
        RiskCardService.PlayerTradeRiskCardsCallback callback = mock(RiskCardService.PlayerTradeRiskCardsCallback.class);

        doAnswer(invocation -> {
            ((BackendService.PlayerTradeRiskCardsCallback) invocation.getArgument(1)).onError("Error occurred");
            return null;
        }).when(backendService).getPlayerTradeRiskCardsRequest(anyString(), any());

        riskCardService.playerTradeRiskCards("playerId", callback);

        verify(callback).onError("Error occurred");
    }
}
