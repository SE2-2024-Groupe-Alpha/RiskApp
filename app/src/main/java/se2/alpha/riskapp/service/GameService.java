package se2.alpha.riskapp.service;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class GameService {
    @Getter
    @Setter
    private UUID sessionId = null;

    @Getter
    private final MutableLiveData<Map<String, Boolean>> userStates = new MutableLiveData<>(new HashMap<>());

    public GameService(Context context) {
    }

    public void updateUsers(Map<String, Boolean> newUserStates){
        userStates.postValue(newUserStates);
    }
}
