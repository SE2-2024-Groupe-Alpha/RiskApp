package se2.alpha.riskapp.data;
import dagger.Component;
import se2.alpha.riskapp.activities.*;
import se2.alpha.riskapp.service.BackendService;
import se2.alpha.riskapp.service.SecurePreferencesService;

import javax.inject.Singleton;

@Singleton
@Component(modules = {RiskAppModule.class})
public interface RiskAppComponent {
    void inject(Login login);
    void inject(MainMenu mainMenu);
    void inject(LobbyList lobbyList);
    void inject(Register register);
    void inject(Lobby lobby);

    void inject(BackendService backendService);

}