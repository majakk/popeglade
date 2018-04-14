package studio.coldstream.popeglade.gameworld;

import com.badlogic.gdx.Gdx;

import studio.coldstream.popeglade.gamehelpers.CollisionHandler;
import studio.coldstream.popeglade.gamehelpers.LocationHandler;
import studio.coldstream.popeglade.gameobjects.Terrain;
import studio.coldstream.popeglade.gameobjects.Player;
import studio.coldstream.popeglade.gameobjects.Pointer;
import studio.coldstream.popeglade.gameobjects.entities.Entity;
import studio.coldstream.popeglade.gameobjects.entities.EntityFactory;
import studio.coldstream.popeglade.gameobjects.entities.PlayerInputComponent;
import studio.coldstream.popeglade.gameobjects.maps.MapFactory;
import studio.coldstream.popeglade.gameobjects.maps.MapManager;

/**
 * Created by Scalar on 01/08/2017.
 */

public class GameWorld {
    private static final String TAG = GameWorld.class.getSimpleName();

    //private Player player;
    private static Entity player;
    //private static Entity pointer;

    //private Terrain terrain;
    private static MapManager mapMgr;

    private PlayerInputComponent controller;

    private Pointer pointer;
    /*private CollisionHandler collisionHandler;
    private LocationHandler locationHandler;*/

    public GameWorld() {
        Gdx.app.log(TAG, "Attached");

        //Add world objects, Map, the Player and all entities (loaded)
        mapMgr = new MapManager();
        player = EntityFactory.getInstance().getEntity(EntityFactory.EntityType.PLAYER);
        mapMgr.setPlayer(player);



        //player = new Player(300, 700, 24, 32);
        //terrain = new Terrain(1);
        //pointer = new Pointer();
        //collisionHandler = new CollisionHandler();
        //locationHandler = new LocationHandler();

        controller = new PlayerInputComponent();
        Gdx.input.setInputProcessor(controller);
    }

    public void update(float delta) {
        //_player.update(mapMgr, null, delta);

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

        //collisionHandler.update(delta, player, terrain);
        //pointer.update(delta, player, mapMgr);

    }

    /*public Player getPlayer() { return player; }
    public Terrain getTerrain() { return terrain; }*/


    //public Pointer getPointer() { return pointer; }
    public Entity getPlayerEntity() { return player; }
    public MapManager getMapMgr() { return mapMgr; }


}
