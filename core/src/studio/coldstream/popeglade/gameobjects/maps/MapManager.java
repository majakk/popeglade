package studio.coldstream.popeglade.gameobjects.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gameobjects.entities.Entity;

public class MapManager {
    private static final String TAG = MapManager.class.getSimpleName();

    private Camera camera;
    private Entity player;
    private boolean mapChanged = false;
    private Map currentMap;

    public void loadMap(MapFactory.MapType mapType){
        Map map = MapFactory.getMap(mapType);

        if( map == null ){
            Gdx.app.debug(TAG, "Map does not exist!  ");
            return;
        }

        /*if( currentMap != null ){
            //currentMap.unloadMusic();
            if( _previousLightMap != null ){
                _previousLightMap.setOpacity(0);
                _previousLightMap = null;
            }
            if( _currentLightMap != null ){
                _currentLightMap.setOpacity(1);
                _currentLightMap = null;
            }
        }*/

        //map.loadMusic();

        currentMap = map;
        mapChanged = true;
        //clearCurrentSelectedMapEntity();
        Gdx.app.debug(TAG, "Current Map: " + mapType + ", Player Start: (" + currentMap.getPlayerStart().x + "," + currentMap.getPlayerStart().y + ")");
    }

    public MapLayer getCollisionLayer(){
        return currentMap.getCollisionLayer();
    }

    public MapLayer getPortalLayer(){
        return currentMap.getPortalLayer();
    }

    public MapLayer getObjectsLayer(){
        return currentMap.getObjectsLayer();
    }

    public Array<Entity> getCurrentMapEntities() {
        return currentMap.mapEntities;
    }

    /*public MapLayer getEnemySpawnLayer(){
        return currentMap.getEnemySpawnLayer();
    }*/

    public MapFactory.MapType getCurrentMapType(){
        return currentMap.getCurrentMapType();
    }

    public Vector2 getPlayerStartUnitScaled() {
        return currentMap.getPlayerStartUnitScaled();
    }

    public TiledMap getCurrentTiledMap(){
        if( currentMap == null ) {
            loadMap(MapFactory.MapType.FOREST);
        }
        return currentMap.getCurrentTiledMap();
    }

    public Vector2 getMapDimensions() {
        return currentMap.getMapDimensions();
    }

    public Vector2 getMapNumOfTiles() {
        return currentMap.getMapNumOfTiles();
    }

    public Vector2 getMapTileDimension() { return currentMap.getMapTileDimension(); }

    /*public void disableCurrentmapMusic(){
        currentMap.unloadMusic();
    }*/

    /*public void enableCurrentmapMusic(){
        currentMap.loadMusic();
    }*/

    /*public Array<Entity> getCurrentMapEntities(){
        return currentMap.getMapEntities();
    }*/

    public void updateCurrentMapEntities(MapManager mapMgr, Batch batch, float delta){
        currentMap.updateMapEntities(mapMgr, batch, delta);
    }

    public void setPlayer(Entity entity){
        this.player = entity;
    }

    public Entity getPlayer(){
        return this.player;
    }

    public void setCamera(Camera camera){
        this.camera = camera;
    }

    public Camera getCamera(){
        return camera;
    }

    public boolean hasMapChanged(){
        return mapChanged;
    }

    public void setMapChanged(boolean hasMapChanged){
        this.mapChanged = hasMapChanged;
    }
}
