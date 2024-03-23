package se2.alpha.riskapp.model.websocket;

import lombok.Getter;

import java.util.UUID;

@Getter
public class JoinWebsocketMessage implements IGameWebsocketMessage {
    private final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    GameWebsocketMessageAction action = GameWebsocketMessageAction.JOIN;
    private UUID gameSessionId;

    public JoinWebsocketMessage(UUID gameSessionId) {
        this.gameSessionId = gameSessionId;
    }
}
