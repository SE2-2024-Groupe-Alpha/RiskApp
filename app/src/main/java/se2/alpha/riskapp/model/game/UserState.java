package se2.alpha.riskapp.model.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserState {
    private String userName;
    private Boolean isReady;
}
