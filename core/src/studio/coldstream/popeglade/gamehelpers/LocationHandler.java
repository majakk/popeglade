package studio.coldstream.popeglade.gamehelpers;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gameobjects.Terrain;
import studio.coldstream.popeglade.gameobjects.Player;
import studio.coldstream.popeglade.gameobjects.Pointer;

/**
 * Created by Scalar on 17/09/2017.
 */

public class LocationHandler {

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

    public Cell playerTile(Player player, Terrain terrain) {
        tileLayer = (TiledMapTileLayer) terrain.getTiledMap().getLayers().get(1);
        cell = tileLayer.getCell((int)Math.floor((double)player.getX()/(double) terrain.getSingleTileWidth()),(int)Math.floor((double)player.getY()/(double) terrain.getSingleTileHeight()));


        //Gdx.app.log("LocationHandler", cell.getTile().getId() + "");
        return cell;
    }

    public Cell pointerTile(Pointer pointer, Terrain terrain) {
        tileLayer = (TiledMapTileLayer) terrain.getTiledMap().getLayers().get(2);
        cell = tileLayer.getCell((int)Math.floor((double)pointer.getX()/(double) terrain.getSingleTileWidth()),(int)Math.floor((double)pointer.getY()/(double) terrain.getSingleTileHeight()));
        //cell.getTile().setBlendMode(TiledMapTile.BlendMode.ALPHA);

        //Gdx.app.log("LocationHandler", cell.getTile().getId() + "");
        if(cell != null)
            return cell;
        else
            return new Cell();
    }

    public boolean isTileWall(Cell cell){
        /*if(cell.getTile().getProperties().get("wall") != null)
            return true;
        else
            return false;*/
        //try {
            return cell.getTile().getProperties().get("wall") != null;
        //} catch(Exception e){
        //    return false;
        //}
    }

    public Rectangle playerTileRect(Player player, Terrain terrain) {
        tempRect.set(
                (float)Math.floor((double)player.getX()/(double) terrain.getSingleTileWidth()) * terrain.getSingleTileWidth(),
                (float)Math.floor((double)player.getY()/(double) terrain.getSingleTileHeight()) * terrain.getSingleTileHeight(),
                terrain.getSingleTileWidth(),
                terrain.getSingleTileHeight());
        return tempRect;
    }

    public Rectangle pointerTileRect(Pointer pointer, Terrain terrain) {
        tempRect.set(
                (float)Math.floor((double)pointer.getX()/(double) terrain.getSingleTileWidth()) * terrain.getSingleTileWidth(),
                (float)Math.floor((double)pointer.getY()/(double) terrain.getSingleTileHeight()) * terrain.getSingleTileHeight(),
                terrain.getSingleTileWidth(),
                terrain.getSingleTileHeight());
        return tempRect;
    }

    public Array<Rectangle> playerNineTileRect(Player player, Terrain terrain) {
        for(int i = 0; i < 9; i++) {
            tempNineRect.set(i, new Rectangle(
                    ((float)Math.floor((double)player.getX()/(double) terrain.getSingleTileWidth()) + nd.get(i).x) * terrain.getSingleTileWidth(),
                    ((float)Math.floor((double)player.getY()/(double) terrain.getSingleTileHeight()) + nd.get(i).y) * terrain.getSingleTileHeight(),
                    terrain.getSingleTileWidth(),
                    terrain.getSingleTileHeight()));
        }

        return tempNineRect;
    }

    public Array<Cell> playerNineTile(Player player, Terrain terrain) {
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
    }

}
