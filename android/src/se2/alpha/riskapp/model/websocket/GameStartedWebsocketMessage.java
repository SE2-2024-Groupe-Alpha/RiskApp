package se2.alpha.riskapp.model.websocket;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import se2.alpha.riskapp.dol.Player;

@AllArgsConstructor
@Getter
public class GameStartedWebsocketMessage implements IGameWebsocketMessage {
    private final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    private final GameWebsocketMessageAction action = GameWebsocketMessageAction.GAME_STARTED;
    private ArrayList<Player> players;
    private Player activePlayer;

    @Override
    public CustomWebsocketMessageType getType() {
        return type;
    }

    @Override
    public GameWebsocketMessageAction getAction() {
        return action;
    }
}
