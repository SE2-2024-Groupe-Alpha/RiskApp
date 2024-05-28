package se2.alpha.riskapp.model.websocket;

import java.util.UUID;

import lombok.Getter;

@Getter
public class UserReadyWebsocketMessage implements IGameWebsocketMessage {
    private static final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    private static final GameWebsocketMessageAction action = GameWebsocketMessageAction.JOIN;
    private UUID gameSessionId;
    private Boolean isReady;

    @Override
    public CustomWebsocketMessageType getType() {
        return type;
    }

    @Override
    public GameWebsocketMessageAction getAction() {
        return action;
    }
}
