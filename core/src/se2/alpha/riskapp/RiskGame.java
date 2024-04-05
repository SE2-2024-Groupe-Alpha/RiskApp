package se2.alpha.riskapp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;


public class RiskGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img1, img2;
	float x1, y1, x2, y2; // Position variables
	float vx1, vy1, vx2, vy2; // Velocity variables

	@Override
	public void create () {
		batch = new SpriteBatch();
		img1 = new Texture("oyul.png");
		img2 = new Texture("sosig.png");

		x1 = 0;
		y1 = 0;
		x2 = img1.getWidth();
		y2 = 0;

		vx1 = 1;
		vy1 = 1;
		vx2 = -1;
		vy2 = -1;

		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				float touchX = screenX;
				float touchY = Gdx.graphics.getHeight() - screenY;

				if (touchX >= x1 && touchX <= x1 + img1.getWidth() && touchY >= y1 && touchY <= y1 + img1.getHeight()) {
					vx1 = -vx1;
					vy1 = -vy1;
					return true;
				}

				if (touchX >= x2 && touchX <= x2 + img2.getWidth() && touchY >= y2 && touchY <= y2 + img2.getHeight()) {
					vx2 = -vx2;
					vy2 = -vy2;
					return true;
				}

				return super.touchDown(screenX, screenY, pointer, button);
			}
		});
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		x1 += vx1;
		y1 += vy1;
		x2 += vx2;
		y2 += vy2;

		// Simple screen boundary collision to reverse direction
		if (x1 < 0 || x1 + img1.getWidth() > Gdx.graphics.getWidth()) vx1 *= -1;
		if (y1 < 0 || y1 + img1.getHeight() > Gdx.graphics.getHeight()) vy1 *= -1;
		if (x2 < 0 || x2 + img2.getWidth() > Gdx.graphics.getWidth()) vx2 *= -1;
		if (y2 < 0 || y2 + img2.getHeight() > Gdx.graphics.getHeight()) vy2 *= -1;

		batch.begin();
		batch.draw(img1, x1, y1);
		batch.draw(img2, x2, y2);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img1.dispose();
		img2.dispose();
	}
}
