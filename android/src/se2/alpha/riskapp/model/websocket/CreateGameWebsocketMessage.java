package se2.alpha.riskapp.model.websocket;

import java.util.ArrayList;
import java.util.UUID;

import lombok.AllArgsConstructor;
import se2.alpha.riskapp.model.dol.Player;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateGameWebsocketMessage implements IGameWebsocketMessage {
    private final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    private final GameWebsocketMessageAction action = GameWebsocketMessageAction.CREATE_GAME;
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
