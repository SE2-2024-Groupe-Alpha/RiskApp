package se2.alpha.riskapp.service;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class GameService {
    @Getter
    private final MutableLiveData<List<String>> userNames = new MutableLiveData<>(new ArrayList<>());

    public GameService(Context context) {
    }

    public void updateUsers(List<String> newUserNames){
        userNames.postValue(newUserNames);
    }


}
