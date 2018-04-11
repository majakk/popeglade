package studio.coldstream.popeglade.gameobjects.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;

public abstract class Map {
    private static final String TAG = Map.class.getSimpleName();

    public final static float UNIT_SCALE = 1/16f;

    //Map layers
    protected final static String COLLISION_LAYER = "MAP_COLLISION_LAYER";
    protected final static String SPAWNS_LAYER = "MAP_SPAWNS_LAYER";
    protected final static String PORTAL_LAYER = "MAP_PORTAL_LAYER";

    public final static String BACKGROUND_LAYER = "Walls";
    public final static String GROUND_LAYER = "Ground";
    public final static String DECORATION_LAYER = "Collectables";
    public final static String TOP_LAYER = "Top";

    //Starting locations
    protected final static String PLAYER_START = "PLAYER_START";

    protected Json json;
    protected Vector2 playerStart;

    protected TiledMap currentMap = null;
    private MapProperties tiledMapProp;
    protected MapFactory.MapType currentMapType;

    protected Vector2 mapDimensions;
    protected Vector2 numOfTiles;
    protected Vector2 tileDimension;


    Map( MapFactory.MapType mapType, String fullMapPath){
        playerStart = new Vector2(16.0f,16.0f);


        if( fullMapPath == null || fullMapPath.isEmpty() ) {
            Gdx.app.debug(TAG, "Map is invalid");
            return;
        }

        AssetLoader.loadMapAsset(fullMapPath);
        if( AssetLoader.isAssetLoaded(fullMapPath) ) {
            currentMap = AssetLoader.getMapAsset(fullMapPath);
            tiledMapProp = currentMap.getProperties();

            numOfTiles = new Vector2(tiledMapProp.get("width", Integer.class), tiledMapProp.get("height", Integer.class));
            tileDimension = new Vector2(tiledMapProp.get("tilewidth", Integer.class), tiledMapProp.get("tileheight", Integer.class));
            mapDimensions = new Vector2(tileDimension.x * numOfTiles.x, tileDimension.y * numOfTiles.y);
        }else{
            Gdx.app.debug(TAG, "Map not loaded");
        }
    }

    public TiledMap getCurrentTiledMap() {
        return currentMap;
    }

    public MapFactory.MapType getCurrentMapType(){
        return currentMapType;
    }

    public Vector2 getPlayerStart() {
        return playerStart;
    }

    public Vector2 getPlayerStartUnitScaled(){
        Vector2 pS = playerStart.cpy();
        pS.set(playerStart.x * UNIT_SCALE, playerStart.y * UNIT_SCALE);
        return pS;
    }

    public Vector2 getMapDimensions() {
        return mapDimensions;
    }
    public Vector2 getMapNumOfTiles() {
        return numOfTiles;
    }

    protected void dispose(){
        currentMap.dispose();
    }

}
