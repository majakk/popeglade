package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.Hashtable;

import studio.coldstream.popeglade.gameobjects.maps.MapManager;
import studio.coldstream.popeglade.profiles.ProfileManager;

public class Entity {
    private static final String TAG = Entity.class.getSimpleName();
    private Json json;
    private EntityConfig entityConfig;

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        LEFT_UP,
        LEFT_DOWN,
        RIGHT_UP,
        RIGHT_DOWN;

        /*public Direction getOpposite(){
            if(this == UP)
                return DOWN;
            else if(this == RIGHT)
                return LEFT;
            else if(this == DOWN)
                return UP;
            else
                return RIGHT;
        }*/
    }

    public enum State {
        IDLE,
        WALKING,

        IMMOBILE //Should always be last
    }

    public enum AnimationType {
        WALK_UP,
        WALK_RIGHT,
        WALK_DOWN,
        WALK_LEFT,
        IDLE,
        IMMOBILE
    }

    public static Vector2 frameDimensions;
    public static Vector2 numOfTilesDimensions;
    //public static final int FRAME_WIDTH = 18;
    //public static final int FRAME_HEIGHT = 18;

    private static final int MAX_COMPONENTS = 5;
    private Array<Component> componentArray;

    private InputComponent inputComponent;
    private PhysicsComponent physicsComponent;
    private GraphicsComponent graphicsComponent;

    public Entity(InputComponent iC, PhysicsComponent pC, GraphicsComponent gC){
        Gdx.app.log(TAG, "Created");

        entityConfig = new EntityConfig();
        json = new Json();

        //frameDimensions = entityConfig.getFrameDimensions();
        componentArray = new Array<>(MAX_COMPONENTS);

        inputComponent = iC;
        physicsComponent = pC;
        graphicsComponent = gC;

        componentArray.add(inputComponent);
        componentArray.add(physicsComponent);
        componentArray.add(graphicsComponent);
    }

    public EntityConfig getEntityConfig() {
        return entityConfig;
    }

    public void sendMessage(Component.MESSAGE messageType, String ...args) {
        String fullMessage = messageType.toString();

        for(String string : args) {
            fullMessage += Component.MESSAGE_TOKEN + string;
        }

        for(Component component: componentArray){
            component.receiveMessage(fullMessage);
        }
    }

    public void update(MapManager mapMgr, Batch batch, float delta){
        inputComponent.update(this, delta);
        physicsComponent.update(this, mapMgr, delta);
        graphicsComponent.update(this, mapMgr, batch, delta);
    }

    public void updateInput(float delta){
        inputComponent.update(this, delta);
    }

    public void dispose(){
        for(Component component: componentArray)
            component.dispose();
    }

    public Array<Rectangle> getCurrentBoundingBoxes(){
        return physicsComponent.boundingBox;
    }

    public String getEntityID(){
        return entityConfig.getEntityID();
    }

    public Vector2 getCurrentPosition(){
        return graphicsComponent.currentPosition;
    }

    public void setCurrentPosition(int x, int y) {
        graphicsComponent.currentPosition.x = x;
        graphicsComponent.currentPosition.y = y;

    }

    public void setEntityConfig(EntityConfig eC){
        this.entityConfig = eC;
    }

    static public EntityConfig getEntityConfig(String configFilePath){
        Json j = new Json();
        return j.fromJson(EntityConfig.class, Gdx.files.internal(configFilePath));
    }

    static public Array<EntityConfig> getEntityConfigs(String configFilePath){
        Json j = new Json();
        Array<EntityConfig> configs = new Array<>();

        ArrayList<JsonValue> list = j.fromJson(ArrayList.class, Gdx.files.internal(configFilePath));

        for(JsonValue jsonVal : list) {
            configs.add(j.readValue(EntityConfig.class, jsonVal));
        }

        return configs;
    }

    public static EntityConfig loadEntityConfigByPath(String entityConfigPath){
        EntityConfig entityConfig = Entity.getEntityConfig(entityConfigPath);
        EntityConfig serializedConfig = ProfileManager.getInstance().getProperty(entityConfig.getEntityID(), EntityConfig.class);

        Gdx.app.log(TAG, "FrameDimensions: " + Integer.valueOf(entityConfig.getFrameWidth()) + ":" + Integer.valueOf(entityConfig.getFrameHeight()));

        frameDimensions = new Vector2(Integer.valueOf(entityConfig.getFrameWidth()),Integer.valueOf(entityConfig.getFrameHeight()));
        numOfTilesDimensions = new Vector2(Integer.valueOf(entityConfig.getNumOfTilesWidth()), Integer.valueOf(entityConfig.getNumOfTilesHeight()));
        /*frameDimensions = new Vector2(
                Integer.valueOf(entityConfig.getFrameWidth()) * Integer.valueOf(entityConfig.getNumOfTilesWidth()),
                Integer.valueOf(entityConfig.getFrameHeight()) * Integer.valueOf(entityConfig.getNumOfTilesHeight())
        );*/

        if( serializedConfig == null ){
            return entityConfig;
        }else{
            return serializedConfig;
        }
    }

    public static Entity initEntity(EntityConfig entityConfig, Vector2 position){
        Json json = new Json();
        Entity entity = EntityFactory.getEntity(EntityFactory.EntityType.NPC);
        entity.setEntityConfig(entityConfig);

        entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));
        entity.sendMessage(Component.MESSAGE.INIT_START_POSITION, json.toJson(position));
        entity.sendMessage(Component.MESSAGE.INIT_STATE, json.toJson(entity.getEntityConfig().getState()));
        entity.sendMessage(Component.MESSAGE.INIT_DIRECTION, json.toJson(entity.getEntityConfig().getDirection()));

        return entity;
    }

    public static Hashtable<String, Entity> initEntities(Array<EntityConfig> configs){
        Json json = new Json();
        Hashtable<String, Entity > entities = new Hashtable<String, Entity>();
        for( EntityConfig config: configs ){
            Entity entity = EntityFactory.getEntity(EntityFactory.EntityType.NPC);

            entity.setEntityConfig(config);
            entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));
            entity.sendMessage(Component.MESSAGE.INIT_START_POSITION, json.toJson(new Vector2(0,0)));
            entity.sendMessage(Component.MESSAGE.INIT_STATE, json.toJson(entity.getEntityConfig().getState()));
            entity.sendMessage(Component.MESSAGE.INIT_DIRECTION, json.toJson(entity.getEntityConfig().getDirection()));

            entities.put(entity.getEntityConfig().getEntityID(), entity);
        }

        return entities;
    }

    public static Entity initEntity(EntityConfig entityConfig){
        Json json = new Json();
        Entity entity = EntityFactory.getEntity(EntityFactory.EntityType.NPC);
        entity.setEntityConfig(entityConfig);

        //entity.sendMessage(Component.MESSAGE.INIT_FRAME_DIMENSIONS, json.toJson(entity.getEntityConfig().getFrameDimensions()));
        //entity.sendMessage(Component.MESSAGE.INIT_NUM_OF_TILES_DIMENSIONS, json.toJson(entity.getEntityConfig().getNumOfTilesDimensions()));

        entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));
        entity.sendMessage(Component.MESSAGE.INIT_START_POSITION, json.toJson(new Vector2(0,0)));
        entity.sendMessage(Component.MESSAGE.INIT_STATE, json.toJson(entity.getEntityConfig().getState()));
        entity.sendMessage(Component.MESSAGE.INIT_DIRECTION, json.toJson(entity.getEntityConfig().getDirection()));
        //entity.sendMessage(Component.MESSAGE.INIT_BOUNDING_BOX, json.toJson(entity.getEntityConfig()));


        return entity;
    }

}
