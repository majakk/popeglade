package studio.coldstream.popeglade.gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import studio.coldstream.popeglade.gameobjects.Level;
import studio.coldstream.popeglade.gameobjects.Player;

/**
 * Created by Scalar on 16/09/2017.
 */

public class CollisionHandler {

    private LocationHandler lh;

    public CollisionHandler() {
        lh = new LocationHandler();
    }

    public void collision(float delta, Player player, Level level) {

        player.updateX(delta);

        //X-axis collisions

        if(!anyWallCollision(player, level)){
            player.makeMoveX();
        }

        //Y-axis collisions
        player.updateY(delta);

        if(!anyWallCollision(player, level)){
            player.makeMoveY();
        }

    }

    public boolean anyWallCollision(Player player, Level level) {
        for(int i = 0; i < 9; i++){
            if(lh.isTileWall(lh.playerNineTile(player,level).get(i))) {
                if(Intersector.overlaps(player.getBoundingRect(), lh.playerNineTileRect(player, level).get(i)))
                    return true;
            }
        }
        return false;
    }



}
