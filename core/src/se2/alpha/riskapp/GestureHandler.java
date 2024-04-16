package se2.alpha.riskapp;

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

    public GestureHandler(OrthographicCamera camera, Texture background) {
        this.camera = camera;
        this.background = background;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        camera.unproject(touchPoint.set(x, y, 0));
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.unproject(touchPoint.set(x, y, 0));
        camera.translate(-deltaX, deltaY);
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        Vector2 oldInitial = initialPointer1.cpy().sub(initialPointer2);
        Vector2 newDistance = pointer1.cpy().sub(pointer2);
        float ratio = newDistance.len() / oldInitial.len();

        camera.zoom = Math.max(0.1f, Math.min(initialScale / ratio, 10f));
        return true;
    }

    @Override
    public void pinchStop() {
        initialScale = camera.zoom;
    }
}
