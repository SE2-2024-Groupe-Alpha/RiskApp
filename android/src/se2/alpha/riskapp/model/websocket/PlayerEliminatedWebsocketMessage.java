package se2.alpha.riskapp.model.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerEliminatedWebsocketMessage implements IGameWebsocketMessage {
    private static final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    private static final GameWebsocketMessageAction action = GameWebsocketMessageAction.PLAYER_ELIMINATED;
    private String eliminatedPlayerId;

    @Override
    public CustomWebsocketMessageType getType() {
        return type;
    }

    @Override
    public GameWebsocketMessageAction getAction() {
        return action;
    }
}
