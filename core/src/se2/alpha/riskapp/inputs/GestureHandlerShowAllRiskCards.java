package se2.alpha.riskapp.inputs;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import se2.alpha.riskapp.ui.OverlayShowAllRiskCards;

public class GestureHandlerShowAllRiskCards extends GestureDetector.GestureAdapter {
    OverlayShowAllRiskCards overlayShowRiskCards;
    ImageButton buttonLeft, buttonRight;
    public GestureHandlerShowAllRiskCards(OverlayShowAllRiskCards overlayShowRiskCards, ImageButton buttonLeft, ImageButton buttonRight)
    {
        this.overlayShowRiskCards = overlayShowRiskCards;
        this.buttonLeft = buttonLeft;
        this.buttonRight = buttonRight;
    }
    @Override
    public boolean tap(float x, float y, int count, int button) {
        if(!isTapOnButton(x, y))
            overlayShowRiskCards.hide();
        return true;
    }

    private boolean isTapOnButton(float x, float y)
    {
        return isTapOnButtonLeft(x, y) || isTapOnButtonRight(x, y);
    }

    private boolean isTapOnButtonLeft(float x, float y)
    {
        return x > buttonLeft.getOriginX()
                && x < buttonLeft.getOriginX() + buttonLeft.getWidth()
                && y > buttonLeft.getOriginY()
                && y < buttonLeft.getOriginY() + buttonLeft.getHeight();
    }

    private boolean isTapOnButtonRight(float x, float y)
    {
        return x > buttonRight.getOriginX()
                && x < buttonRight.getOriginX() + buttonRight.getWidth()
                && y > buttonRight.getOriginY()
                && y < buttonRight.getOriginY() + buttonRight.getHeight();
    }
}
