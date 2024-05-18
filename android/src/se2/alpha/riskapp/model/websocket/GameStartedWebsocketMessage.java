package se2.alpha.riskappbackend.model.websocket;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import se2.alpha.riskappbackend.model.db.Player;

@AllArgsConstructor
@Getter
public class GameStartedWebsocketMessage implements IGameWebsocketMessage {
    private final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    private final GameWebsocketMessageAction action = GameWebsocketMessageAction.GAME_STARTED;
    private Player activePlayer;
}
