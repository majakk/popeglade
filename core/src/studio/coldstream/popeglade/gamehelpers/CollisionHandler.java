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

    public CollisionHandler() {}

    public void collision(float delta, Player player, Level level) {

        //X-axis collisions
        player.updateX(delta);

        if(!Intersector.overlaps(player.getBoundingRect(), level.getRect1())){
            player.makeMoveX();
        }

        //Y-axis collisions
        player.updateY(delta);

        if(!Intersector.overlaps(player.getBoundingRect(), level.getRect1())){
            player.makeMoveY();
        }
    }

}
