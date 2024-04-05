package se2.alpha.riskapp.model.websocket;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserLeaveWebsocketMessage implements IGameWebsocketMessage {
    private final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    GameWebsocketMessageAction action = GameWebsocketMessageAction.USER_LEAVE;
    private UUID gameSessionId;

    public UserLeaveWebsocketMessage(UUID gameSessionId) {
        this.gameSessionId = gameSessionId;
    }
}
