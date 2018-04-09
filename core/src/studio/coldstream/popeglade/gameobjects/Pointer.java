package studio.coldstream.popeglade.gameobjects;

import com.badlogic.gdx.math.Vector2;

import studio.coldstream.popeglade.gamehelpers.LocationHandler;
import studio.coldstream.popeglade.gameobjects.entities.Entity;
import studio.coldstream.popeglade.gameobjects.maps.MapManager;

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

    /*public void update(float delta, Player player, MapManager mapMgr) {
        //Gdx.app.log("Pointer","Click!");

        if(leftClicked){
            if(lh.pointerTile(this, terrain).getTile().getId() == 600) {
                lh.pointerTile(this, terrain).setTile(terrain.getTiledMap().getTileSets().getTile(119));
                if(player.getInventory().getPocket().size() < player.getInventory().getMaxItemSlots())
                    player.pickUpCollectable(new Collectable(0));
            }

            if(lh.pointerTile(this, terrain).getTile().getId() == 924) {
                lh.pointerTile(this, terrain).setTile(terrain.getTiledMap().getTileSets().getTile(119));
                if(player.getInventory().getPocket().size() < player.getInventory().getMaxItemSlots())
                    player.pickUpCollectable(new Collectable(1));
            }

            if(lh.pointerTile(this, terrain).getTile().getId() == 497) {
                lh.pointerTile(this, terrain).setTile(terrain.getTiledMap().getTileSets().getTile(119));
                if(player.getInventory().getPocket().size() < player.getInventory().getMaxItemSlots())
                    player.pickUpCollectable(new Collectable(2));
            }

            leftClicked = false;
        }
    }*/

    public void update(float delta, Entity player, MapManager mapMgr) {
        //Gdx.app.log("Pointer","Click!");

        /*if(leftClicked){
            if(lh.pointerTile(this, terrain).getTile().getId() == 600) {
                lh.pointerTile(this, terrain).setTile(terrain.getTiledMap().getTileSets().getTile(119));
                if(player.getInventory().getPocket().size() < player.getInventory().getMaxItemSlots())
                    player.pickUpCollectable(new Collectable(0));
            }

            if(lh.pointerTile(this, terrain).getTile().getId() == 924) {
                lh.pointerTile(this, terrain).setTile(terrain.getTiledMap().getTileSets().getTile(119));
                if(player.getInventory().getPocket().size() < player.getInventory().getMaxItemSlots())
                    player.pickUpCollectable(new Collectable(1));
            }

            if(lh.pointerTile(this, terrain).getTile().getId() == 497) {
                lh.pointerTile(this, terrain).setTile(terrain.getTiledMap().getTileSets().getTile(119));
                if(player.getInventory().getPocket().size() < player.getInventory().getMaxItemSlots())
                    player.pickUpCollectable(new Collectable(2));
            }

            leftClicked = false;
        }*/
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
