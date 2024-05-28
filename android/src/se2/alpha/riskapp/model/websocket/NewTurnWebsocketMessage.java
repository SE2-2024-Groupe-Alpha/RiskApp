package se2.alpha.riskapp.model.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import se2.alpha.riskapp.model.dol.Player;

@AllArgsConstructor
@Getter
public class NewTurnWebsocketMessage implements IGameWebsocketMessage {
    private static final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    private static final GameWebsocketMessageAction action = GameWebsocketMessageAction.NEW_TURN;
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
