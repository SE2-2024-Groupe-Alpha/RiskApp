package se2.alpha.riskapp.model.websocket;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserReadyWebsocketMessage implements IGameWebsocketMessage {
    private final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    GameWebsocketMessageAction action = GameWebsocketMessageAction.JOIN;
    private UUID gameSessionId;
    private Boolean isReady;
}
