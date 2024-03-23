package se2.alpha.riskapp.model.websocket;

public interface IGameWebsocketMessage extends ICustomWebsocketMessage {
    GameWebsocketMessageAction getAction();
}