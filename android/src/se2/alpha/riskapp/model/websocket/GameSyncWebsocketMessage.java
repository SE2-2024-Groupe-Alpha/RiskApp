package se2.alpha.riskapp.model.websocket;

import lombok.Getter;
import se2.alpha.riskapp.dol.Board;
import se2.alpha.riskapp.dol.Country;
import se2.alpha.riskapp.dol.Player;
import se2.alpha.riskapp.model.game.GameSession;

import java.util.List;

@Getter
public class GameSyncWebsocketMessage implements IGameWebsocketMessage {
    private final CustomWebsocketMessageType type = CustomWebsocketMessageType.GAME;
    private final GameWebsocketMessageAction action = GameWebsocketMessageAction.GAME_SYNC;

    private final List<Country> countries;
    private final Player activePlayerName;
    private final List<Player> players;

    public GameSyncWebsocketMessage() {
        this.countries = null;
        this.players = null;
        this.activePlayerName = null;
    }

    @Override
    public CustomWebsocketMessageType getType() {
        return type;
    }

    @Override
    public GameWebsocketMessageAction getAction() {
        return action;
    }
}