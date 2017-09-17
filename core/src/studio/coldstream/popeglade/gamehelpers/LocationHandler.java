package studio.coldstream.popeglade.gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gameobjects.Level;
import studio.coldstream.popeglade.gameobjects.Player;

/**
 * Created by Scalar on 17/09/2017.
 */

public class LocationHandler {

    private Rectangle tempRect;
    private Array<Rectangle> tempOctaRect;
    private TiledMapTileLayer.Cell cell;
    private TiledMapTileLayer tileLayer;

    public LocationHandler() {
        tempRect = new Rectangle();
        //for(int i = 0; i < 7; i++)
        //tempOctaRect[i] = new Rectangle();
        tempOctaRect = new Array<Rectangle>();
        for(int i = 0; i < 8; i++)
            tempOctaRect.add(new Rectangle());

        //playerTile = new int[2];
    }

    public TiledMapTileLayer.Cell playerTile(Player player, Level level) {
        tileLayer = (TiledMapTileLayer) level.getTiledMap().getLayers().get(0);
        cell = tileLayer.getCell((int)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()),(int)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()));


        //Gdx.app.log("LocationHandler", cell.getTile().getId() + "");
        return cell;
    }

    public boolean isTileWall(TiledMapTileLayer.Cell cell){
        return cell.getTile().getProperties().get("wall").equals("true");
    }

    public Rectangle playerTileRect(Player player, Level level) {
        tempRect.set(
                (float)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()) * level.getSingleTileWidth(),
                (float)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()) * level.getSingleTileHeight(),
                level.getSingleTileWidth(),
                level.getSingleTileHeight());
        return tempRect;
    }

    public Array<Rectangle> playerOctaTileRect(Player player, Level level) {
        //sw
        tempOctaRect.set(0, new Rectangle(
                ((float)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()) - 1) * level.getSingleTileWidth(),
                ((float)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()) - 1) * level.getSingleTileHeight(),
                level.getSingleTileWidth(),
                level.getSingleTileHeight()));
        //s
        tempOctaRect.set(1, new Rectangle(
                ((float)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()) - 0) * level.getSingleTileWidth(),
                ((float)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()) - 1) * level.getSingleTileHeight(),
                level.getSingleTileWidth(),
                level.getSingleTileHeight()));
        //se
        tempOctaRect.set(2, new Rectangle(
                ((float)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()) + 1) * level.getSingleTileWidth(),
                ((float)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()) - 1) * level.getSingleTileHeight(),
                level.getSingleTileWidth(),
                level.getSingleTileHeight()));
        //w
        tempOctaRect.set(3, new Rectangle(
                ((float)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()) - 1) * level.getSingleTileWidth(),
                ((float)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()) - 0) * level.getSingleTileHeight(),
                level.getSingleTileWidth(),
                level.getSingleTileHeight()));
        //e
        tempOctaRect.set(4, new Rectangle(
                ((float)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()) + 1) * level.getSingleTileWidth(),
                ((float)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()) - 0) * level.getSingleTileHeight(),
                level.getSingleTileWidth(),
                level.getSingleTileHeight()));
        //nw
        tempOctaRect.set(5, new Rectangle(
                ((float)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()) - 1) * level.getSingleTileWidth(),
                ((float)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()) + 1) * level.getSingleTileHeight(),
                level.getSingleTileWidth(),
                level.getSingleTileHeight()));
        //n
        tempOctaRect.set(6, new Rectangle(
                ((float)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()) - 0) * level.getSingleTileWidth(),
                ((float)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()) + 1) * level.getSingleTileHeight(),
                level.getSingleTileWidth(),
                level.getSingleTileHeight()));
        //ne
        tempOctaRect.set(7, new Rectangle(
                ((float)Math.floor((double)player.getX()/(double)level.getSingleTileWidth()) + 1) * level.getSingleTileWidth(),
                ((float)Math.floor((double)player.getY()/(double)level.getSingleTileHeight()) + 1) * level.getSingleTileHeight(),
                level.getSingleTileWidth(),
                level.getSingleTileHeight()));

        return tempOctaRect;
    }

}
