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


/**
 * Created by Scalar on 01/08/2017.
 */

public class GameWorld {

    private Player player;
    private Level level;
    private CollisionHandler collisionHandler;
    private LocationHandler locationHandler;



    public GameWorld(int midX, int midY) {
        Gdx.app.log("GameWorld", "Attached");

        player = new Player(300, 700, 24, 32);
        level = new Level(1);
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

        //player.updateX(delta);

        collisionHandler.collision(delta, player, level);
        //Gdx.app.log("GameWorld", level.getTiledMap().getLayers().get(0).getName());
        //Gdx.app.log("GameWorld", locationHandler.playerTile(player,level).getTile().getProperties().get("wall") + "");

        //aTile.getId();
        //player.updateY(delta);

        //collisionHandler.collisionY(player, level);


        /*TiledMapTileLayer tileLayer;

        //define the layer where you select the tile
        tileLayer = (TiledMapTileLayer) level.getTiledMap().getLayers().get(0);

        //get the tile that you want
        TiledMapTileLayer.Cell cell = tileLayer.getCell(x,y);
        TiledMapTile tile = cell.getTile();

        //this is where you get the properties of the tile
        tile.getProperties().get("propertiename");*/

    }

    public Player getPlayer() { return player; }
    public Level getLevel() { return level; }


}
