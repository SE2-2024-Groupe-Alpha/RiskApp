package se2.alpha.riskapp.model.websocket;

import lombok.Getter;

@Getter
public class GameWebsocketMessage implements IGameWebsocketMessage {
    CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    GameWebsocketMessageAction action;
}
