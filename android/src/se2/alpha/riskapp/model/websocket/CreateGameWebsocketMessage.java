package se2.alpha.riskapp.model.websocket;

import java.util.ArrayList;
import java.util.UUID;

import lombok.Getter;
import se.alpha.riskappbackend.model.db.Player;

@Getter
public class CreateGameWebsocketMessage implements IGameWebsocketMessage {
    private static final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    private static final GameWebsocketMessageAction action = GameWebsocketMessageAction.CREATE_GAME;
    private UUID gameSessionId;
    private ArrayList<Player> players;

    @Override
    public CustomWebsocketMessageType getType() {
        return type;
    }

    @Override
    public GameWebsocketMessageAction getAction() {
        return action;
    }
}
