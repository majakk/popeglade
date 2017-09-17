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

    private Rectangle intersectionX = new Rectangle();
    private Rectangle intersectionY = new Rectangle();

    public CollisionHandler() {
    }

    public void collision(Player player, Level level) {
        intersectionX = new Rectangle();
        intersectionY = new Rectangle();

        Intersector.intersectRectangles(player.getBoundingRectX(), level.getRect1(), intersectionX);
        Intersector.intersectRectangles(player.getBoundingRectY(), level.getRect1(), intersectionY);

        if (intersectionY.area() > 0.0f && intersectionY.getAspectRatio() > 1.0f) { //collision top-bottom

            player.makeMoveX();

            Gdx.app.log("GameWorld", "CollisionY!! " + intersectionY.area() + "   " + intersectionY.getAspectRatio() + "   " );
            //intersection.setWidth(intersection.width + 0.1f);
        } else if (intersectionX.area() > 0.0f || intersectionX.getAspectRatio() <= 1.0f) { //collision right-left

            player.makeMoveY();


            Gdx.app.log("GameWorld", "CollisionX!! " + intersectionX.area() + "   " + intersectionX.getAspectRatio());
            //intersection.setWidth(intersection.width + 0.1f);
        } else {
            player.makeMoveX();
            player.makeMoveY();
        }
    }



}
