package studio.coldstream.popeglade.gameobjects.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;

public abstract class Map {
    private static final String TAG = Map.class.getSimpleName();

    public final static float UNIT_SCALE = 1/16f;
    //public final static float TILE_SIZE = 16.0f;

    //Map layers
    public final static String COLLISION_LAYER = "MAP_COLLISION_LAYER";
    protected final static String SPAWNS_LAYER = "MAP_SPAWNS_LAYER";
    protected final static String PORTAL_LAYER = "MAP_PORTAL_LAYER";

    public final static String BACKGROUND_LAYER = "Background_Layer";
    public final static String GROUND_LAYER = "Ground_Layer";
    public final static String DECORATION_LAYER = "Decoration_Layer";
    public final static String WALLS_LAYER = "Walls_Layer";
    public final static String TOP_LAYER = "Top_Layer";

    //Starting locations
    protected final static String PLAYER_START = "PLAYER_START";

    protected Json json;

    protected Vector2 playerStart;
    protected Vector2 playerStartPositionRect;
    protected Vector2 closestPlayerStartPosition;

    protected TiledMap currentMap = null;
    protected MapProperties tiledMapProp;
    protected MapFactory.MapType currentMapType;

    protected Vector2 mapDimensions;
    protected Vector2 numOfTiles;
    protected Vector2 tileDimension;

    protected MapLayer collisionLayer = null;
    protected MapLayer portalLayer = null;
    protected MapLayer spawnsLayer = null;


    Map(MapFactory.MapType mapType, String fullMapPath){
        playerStart = new Vector2(0.0f,0.0f);
        playerStartPositionRect = new Vector2(0,0);
        closestPlayerStartPosition = new Vector2(0,0);

        currentMapType = mapType;

        if(fullMapPath == null || fullMapPath.isEmpty() ) {
            Gdx.app.debug(TAG, "Map is invalid");
            return;
        }

        AssetLoader.loadMapAsset(fullMapPath);
        if(AssetLoader.isAssetLoaded(fullMapPath)) {
            currentMap = AssetLoader.getMapAsset(fullMapPath);
            tiledMapProp = currentMap.getProperties();

            numOfTiles = new Vector2(tiledMapProp.get("width", Integer.class), tiledMapProp.get("height", Integer.class));
            tileDimension = new Vector2(tiledMapProp.get("tilewidth", Integer.class), tiledMapProp.get("tileheight", Integer.class));
            mapDimensions = new Vector2(tileDimension.x * numOfTiles.x, tileDimension.y * numOfTiles.y);
        }else{
            Gdx.app.debug(TAG, "Map not loaded");
        }

        collisionLayer = currentMap.getLayers().get(COLLISION_LAYER);
        if( collisionLayer == null ){
            Gdx.app.debug(TAG, "No collision layer!");
        }

        portalLayer = currentMap.getLayers().get(PORTAL_LAYER);
        if( portalLayer == null ){
            Gdx.app.debug(TAG, "No portal layer!");
        }

        spawnsLayer = currentMap.getLayers().get(SPAWNS_LAYER);
        if( spawnsLayer == null ){
            Gdx.app.debug(TAG, "No spawn layer!");
        }else{
            setClosestStartPosition(playerStart);
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

    public void setPlayerStart(Vector2 playerStart) {
        this.playerStart = playerStart;
    }

    public Vector2 getPlayerStartUnitScaled(){
        Vector2 pS = playerStart.cpy();
        pS.set(playerStart.x * UNIT_SCALE, playerStart.y * UNIT_SCALE);
        return pS;
    }

    private void setClosestStartPosition(final Vector2 position){
        Gdx.app.debug(TAG, "setClosestStartPosition INPUT: (" + position.x + "," + position.y + ") " );

        //Get last known position on this map
        playerStartPositionRect.set(0,0);
        closestPlayerStartPosition.set(0,0);
        float shortestDistance = 0f;

        //Go through all player start positions and choose closest to last known position
        for( MapObject object: spawnsLayer.getObjects()){
            String objectName = object.getName();

            if( objectName == null || objectName.isEmpty() ){
                continue;
            }

            if( objectName.equalsIgnoreCase(PLAYER_START) ){
                ((RectangleMapObject)object).getRectangle().getPosition(playerStartPositionRect);
                float distance = position.dst2(playerStartPositionRect);

                Gdx.app.debug(TAG, "DISTANCE: " + distance + " for " + currentMapType.toString());

                if( distance < shortestDistance || shortestDistance == 0 ){
                    closestPlayerStartPosition.set(playerStartPositionRect);
                    shortestDistance = distance;
                    Gdx.app.debug(TAG, "closest START is: (" + closestPlayerStartPosition.x + "," + closestPlayerStartPosition.y + ") " +  currentMapType.toString());
                }
            }
        }
        playerStart =  closestPlayerStartPosition.cpy();
    }

    public MapLayer getCollisionLayer(){
        return collisionLayer;
    }

    public MapLayer getPortalLayer(){
        return portalLayer;
    }

    public Vector2 getMapDimensions() {
        return mapDimensions;
    }

    public Vector2 getMapNumOfTiles() {
        return numOfTiles;
    }

    public Vector2 getMapTileDimension() {
        return tileDimension;
    }

    protected void dispose(){
        currentMap.dispose();
    }



}
