package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class EntityConfig {
    private static final String TAG = EntityConfig.class.getSimpleName();

    private Array<AnimationConfig> animationConfig;
    //private Array<ItemTypeID> inventory;
    private Entity.State state = Entity.State.IDLE;
    private Entity.Direction direction = Entity.Direction.DOWN;
    private String entityID;
    private String conversationConfigPath;
    private String questConfigPath;
    private String currentQuestID;
    private String itemTypeID;
    private String frameWidth;
    private String frameHeight;
    private String numOfTilesWidth;
    private String numOfTilesHeight;
    private Array<BoundingBox> boundingBox;
    //private String test;
    private ObjectMap<String, String> entityProperties;



    //Ok so different entities might have different types of "health", eg. SAW_HEALTH, WATER_HEALTH, etc
    public enum EntityProperties{
        ENTITY_HEALTH_POINTS,
        ENTITY_ATTACK_POINTS,
        ENTITY_DEFENSE_POINTS,
        ENTITY_HIT_DAMAGE_TOTAL,
        ENTITY_XP_REWARD,
        ENTITY_GP_REWARD,
        NONE
    }

    EntityConfig(){
        animationConfig = new Array<>();
        //inventory = new Array<ItemTypeID>();
        entityProperties = new ObjectMap<>();
        //Gdx.app.log(TAG, "End of constructor: " + entityProperties.get(""));

    }

    EntityConfig(EntityConfig config){
        state = config.getState();
        direction = config.getDirection();
        entityID = config.getEntityID();
        conversationConfigPath = config.getConversationConfigPath();
        questConfigPath = config.getQuestConfigPath();
        currentQuestID = config.getCurrentQuestID();
        itemTypeID = config.getItemTypeID();
        frameWidth = config.getFrameWidth();
        frameHeight = config.getFrameHeight();
        numOfTilesWidth = config.getNumOfTilesWidth();
        numOfTilesHeight = config.getNumOfTilesHeight();
        boundingBox = new Array<BoundingBox>();
        boundingBox.addAll(config.getBoundingBox());
        animationConfig = new Array<AnimationConfig>();
        animationConfig.addAll(config.getAnimationConfig());

        //inventory = new Array<ItemTypeID>();
        //inventory.addAll(config.getInventory());

        entityProperties = new ObjectMap<String, String>();
        entityProperties.putAll(config.entityProperties);
    }

    public String getNumOfTilesWidth() {
        return numOfTilesWidth;
    }

    public String getNumOfTilesHeight() {
        return numOfTilesHeight;
    }

    public String getFrameHeight() {
        return frameHeight;
    }

    public String getFrameWidth() {
        return frameWidth;
    }

    public Vector2 getNumOfTilesDimensions() {
        //Gdx.app.log(TAG, "Created " + getFrameWidth() + " : " + getFrameHeight());
        //return new Vector2(Integer.decode(frameWidth), Integer.decode(frameHeight));
        return new Vector2(Integer.valueOf(getNumOfTilesWidth()),Integer.valueOf(getNumOfTilesHeight()));
        //return new Vector2(Integer.valueOf(getFrameWidth()) * Integer.valueOf(getNumOfTilesWidth()),Integer.valueOf(getFrameHeight()) * Integer.valueOf(getNumOfTilesHeight()));
    }

    public Vector2 getFrameDimensions() {
        return new Vector2(Integer.valueOf(getFrameWidth()),Integer.valueOf(getFrameHeight()));
    }

    public void setFrameWidth(String frameWidth) {
        this.frameWidth = frameWidth;
    }

    public void setFrameHeight(String frameHeight) {
        this.frameHeight = frameHeight;
    }

    public ObjectMap<String, String> getEntityProperties() {
        return entityProperties;
    }

    public void setEntityProperties(ObjectMap<String, String> entityProperties) {
        this.entityProperties = entityProperties;
    }

    public void setPropertyValue(String key, String value){
        entityProperties.put(key, value);
    }

    public String getPropertyValue(String key){
        Object propertyVal = entityProperties.get(key);
        if( propertyVal == null ) return new String();
        return propertyVal.toString();
    }

    public String getCurrentQuestID() {
        return currentQuestID;
    }

    public void setCurrentQuestID(String currentQuestID) {
        this.currentQuestID = currentQuestID;
    }

    public String getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(String itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public String getQuestConfigPath() {
        return questConfigPath;
    }

    public void setQuestConfigPath(String questConfigPath) {
        this.questConfigPath = questConfigPath;
    }

    public String getConversationConfigPath() {
        return conversationConfigPath;
    }

    public void setConversationConfigPath(String conversationConfigPath) {
        this.conversationConfigPath = conversationConfigPath;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public Entity.Direction getDirection() {
        return direction;
    }

    public void setDirection(Entity.Direction direction) {
        this.direction = direction;
    }

    public Entity.State getState() {

        return state;
    }

    public void setState(Entity.State state) {
        this.state = state;
    }

    public Array<BoundingBox> getBoundingBox() {
        return boundingBox;
    }

    public Array<AnimationConfig> getAnimationConfig() {
        return animationConfig;
    }

    public void addAnimationConfig(AnimationConfig animationConfig) {
        this.animationConfig.add(animationConfig);
    }

    /*public Array<ItemTypeID> getInventory() {
        return inventory;
    }*/

    /*public void setInventory(Array<ItemTypeID> inventory) {
        this.inventory = inventory;
    }*/

    //An inner class for the bounding box
    static public class BoundingBox{
        private String x;
        private String y;
        private String w;
        private String h;

        public BoundingBox(){
            x = new String();
            y = new String();
            w = new String();
            h = new String();
        }

        public Rectangle getBoundingBox() {
            return new Rectangle(
                    Integer.parseInt(x),
                    Integer.parseInt(y),
                    Integer.parseInt(w),
                    Integer.parseInt(h)
                    );
        }
    }

    //An inner class of the EntityConfig is AnimationConfig.
    static public class AnimationConfig{
        private float frameDuration = 1.0f;
        private Entity.AnimationType animationType;
        private Array<String> texturePaths;
        private Array<GridPoint2> gridPoints;

        public AnimationConfig(){
            animationType = Entity.AnimationType.IDLE;
            texturePaths = new Array<>();
            gridPoints = new Array<>();
        }

        public float getFrameDuration() {
            return frameDuration;
        }

        public void setFrameDuration(float frameDuration) {
            this.frameDuration = frameDuration;
        }

        public Array<String> getTexturePaths() {
            return texturePaths;
        }

        public void setTexturePaths(Array<String> texturePaths) {
            this.texturePaths = texturePaths;
        }

        public Array<GridPoint2> getGridPoints() {
            return gridPoints;
        }

        public void setGridPoints(Array<GridPoint2> gridPoints) {
            this.gridPoints = gridPoints;
        }

        public Entity.AnimationType getAnimationType() {
            return animationType;
        }

        public void setAnimationType(Entity.AnimationType animationType) {
            this.animationType = animationType;
        }
    }
}
