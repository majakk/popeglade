package studio.coldstream.popeglade.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gameobjects.entities.Entity;
import studio.coldstream.popeglade.gameobjects.entities.EntityFactory;
import studio.coldstream.popeglade.gameobjects.maps.MapManager;

/**
 * Created by Scalar on 01/08/2017.
 */

public class GameWorld {
    private static final String TAG = GameWorld.class.getSimpleName();

    private static MapManager mapMgr;
    private static Entity player;
    private static Entity pointer;
    private Array<Entity> mapEntities;
    private Array<Entity> mapNPCs;

    public GameWorld() {
        Gdx.app.log(TAG, "Attached");

        //Add world objects, Map, the Player and all entities (loaded)
        mapMgr = new MapManager();
        player = EntityFactory.getInstance().getEntity(EntityFactory.EntityType.PLAYER);
        pointer = EntityFactory.getInstance().getEntity(EntityFactory.EntityType.POINTER);
        mapMgr.setPlayer(player);
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
    public Entity getPointerEntity() { return pointer; }
    public MapManager getMapMgr() { return mapMgr; }


}
