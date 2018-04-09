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

import studio.coldstream.popeglade.gameobjects.maps.MapManager;
import studio.coldstream.popeglade.profiles.ProfileManager;

public class Entity {
    private static final String TAG = Entity.class.getSimpleName();
    private Json json;
    private EntityConfig entityConfig;

    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT;

        public Direction getOpposite(){
            if(this == UP)
                return DOWN;
            else if(this == RIGHT)
                return LEFT;
            else if(this == DOWN)
                return UP;
            else
                return RIGHT;
        }
    }

    public enum State {
        IDLE,
        WALKING,

        IMMOBILE; //Should always be last
    }

    public enum AnimationType {
        WALK_UP,
        WALK_RIGHT,
        WALK_DOWN,
        WALK_LEFT,
        IDLE,
        IMMOBILE;
    }

    public static final int FRAME_WIDTH = 16;
    public static final int FRAME_HEIGHT = 16;
    private static final int MAX_COMPONENTS = 5;
    private Array<Component> componentArray;

    private InputComponent inputComponent;
    private PhysicsComponent physicsComponent;
    private GraphicsComponent graphicsComponent;

    public Entity(InputComponent iC, PhysicsComponent pC, GraphicsComponent gC){
        Gdx.app.log(TAG, "Created");

        entityConfig = new EntityConfig();
        json = new Json();

        componentArray = new Array<Component>(MAX_COMPONENTS);

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

    public void dispose(){
        for(Component component: componentArray)
            component.dispose();
    }

    public Rectangle getCurrentBoundingBox(){
        return physicsComponent.boundingBox;
    }

    public Vector2 getCurrentPosition(){
        return graphicsComponent.currentPosition;
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
        Array<EntityConfig> configs = new Array<EntityConfig>();

        ArrayList<JsonValue> list = j.fromJson(ArrayList.class, Gdx.files.internal(configFilePath));

        for(JsonValue jsonVal : list) {
            configs.add(j.readValue(EntityConfig.class, jsonVal));
        }

        return configs;
    }

    public static EntityConfig loadEntityConfigByPath(String entityConfigPath){
        EntityConfig entityConfig = Entity.getEntityConfig(entityConfigPath);
        EntityConfig serializedConfig = ProfileManager.getInstance().getProperty(entityConfig.getEntityID(), EntityConfig.class);

        if( serializedConfig == null ){
            return entityConfig;
        }else{
            return serializedConfig;
        }
    }

}
