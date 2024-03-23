package se2.alpha.riskapp.model.websocket;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserReadyWebsocketMessage implements IGameWebsocketMessage {
    private final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    GameWebsocketMessageAction action = GameWebsocketMessageAction.USER_READY;
    private final UUID gameSessionId;
    private final Boolean isReady;

    public UserReadyWebsocketMessage(UUID gameSessionId, Boolean isReady) {
        this.gameSessionId = gameSessionId;
        this.isReady = isReady;
    }
}
