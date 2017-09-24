package studio.coldstream.popeglade.gameworld;

import com.badlogic.gdx.Gdx;

import studio.coldstream.popeglade.gamehelpers.CollisionHandler;
import studio.coldstream.popeglade.gamehelpers.LocationHandler;
import studio.coldstream.popeglade.gameobjects.Terrain;
import studio.coldstream.popeglade.gameobjects.Player;
import studio.coldstream.popeglade.gameobjects.Pointer;


/**
 * Created by Scalar on 01/08/2017.
 */

public class GameWorld {

    private Player player;
    private Terrain terrain;
    private Pointer pointer;
    private CollisionHandler collisionHandler;
    private LocationHandler locationHandler;

    public GameWorld(int midX, int midY) {
        Gdx.app.log("GameWorld", "Attached");

        player = new Player(300, 700, 24, 32);
        terrain = new Terrain(1);
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

        collisionHandler.update(delta, player, terrain);
        pointer.update(delta, player, terrain);

    }

    public Player getPlayer() { return player; }
    public Terrain getTerrain() { return terrain; }
    public Pointer getPointer() { return pointer; }


}
