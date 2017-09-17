package studio.coldstream.popeglade.gameobjects;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Scalar on 16/09/2017.
 */

public class Level {

    private TiledMap tiledMap;
    private MapProperties tiledMapProp;
    private TiledMapRenderer tiledMapRenderer;

    private int singleTileHeight;
    private int singleTileWidth;
    private int numOfTilesX;
    private int numOfTilesY;
    private int width;
    private int height;

    private int currentLevel;
    private Rectangle rect1 = new Rectangle(100,200,32,32);
    private Rectangle rect2 = new Rectangle(132,200,32,32);

    public Level(int clvl) {
        currentLevel = clvl;
        switch(currentLevel){
            case 1:
                tiledMap = new TmxMapLoader().load("android/assets/tmx/level1.tmx");
            default:
        }

        tiledMapProp = tiledMap.getProperties();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        singleTileHeight = tiledMapProp.get("tilewidth", Integer.class);
        singleTileWidth = tiledMapProp.get("tileheight", Integer.class);
        numOfTilesX = tiledMapProp.get("width", Integer.class);
        numOfTilesY = tiledMapProp.get("height", Integer.class);
        width = singleTileWidth * numOfTilesX;
        height = singleTileHeight * numOfTilesY;


    }

    public Rectangle getRect1() { return rect1; }
    public Rectangle getRect2() { return rect2; }
    public TiledMap getTiledMap() { return tiledMap; }
    public TiledMapRenderer getTiledMapRenderer() { return tiledMapRenderer; }
    public MapProperties getTiledMapProp() { return tiledMapProp; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public int getSingleTileWidth() {
        return singleTileWidth;
    }

    public int getSingleTileHeight() {
        return singleTileHeight;
    }


}
