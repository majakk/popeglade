package studio.coldstream.popeglade.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import studio.coldstream.popeglade.gamehelpers.CollisionHandler;
import studio.coldstream.popeglade.gameobjects.Level;
import studio.coldstream.popeglade.gameobjects.Player;


/**
 * Created by Scalar on 01/08/2017.
 */

public class GameWorld {

    private Player player;
    private Level level;
    private CollisionHandler collisionHandler;



    public GameWorld(int midX, int midY) {
        Gdx.app.log("GameWorld", "Attached");

        player = new Player(300, 700, 24, 32);
        level = new Level(1);
        collisionHandler = new CollisionHandler();
    }


    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    public void update(float delta) {
        //Gdx.app.log("GameWorld", "update");

        /*switch (currentState){
            case MENU:
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }*/

        //player.updateX(delta);

        collisionHandler.collision(delta, player, level);

        //player.updateY(delta);

        //collisionHandler.collisionY(player, level);

    }

    public Player getPlayer() { return player; }
    public Level getLevel() { return level; }


}
