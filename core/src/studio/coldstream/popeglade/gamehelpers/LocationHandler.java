package studio.coldstream.popeglade.gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gameobjects.Level;
import studio.coldstream.popeglade.gameobjects.Player;

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

    public Cell playerTile(Player player, Level level) {
        tileLayer = (TiledMapTileLayer) level.getTiledMap().getLayers().get(0);
        cell = tileLayer.getCell((int)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()),(int)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()));


        //Gdx.app.log("LocationHandler", cell.getTile().getId() + "");
        return cell;
    }

    public boolean isTileWall(Cell cell){
        if(cell.getTile().getProperties().get("wall") != null)
            return true;
        else
            return false;
    }

    public Rectangle playerTileRect(Player player, Level level) {
        tempRect.set(
                (float)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()) * level.getSingleTileWidth(),
                (float)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()) * level.getSingleTileHeight(),
                level.getSingleTileWidth(),
                level.getSingleTileHeight());
        return tempRect;
    }

    public Array<Rectangle> playerNineTileRect(Player player, Level level) {
        for(int i = 0; i < 9; i++) {
            tempNineRect.set(i, new Rectangle(
                    ((float)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()) + nd.get(i).x) * level.getSingleTileWidth(),
                    ((float)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()) + nd.get(i).y) * level.getSingleTileHeight(),
                    level.getSingleTileWidth(),
                    level.getSingleTileHeight()));
        }

        return tempNineRect;
    }

    public Array<Cell> playerNineTile(Player player, Level level) {
        //tempNineCell.set(0, new Cell());
        tileLayer = (TiledMapTileLayer) level.getTiledMap().getLayers().get(0);
        for(int i = 0; i < 9; i++) {
            if(tileLayer.getCell(
                    (int) Math.floor((double) player.getX() / (double) level.getSingleTileWidth()) + (int) nd.get(i).x,
                    (int) Math.floor((double) player.getY() / (double) level.getSingleTileHeight()) + (int) nd.get(i).y
            ) != null) {
                tempNineCell.set(i,
                        tileLayer.getCell(
                                (int) Math.floor((double) player.getX() / (double) level.getSingleTileWidth()) + (int) nd.get(i).x,
                                (int) Math.floor((double) player.getY() / (double) level.getSingleTileHeight()) + (int) nd.get(i).y
                        )
                );
            }
        }

        return tempNineCell;
    }

}
