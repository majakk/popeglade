package studio.coldstream.popeglade.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Scalar on 24/09/2017.
 */

public class HeadUpDisplay {

    Player player;

    public HeadUpDisplay(Player p) {
        this.player = p;
    }

    public void render(float delta, float runTime, SpriteBatch batcher, ShapeRenderer shapeRenderer, OrthographicCamera cam) {
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //shapeRenderer.setColor(1, 1, 1, 1.0f);
        Gdx.gl.glLineWidth(4);

            float yOffset;
            if (player.getY() > cam.position.y - 0.5f * (cam.viewportHeight / 2))
                yOffset = cam.position.y - 0.8f * (cam.viewportHeight / 2) - 32;
            else
                yOffset = cam.position.y + 0.8f * (cam.viewportHeight / 2);

            for (int i = 0; i < 10; i++) {
                if(i == player.getInventory().getActiveItemSlot()){
                    shapeRenderer.setColor(0, 1, 1, 1.0f);
                }
                else {
                    shapeRenderer.setColor(1, 1, 1, 1.0f);
                }
                    shapeRenderer.rect(cam.position.x - (5 * 32) + i * 32, yOffset, 32, 32);
            }

        shapeRenderer.end();

    }
}
