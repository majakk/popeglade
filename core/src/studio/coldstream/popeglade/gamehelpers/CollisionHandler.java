package studio.coldstream.popeglade.gamehelpers;

import com.badlogic.gdx.math.Intersector;

import studio.coldstream.popeglade.gameobjects.Terrain;
import studio.coldstream.popeglade.gameobjects.Player;

/**
 * Created by Scalar on 16/09/2017.
 */

public class CollisionHandler {

    private boolean wallsEnabled = true;

    private LocationHandler lh;

    public CollisionHandler() {
        lh = new LocationHandler();
    }

    /*public void update(float delta, Player player, Terrain terrain) {

        //X-axis collisions
        player.updateX(delta);

        if(!anyWallCollision(player, terrain)){
            player.makeMoveX();
        }

        //Y-axis collisions
        player.updateY(delta);

        if(!anyWallCollision(player, terrain)){
            player.makeMoveY();
        }

    }*/

    /*private boolean anyWallCollision(Player player, Terrain terrain) {
        if(wallsEnabled) {
            for (int i = 0; i < 9; i++) {
                if (lh.isTileWall(lh.playerNineTile(player, terrain).get(i))) {
                    if (Intersector.overlaps(player.getBoundingRect(), lh.playerNineTileRect(player, terrain).get(i)))
                        return true;
                }
            }
        }
        return false;
    }*/



}
