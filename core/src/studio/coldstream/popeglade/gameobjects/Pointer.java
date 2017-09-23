package studio.coldstream.popeglade.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import studio.coldstream.popeglade.gamehelpers.LocationHandler;

/**
 * Created by Scalar on 23/09/2017.
 */

public class Pointer {

    private Vector2 position;
    private boolean leftClicked;
    private LocationHandler lh;

    public Pointer() {
        position = new Vector2(0, 0);
        leftClicked = false;
        lh = new LocationHandler();
    }

    public void update(float delta, Player player, Level level) {
        //Gdx.app.log("Pointer","Click!");

        if(leftClicked){
            if(lh.pointerTile(this, level).getTile().getId() == 1)
                lh.pointerTile(this, level).setTile(level.getTiledMap().getTileSets().getTile(30));
            leftClicked = false;
        }
    }

    public void setPosition(Vector2 v) {
        position = v;
    }

    public void leftClick() {
        //Gdx.app.log("Pointer","Click!");
        leftClicked = true;

    }

    public Vector2 getPosition() {
        return position;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }
}
