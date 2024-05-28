package se2.alpha.riskapp.model.websocket;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLeaveWebsocketMessage implements IGameWebsocketMessage {
    private static final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    private static final GameWebsocketMessageAction action = GameWebsocketMessageAction.USER_LEAVE;
    private UUID gameSessionId;

    @Override
    public CustomWebsocketMessageType getType() {
        return type;
    }

    @Override
    public GameWebsocketMessageAction getAction() {
        return action;
    }
}
