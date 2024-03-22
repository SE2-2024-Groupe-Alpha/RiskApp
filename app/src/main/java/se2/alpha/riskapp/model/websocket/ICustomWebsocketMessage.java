package se2.alpha.riskapp.model.websocket;

import lombok.Getter;

public interface ICustomWebsocketMessage {
    CustomWebsocketMessageType getType();
}