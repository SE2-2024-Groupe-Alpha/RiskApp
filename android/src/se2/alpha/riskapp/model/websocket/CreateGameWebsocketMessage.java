package se2.alpha.riskappbackend.model.websocket;

import java.util.ArrayList;
import java.util.UUID;

import lombok.Getter;
import se2.alpha.riskappbackend.model.db.Player;

@Getter
public class CreateGameWebsocketMessage implements IGameWebsocketMessage {
    private final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    GameWebsocketMessageAction action = GameWebsocketMessageAction.CREATE_GAME;
    private UUID gameSessionId;
    private ArrayList<Player> players;
}
