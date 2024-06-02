package se2.alpha.riskapp.model.websocket;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class JoinWebsocketMessage implements IGameWebsocketMessage {
    private CustomWebsocketMessageType type;
    private GameWebsocketMessageAction action;
    private UUID gameSessionId;

    public JoinWebsocketMessage(UUID gameSessionId) {
        this.gameSessionId = gameSessionId;
        this.type = CustomWebsocketMessageType.GAME;
        this.action = GameWebsocketMessageAction.JOIN;
    }

    public void setGameSessionId(UUID gameSessionId) {
        this.gameSessionId = gameSessionId;
    }

    @Override
    public CustomWebsocketMessageType getType() {
        return type;
    }

    @Override
    public GameWebsocketMessageAction getAction() {
        return action;
    }

    public UUID getGameSessionId() {
        return gameSessionId;
    }
}
