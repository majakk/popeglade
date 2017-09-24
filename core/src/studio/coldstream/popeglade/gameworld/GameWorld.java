package studio.coldstream.popeglade.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import studio.coldstream.popeglade.gamehelpers.CollisionHandler;
import studio.coldstream.popeglade.gamehelpers.LocationHandler;
import studio.coldstream.popeglade.gameobjects.Level;
import studio.coldstream.popeglade.gameobjects.Player;
import studio.coldstream.popeglade.gameobjects.Pointer;


/**
 * Created by Scalar on 01/08/2017.
 */

public class GameWorld {

    private Player player;
    private Level level;
    private Pointer pointer;
    private CollisionHandler collisionHandler;
    private LocationHandler locationHandler;

    public GameWorld(int midX, int midY) {
        Gdx.app.log("GameWorld", "Attached");

        player = new Player(300, 700, 24, 32);
        level = new Level(1);
        pointer = new Pointer();
        collisionHandler = new CollisionHandler();
        locationHandler = new LocationHandler();
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

        collisionHandler.update(delta, player, level);
        pointer.update(delta, player, level);

    }

    public Player getPlayer() { return player; }
    public Level getLevel() { return level; }
    public Pointer getPointer() { return pointer; }


}
