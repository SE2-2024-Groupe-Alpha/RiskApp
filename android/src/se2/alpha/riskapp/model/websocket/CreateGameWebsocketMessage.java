package se2.alpha.riskapp.model.websocket;

import java.util.ArrayList;
import java.util.UUID;

import lombok.Getter;
import se2.alpha.riskapp.model.dol.Player;

@Getter
public class CreateGameWebsocketMessage implements IGameWebsocketMessage {
    private final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    GameWebsocketMessageAction action = GameWebsocketMessageAction.CREATE_GAME;
    private UUID gameSessionId;
    private ArrayList<Player> players;

    public CreateGameWebsocketMessage(UUID gameSessionId, ArrayList<Player> players)
    {
        this.gameSessionId = gameSessionId;
        this.players = players;
    }
}
