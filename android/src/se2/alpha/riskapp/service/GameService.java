package se2.alpha.riskapp.service;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import com.badlogic.gdx.Game;
import lombok.Getter;
import lombok.Setter;
import se2.alpha.riskapp.RiskGame;
import se2.alpha.riskapp.dol.Board;
import se2.alpha.riskapp.dol.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Getter
public class GameService {
    @Setter
    private UUID sessionId = null;
    @Setter
    private RiskGame riskGame = null;

    private final MutableLiveData<Map<String, Boolean>> userStates = new MutableLiveData<>(new HashMap<>());
    private MutableLiveData<Boolean> gameStarted = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Player>> players = new MutableLiveData<>();
    private MutableLiveData<Player> activePlayer = new MutableLiveData<>();

    public GameService(Context context) {
    }

    public void updateUsers(Map<String, Boolean> newUserStates){
        userStates.postValue(newUserStates);
    }

    public void updatePlayers(ArrayList<Player> newPlayers){
        players.postValue(newPlayers);
    }

    public void setActivePlayer(Player newActivePlayer){
        activePlayer.postValue(newActivePlayer);
    }

    public RiskGame startGame(){
        return riskGame = RiskGame.getInstance();
    }

    public void syncGame(Board board){
        riskGame.updateBoard(board);
    }
}
