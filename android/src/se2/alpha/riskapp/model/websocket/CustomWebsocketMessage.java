package se2.alpha.riskapp.model.websocket;

import lombok.Getter;

@Getter
public class CustomWebsocketMessage implements ICustomWebsocketMessage {
    private CustomWebsocketMessageType type;

}
