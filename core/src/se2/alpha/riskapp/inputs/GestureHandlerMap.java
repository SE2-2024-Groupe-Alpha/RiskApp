package se2.alpha.riskapp.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import se2.alpha.riskapp.RiskGame;
import se2.alpha.riskapp.dol.Country;
import se2.alpha.riskapp.dol.Player;
import se2.alpha.riskapp.events.*;
import se2.alpha.riskapp.logic.EventBus;
import se2.alpha.riskapp.ui.GameMap;
import se2.alpha.riskapp.ui.PixelReader;
import se2.alpha.riskapp.utils.Territories;
import se2.alpha.riskapp.utils.TerritoryNode;

import java.util.Objects;

public class GestureHandlerMap extends GestureAdapter {
    private OrthographicCamera camera;
    private Vector3 touchPoint = new Vector3();
    private float initialScale = 1f;
    private Texture background;
    private float screenScaleFactor;
    PixelReader pixelReader;
    private GameMap gameMap;

    private TerritoryNode attackFrom;
    private boolean attackNext = false;

    private TerritoryNode moveTroopFrom;
    public static boolean moveTroopNext = false;

    public GestureHandlerMap(OrthographicCamera camera, Texture background, float screenScaleFactor, GameMap gameMap) {
        this.camera = camera;
        this.background = background;
        this.screenScaleFactor = screenScaleFactor;
        this.gameMap = gameMap;
        this.pixelReader = new PixelReader(screenScaleFactor);

        EventBus.registerCallback(InitiateAttackEvent.class, event -> {
            attackNext = true;
        });

        EventBus.registerCallback(InitiateMoveTroopEvent.class, event -> {
            moveTroopNext = true;
        });

        EventBus.registerCallback(FinishMoveTroopEvent.class, event -> {
            moveTroopNext = false;
        });
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        System.out.println("Clicked at " + x + ", " + y);

        Vector3 worldCoordinates = camera.unproject(new Vector3(x, y, 0));


        int pixelY = (int)(worldCoordinates.y / screenScaleFactor);
        int flippedY = gameMap.background.getHeight() + (pixelY) * (-gameMap.background.getHeight()) / (gameMap.background.getHeight());
        Color color = this.pixelReader.getPixelColor((int)(worldCoordinates.x / screenScaleFactor), flippedY);

        TerritoryNode selectedTerritory = Territories.getTerritoryByColor(color);
        System.out.println(selectedTerritory);

        System.out.println("WORLD " + selectedTerritory + " = " + worldCoordinates.x + ", " + worldCoordinates.y);


        if (selectedTerritory != null) {

            EventBus.invoke(new SelectCountryEvent(selectedTerritory));

            if (
                    gameMap.board.getCountryByName(selectedTerritory.getName()).getOwner() != null &&
                    Objects.equals(gameMap.board.getCountryByName(selectedTerritory.getName()).getOwner().getName(), gameMap.board.playerName)
            ) {
                attackFrom = selectedTerritory;

                if (!moveTroopNext) {
                    moveTroopFrom = selectedTerritory;
                }

                TerritoryClickedEvent territoryClickedEvent = new TerritoryClickedEvent(selectedTerritory);

                EventBus.invoke(territoryClickedEvent);
                gameMap.onCountryClickedApplyTextureMask(selectedTerritory.getMask());
                gameMap.onCountryClickedApplyTextureMaskToNeighbouringCountries(selectedTerritory.getNeighborMasks());
            }

            if (            (gameMap.board.getCountryByName(selectedTerritory.getName()).getOwner() == null ||
                            !Objects.equals(gameMap.board.getCountryByName(selectedTerritory.getName()).getOwner().getName(), gameMap.board.playerName)) &&
                            attackNext && selectedTerritory.getAdjTerritories().contains(attackFrom)
            ) {
                Player attackingPlayer = RiskGame.getInstance().getPlayers().stream()
                        .filter(player -> gameMap.board.playerName.equals(player.getName()))
                        .findFirst()
                        .orElse(null);
                Player defendingPlayer = gameMap.board.getCountryByName(selectedTerritory.getName()).getOwner();

                Country attackingCountry = gameMap.board.getCountryByName(attackFrom.getName());
                Country defendingCountry = gameMap.board.getCountryByName(selectedTerritory.getName());

//                HERE DICE
                InitiateDiceEvent initiateDiceEvent = new InitiateDiceEvent();
                EventBus.invoke(initiateDiceEvent);

                TerritoryAttackEvent territoryAttackEvent = new TerritoryAttackEvent(
                        attackingPlayer.getName(),
                        defendingPlayer != null ? defendingPlayer.getName() : null,
                        attackingCountry.getName(),
                        defendingCountry.getName()
                );

                if(attackingCountry.getNumberOfTroops() > 1) {
                    EventBus.registerCallback(FinishAttackEvent.class, event ->{
                        EventBus.invoke(territoryAttackEvent);
                    });
                }


                attackNext = false;
            }

            if (gameMap.board.getCountryByName(selectedTerritory.getName()).getOwner() != null &&
                    (Objects.equals(gameMap.board.getCountryByName(selectedTerritory.getName()).getOwner().getName(), gameMap.board.playerName)) &&
                    moveTroopNext && selectedTerritory.getAdjTerritories().contains(moveTroopFrom)
            ){
                Player movingTroopPlayer = RiskGame.getInstance().getPlayers().stream()
                        .filter(player -> gameMap.board.playerName.equals(player.getName()))
                        .findFirst()
                        .orElse(null);

                Country moveFromCountry = gameMap.board.getCountryByName(moveTroopFrom.getName());
                Country moveToCountry = gameMap.board.getCountryByName(selectedTerritory.getName());

                MoveTroopEvent moveTroopEvent = new MoveTroopEvent(
                    movingTroopPlayer.getName(),
                        moveFromCountry.getName(),
                        moveToCountry.getName(),
                        1
                );

                EventBus.invoke(moveTroopEvent);
            }

        } else {
            gameMap.clearCountryTextureMasks();
            TerritoryClickedClearEvent territoryClickedClearEvent = new TerritoryClickedClearEvent();
            EventBus.invoke(territoryClickedClearEvent);
            moveTroopNext = false;
        }

        return true;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        camera.unproject(touchPoint.set(x, y, 0));
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.unproject(touchPoint.set(x, y, 0));

        camera.translate(-deltaX * camera.zoom, deltaY * camera.zoom);
        clampCamera();
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        Vector2 oldInitial = initialPointer1.cpy().sub(initialPointer2);
        Vector2 newDistance = pointer1.cpy().sub(pointer2);
        float ratio = newDistance.len() / oldInitial.len();
        camera.zoom = Math.max(0.1f, Math.min(initialScale / ratio, 1f));
        clampCamera();
        return true;
    }

    @Override
    public void pinchStop() {
        initialScale = camera.zoom;
        clampCamera();
    }

    public void clampCamera() {
        // Half of the viewport dimensions, adjusted for zoom.
        float viewportWidthHalf = camera.viewportWidth * camera.zoom * 0.5f;
        float viewportHeightHalf = camera.viewportHeight * camera.zoom * 0.5f;

        // Dimensions of the background.
        float backgroundWidth = background.getWidth() * screenScaleFactor;
        float backgroundHeight = Gdx.graphics.getHeight();

        // Minimum and maximum coordinates for the camera.
        float minX = viewportWidthHalf;
        float minY = viewportHeightHalf;
        float maxX = backgroundWidth - viewportWidthHalf; // Corrected maximum X calculation
        float maxY = backgroundHeight - viewportHeightHalf;

        // Current position of the camera.
        Vector3 position = camera.position;

        // Clamping the camera's x and y positions within the calculated bounds.
        position.x = Math.max(minX, Math.min(position.x, maxX)); // Use maxX instead of backgroundWidth
        position.y = Math.max(minY, Math.min(position.y, maxY)); // Correct as it was

        // Setting the corrected position and updating the camera.
        camera.position.set(position);
        camera.update();
    }
}
