package se2.alpha.riskapp.inputs;

import com.badlogic.gdx.input.GestureDetector;

import se2.alpha.riskapp.ui.OverlayShowNewRiskCard;

public class GestureHandlerShowNewRiskCard extends GestureDetector.GestureAdapter {
   private  final OverlayShowNewRiskCard overlayShowNewRiskCard;

    public GestureHandlerShowNewRiskCard(OverlayShowNewRiskCard overlayShowNewRiskCard)
    {
        this.overlayShowNewRiskCard = overlayShowNewRiskCard;
    }

    /**
     * Handles tap events. Hides the overlay when tapped.
     *
     * @param x the x coordinate of the tap
     * @param y the y coordinate of the tap
     * @param count the number of taps
     * @param button the button pressed
     * @return true to indicate the event was handled
     */
    @Override
    public boolean tap(float x, float y, int count, int button) {
        overlayShowNewRiskCard.hide();
        return true;
    }
}
