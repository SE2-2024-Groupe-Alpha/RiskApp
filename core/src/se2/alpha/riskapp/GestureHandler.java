package se2.alpha.riskapp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GestureHandler extends GestureAdapter {
    private OrthographicCamera camera;
    private Vector3 touchPoint = new Vector3();
    private float initialScale = 1f;
    private Texture background;
    private float screenScaleFactor;

    public GestureHandler(OrthographicCamera camera, Texture background, float screenScaleFactor) {
        this.camera = camera;
        this.background = background;
        this.screenScaleFactor = screenScaleFactor;
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
