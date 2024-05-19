package se2.alpha.riskapp.model.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import se2.alpha.riskapp.model.dol.Player;

@AllArgsConstructor
@Getter
public class GameStartedWebsocketMessage implements IGameWebsocketMessage {
    private final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    private final GameWebsocketMessageAction action = GameWebsocketMessageAction.GAME_STARTED;
    private Player activePlayer;
}
