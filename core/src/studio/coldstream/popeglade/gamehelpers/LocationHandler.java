package studio.coldstream.popeglade.gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gameobjects.Terrain;
import studio.coldstream.popeglade.gameobjects.Player;
import studio.coldstream.popeglade.gameobjects.entities.Entity;
import studio.coldstream.popeglade.gameobjects.maps.Map;
import studio.coldstream.popeglade.gameobjects.maps.MapManager;


/**
 * Created by Scalar on 17/09/2017.
 */

public class LocationHandler {
    private static final String TAG = LocationHandler.class.getSimpleName();

    private Rectangle tempRect;
    private Array<Rectangle> tempNineRect;
    private Array<Cell> tempNineCell;
    private Cell cell;
    private TiledMapTileLayer tileLayer;

    private Array<Vector2> nd;

    public LocationHandler() {
        tempRect = new Rectangle();

        nd = new Array<Vector2>();

        nd.add(new Vector2(-1,-1));
        nd.add(new Vector2(0 ,-1));
        nd.add(new Vector2(1 ,-1));

        nd.add(new Vector2(-1, 0));
        nd.add(new Vector2(0 , 0));
        nd.add(new Vector2(1 , 0));

        nd.add(new Vector2(-1, 1));
        nd.add(new Vector2(0 , 1));
        nd.add(new Vector2(1 , 1));


        //for(int i = 0; i < 7; i++)
        //tempOctaRect[i] = new Rectangle();
        tempNineRect = new Array<Rectangle>();
        for(int i = 0; i < 9; i++)
            tempNineRect.add(new Rectangle());

        tempNineCell = new Array<Cell>();
        for(int i = 0; i < 9; i++)
            tempNineCell.add(new Cell());

        //playerTile = new int[2];
    }

    /*public Cell playerTile(Player player, Terrain terrain) {
        tileLayer = (TiledMapTileLayer) terrain.getTiledMap().getLayers().get(1);
        cell = tileLayer.getCell((int)Math.floor((double)player.getX()/(double) terrain.getSingleTileWidth()),(int)Math.floor((double)player.getY()/(double) terrain.getSingleTileHeight()));


        //Gdx.app.log("LocationHandler", cell.getTile().getId() + "");
        return cell;
    }*/

    /*public Cell pointerTile(Pointer pointer, Terrain terrain) {
        tileLayer = (TiledMapTileLayer) terrain.getTiledMap().getLayers().get(2);
        cell = tileLayer.getCell((int)Math.floor((double)pointer.getX()/(double) terrain.getSingleTileWidth()),(int)Math.floor((double)pointer.getY()/(double) terrain.getSingleTileHeight()));
        //cell.getTile().setBlendMode(TiledMapTile.BlendMode.ALPHA);

        //Gdx.app.log("LocationHandler", cell.getTile().getId() + "");
        if(cell != null)
            return cell;
        else
            return new Cell();
    }*/

    /*public boolean isTileWall(Cell cell){
        /*if(cell.getTile().getProperties().get("wall") != null)
            return true;
        else
            return false;
        //try {
            return cell.getTile().getProperties().get("wall") != null;
        //} catch(Exception e){
        //    return false;
        //}
    }*/

    /*public Rectangle playerTileRect(Player player, Terrain terrain) {
        tempRect.set(
                (float)Math.floor((double)player.getX()/(double) terrain.getSingleTileWidth()) * terrain.getSingleTileWidth(),
                (float)Math.floor((double)player.getY()/(double) terrain.getSingleTileHeight()) * terrain.getSingleTileHeight(),
                terrain.getSingleTileWidth(),
                terrain.getSingleTileHeight());
        return tempRect;
    }*/

    /*public Rectangle pointerTileRect(Pointer pointer, Terrain terrain) {
        tempRect.set(
                (float)Math.floor((double)pointer.getX()/(double) terrain.getSingleTileWidth()) * terrain.getSingleTileWidth(),
                (float)Math.floor((double)pointer.getY()/(double) terrain.getSingleTileHeight()) * terrain.getSingleTileHeight(),
                terrain.getSingleTileWidth(),
                terrain.getSingleTileHeight());
        return tempRect;
    }*/

    public Rectangle pointerTileRect(Entity entity, MapManager mapMgr, Batch batch) {
        //float numOfTilesX = mapMgr.getMapNumOfTiles().x;
        //float numOfTilesY = mapMgr.getMapNumOfTiles().y;

        float tileSizeX = mapMgr.getMapTileDimension().x;
        float tileSizeY = mapMgr.getMapTileDimension().y;

        float camPosX = -batch.getProjectionMatrix().getTranslation(new Vector3(0,0,0)).x - 1;
        float camPosY = -batch.getProjectionMatrix().getTranslation(new Vector3(0,0,0)).y - 1;

        float mouseX = entity.getCurrentPosition().x;
        float mouseY = entity.getCurrentPosition().y;

        float camWidth = mapMgr.getCamera().viewportWidth;
        float camHeight = mapMgr.getCamera().viewportHeight;

        float physicalWidth = Gdx.app.getGraphics().getWidth();
        float physicalHeight = Gdx.app.getGraphics().getHeight();

        float posX = ((mouseX / physicalWidth) * camWidth) + (camPosX * camWidth / 2);
        float posY = (((physicalHeight - mouseY) / physicalHeight) * camHeight) + (camPosY * camHeight / 2);

        //Gdx.app.log(TAG,"Dimensions: " + mapMgr.getCamera().viewportWidth + ":" + -mapMgr.getCamera().view.getTranslation(new Vector3(1,1,1)).x + ":" + mapMgr.getMapTileDimension().x);
        //Gdx.app.log(TAG,"Dimensions: " + -mapMgr.getCamera().view.getTranslation(new Vector3(1,1,1)).x ); //This is where the player is in the world!!

        //Gdx.app.log(TAG,"Dimensions: " + mapMgr.getCamera().position.x ); //This is also where the player is in the world!!
        //Gdx.app.log(TAG,"Dimensions: " + -batch.getProjectionMatrix().getTranslation(new Vector3(0,0,0)).x + ":" + -batch.getProjectionMatrix().getTranslation(new Vector3(0,0,0)).y); //This is camera position
        //Gdx.app.log(TAG,"Dimensions: " + -batch.getProjectionMatrix().getTranslation(new Vector3(0,0,0)).x + ":" + -batch.getProjectionMatrix().getTranslation(new Vector3(0,0,0)).y);

        tempRect.set(
                (float)Math.floor((double)posX),
                (float)Math.floor((double)posY),
                //10,10,
                //((mouseX / Gdx.app.getGraphics().getWidth()) * camWidth) + (camPosX * camWidth / 2), //Working X
                //(((Gdx.app.getGraphics().getHeight() - mouseY) / Gdx.app.getGraphics().getHeight()) * camHeight) + (camPosY * camHeight / 2), //Working Y

                tileSizeX * Map.UNIT_SCALE,
                tileSizeY * Map.UNIT_SCALE);
        return tempRect;
    }

    /*public Array<Rectangle> playerNineTileRect(Player player, Terrain terrain) {
        for(int i = 0; i < 9; i++) {
            tempNineRect.set(i, new Rectangle(
                    ((float)Math.floor((double)player.getX()/(double) terrain.getSingleTileWidth()) + nd.get(i).x) * terrain.getSingleTileWidth(),
                    ((float)Math.floor((double)player.getY()/(double) terrain.getSingleTileHeight()) + nd.get(i).y) * terrain.getSingleTileHeight(),
                    terrain.getSingleTileWidth(),
                    terrain.getSingleTileHeight()));
        }

        return tempNineRect;
    }*/

    /*public Array<Cell> playerNineTile(Player player, Terrain terrain) {
        //tempNineCell.set(0, new Cell());
        tileLayer = (TiledMapTileLayer) terrain.getTiledMap().getLayers().get(1);
        for(int i = 0; i < 9; i++) {
            if(tileLayer.getCell(
                    (int) Math.floor((double) player.getX() / (double) terrain.getSingleTileWidth()) + (int) nd.get(i).x,
                    (int) Math.floor((double) player.getY() / (double) terrain.getSingleTileHeight()) + (int) nd.get(i).y
            ) != null) {
                tempNineCell.set(i,
                        tileLayer.getCell(
                                (int) Math.floor((double) player.getX() / (double) terrain.getSingleTileWidth()) + (int) nd.get(i).x,
                                (int) Math.floor((double) player.getY() / (double) terrain.getSingleTileHeight()) + (int) nd.get(i).y
                        )
                );
            }
        }

        return tempNineCell;
    }*/

}
