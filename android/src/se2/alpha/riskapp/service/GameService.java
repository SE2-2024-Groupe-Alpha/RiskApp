package se2.alpha.riskapp.service;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import lombok.Getter;
import lombok.Setter;
import se2.alpha.riskapp.dol.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Getter
public class GameService {
    @Setter
    private UUID sessionId = null;

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
}
