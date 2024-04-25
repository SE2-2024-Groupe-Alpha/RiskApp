package se2.alpha.riskapp;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import se2.alpha.riskapp.ui.OverlayShowAllRiskCards;
import se2.alpha.riskapp.ui.OverlayShowNewRiskCard;

public class GestureHandlerShowNewRiskCard extends GestureDetector.GestureAdapter {
    OverlayShowNewRiskCard overlayShowNewRiskCard;

    public GestureHandlerShowNewRiskCard(OverlayShowNewRiskCard overlayShowNewRiskCard)
    {
        this.overlayShowNewRiskCard = overlayShowNewRiskCard;
    }
    @Override
    public boolean tap(float x, float y, int count, int button) {
        overlayShowNewRiskCard.hide();
        return true;
    }
}
