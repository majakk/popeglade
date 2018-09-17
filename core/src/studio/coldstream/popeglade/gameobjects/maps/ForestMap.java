package studio.coldstream.popeglade.gameobjects.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import studio.coldstream.popeglade.gameobjects.entities.Component;
import studio.coldstream.popeglade.gameobjects.entities.Entity;
import studio.coldstream.popeglade.gameobjects.entities.EntityConfig;
import studio.coldstream.popeglade.gameobjects.entities.EntityFactory;
import studio.coldstream.popeglade.profiles.ProfileManager;

public class ForestMap extends Map {
    private static final String TAG = ForestMap.class.getSimpleName();

    private static String mapPath = "android/assets/tmx/new_map.tmx";
    private Json json;

    ForestMap() {
        super(MapFactory.MapType.FOREST, mapPath);

        json = new Json();

        for( Vector2 position: mapObjectsStartPositions){
            Entity entity = EntityFactory.getInstance().getEntityByName(EntityFactory.EntityName.SMALL_TREE);
            entity.sendMessage(Component.MESSAGE.INIT_START_POSITION, json.toJson(position));
            Gdx.app.debug(TAG, "Tree Positions: " + position );
            mapEntities.add(entity);
            //Gdx.app.debug(TAG, "Tree Data: " + entity.getEntityConfig().getEntityID() );
        }

        /*Entity smallTree = EntityFactory.getInstance().getEntityByName(EntityFactory.EntityName.TOWN_BLACKSMITH);
        initMapObjectEntity(smallTree);
        mapEntities.add(smallTree);*/

    }

    /*private void initMapObjectEntity(Entity entity){
        Vector2 position = new Vector2(0,0);

        if( mapObjectsStartPositions.containsKey(entity.getEntityConfig().getEntityID()) ) {
            position = mapObjectsStartPositions.get(entity.getEntityConfig().getEntityID());
        }
        entity.sendMessage(Component.MESSAGE.INIT_START_POSITION, json.toJson(position));

        //Overwrite default if special config is found
        EntityConfig entityConfig = ProfileManager.getInstance().getProperty(entity.getEntityConfig().getEntityID(), EntityConfig.class);
        if( entityConfig != null ){
            entity.setEntityConfig(entityConfig);
        }
    }*/
}