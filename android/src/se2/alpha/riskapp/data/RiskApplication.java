package se2.alpha.riskapp.data;

import android.app.Application;
import lombok.Getter;

@Getter
public class RiskApplication extends Application {
    private RiskAppComponent riskAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        riskAppComponent = DaggerRiskAppComponent.builder()
                .riskAppModule(new RiskAppModule(this))
                .build();
    }

}
