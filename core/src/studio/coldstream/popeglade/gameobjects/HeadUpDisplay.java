package studio.coldstream.popeglade.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;

import static studio.coldstream.popeglade.gamehelpers.AssetLoader.items;

/**
 * Created by Scalar on 24/09/2017.
 */

public class HeadUpDisplay {

    Player player;

    public HeadUpDisplay(Player p) {
        this.player = p;
    }

    public void render(float delta, float runTime, SpriteBatch batcher, ShapeRenderer shapeRenderer, BitmapFont font, OrthographicCamera cam) {
        shapeRenderer.setProjectionMatrix(cam.combined);

        float yOffset;
        if (player.getY() > cam.position.y - 0.5f * (cam.viewportHeight / 2))
            yOffset = cam.position.y - 0.8f * (cam.viewportHeight / 2) - 32;
        else
            yOffset = cam.position.y + 0.8f * (cam.viewportHeight / 2);

        //Inventory frames
        shapeRenderer.setColor(0.8f, 0.8f, 0.8f, 1.0f);
        Gdx.gl.glLineWidth(4);
        for (int i = 0; i < player.getInventory().getMaxItemSlots(); i++) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.rect(cam.position.x - ((player.getInventory().getMaxItemSlots() / 2) * 32) + i * 32, yOffset, 32, 32);
            shapeRenderer.end();
        }

        //Active ItemSlot frame
        shapeRenderer.setColor(1, 1, 1, 1.0f);
        Gdx.gl.glLineWidth(5);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(cam.position.x - ((player.getInventory().getMaxItemSlots() / 2) * 32) + player.getInventory().getActiveItemSlot() * 32, yOffset, 32, 32);
        shapeRenderer.end();

        if(player.getInventory().getPocket().size() != 0) {
            batcher.setProjectionMatrix(cam.combined);
            batcher.begin();
                for (int i = 0; i < player.getInventory().getPocket().size(); i++) {
                    batcher.draw(AssetLoader.items[player.getInventory().getPocket().get(i).getId()], cam.position.x - ((player.getInventory().getMaxItemSlots() / 2) * 32) + i * 32, yOffset);

                    //The correct way to do this is to use number-sprites. Using BitmapFont on the fly like this is not recommended
                    if(player.getInventory().getPocket().get(i).getStackSize() > 1)
                        font.draw(batcher, player.getInventory().getPocket().get(i).getStackSize() + "", cam.position.x - ((player.getInventory().getMaxItemSlots() / 2) * 32) + i * 32 + 4, yOffset + 28);
                }
            batcher.end();
        }

    }
}
