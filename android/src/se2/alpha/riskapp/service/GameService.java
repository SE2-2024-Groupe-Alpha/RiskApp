package se2.alpha.riskapp.service;

import android.content.Context;
import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;
import lombok.Getter;
import lombok.Setter;
import se2.alpha.riskapp.model.dol.Player;
import se2.alpha.riskapp.model.websocket.CreateGameWebsocketMessage;
import se2.alpha.riskapp.model.websocket.JoinWebsocketMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;


public class GameService {
    @Getter
    @Setter
    private UUID sessionId = null;

    @Getter
    private final MutableLiveData<Map<String, Boolean>> userStates = new MutableLiveData<>(new HashMap<>());
    @Getter
    @Setter
    private MutableLiveData<Boolean> gameStarted = new MutableLiveData<>();

    public GameService(Context context) {
    }

    public void updateUsers(Map<String, Boolean> newUserStates){
        userStates.postValue(newUserStates);
    }
}
