package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.Hashtable;

public class EntityFactory {
    private static final String TAG = EntityFactory.class.getSimpleName();

    private static EntityFactory instance = null;
    private static Json json = new Json();
    private Hashtable<String, EntityConfig> entities;

    public enum EntityType{
        PLAYER,
        POINTER,
        DEMO_PLAYER,
        NPC,
        MAP_OBJECTS
    }

    public enum EntityName{
        PLAYER_GNOME,
        POINTER_ARROW,
        TOWN_GUARD_WALKING,
        SMALL_TREE, MEDIUM_TREE, SHRUB,
        TOWN_BLACKSMITH,
        TOWN_MAGE,
        TOWN_INNKEEPER,
        TOWN_FOLK1, TOWN_FOLK2, TOWN_FOLK3, TOWN_FOLK4, TOWN_FOLK5,
        TOWN_FOLK6, TOWN_FOLK7, TOWN_FOLK8, TOWN_FOLK9, TOWN_FOLK10,
        TOWN_FOLK11, TOWN_FOLK12, TOWN_FOLK13, TOWN_FOLK14, TOWN_FOLK15,
        FIRE
    }

    public static String TREES_CONFIG = "android/assets/scripts/trees.json";
    public static String PLAYER_CONFIG = "android/assets/scripts/player.json";
    public static String POINTER_CONFIG = "android/assets/scripts/pointer.json";

    private EntityFactory(){
        entities = new Hashtable<>();

        Array<EntityConfig> treesConfigs = Entity.getEntityConfigs(TREES_CONFIG);
        for( EntityConfig config: treesConfigs){
            entities.put(config.getEntityID(), config);
        }

        /*Array<EntityConfig> environmentalEntityConfigs = Entity.getEntityConfigs(ENVIRONMENTAL_ENTITY_CONFIGS);
        for( EntityConfig config: environmentalEntityConfigs){
            entities.put(config.getEntityID(), config);
        }*/


        //entities.put(EntityName.TOWN_GUARD_WALKING.toString(), Entity.loadEntityConfigByPath(TOWN_GUARD_WALKING_CONFIG));
        //entities.put(EntityName.TOWN_BLACKSMITH.toString(), Entity.loadEntityConfigByPath(TOWN_BLACKSMITH_CONFIG));
        //entities.put(EntityName.TOWN_MAGE.toString(), Entity.loadEntityConfigByPath(TOWN_MAGE_CONFIG));
        //entities.put(EntityName.TOWN_INNKEEPER.toString(), Entity.loadEntityConfigByPath(TOWN_INNKEEPER_CONFIG));
        //entities.put(EntityName.TREES.toString(), Entity.loadEntityConfigByPath(TREES_CONFIG));
        entities.put(EntityName.PLAYER_GNOME.toString(), Entity.loadEntityConfigByPath(PLAYER_CONFIG));
        //entities.put(EntityName.POINTER_ARROW.toString(), Entity.loadEntityConfigByPath(POINTER_CONFIG));
    }

    public static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }

        return instance;
    }

    static public Entity getEntity(EntityType entityType) {
        Entity entity = null;
        switch (entityType) {
            case PLAYER:
                entity = new Entity(
                        new PlayerInputComponent(),
                        new PlayerPhysicsComponent(),
                        new PlayerGraphicsComponent());
                entity.setEntityConfig(Entity.getEntityConfig(EntityFactory.PLAYER_CONFIG));
                entity.getEntityConfig().setEntityID("PLAYER");
                entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));
                entity.sendMessage(Component.MESSAGE.INIT_BOUNDING_BOX, json.toJson(entity.getEntityConfig()));
                return entity;
            case POINTER:
                entity = new Entity(
                        new PointerInputComponent(),
                        new PointerPhysicsComponent(),
                        new PointerGraphicsComponent());
                entity.setEntityConfig(Entity.getEntityConfig(EntityFactory.POINTER_CONFIG));
                //entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));
                return entity;
            case DEMO_PLAYER:
                entity = new Entity(
                        new NPCInputComponent(),
                        new PlayerPhysicsComponent(),
                        new PlayerGraphicsComponent());
                return entity;
            case NPC:
                entity = new Entity(
                        new NPCInputComponent(),
                        new NPCPhysicsComponent(),
                        new NPCGraphicsComponent());
                //entity.setEntityConfig(Entity.getEntityConfig(EntityFactory.TREES_CONFIG));

                return entity;
           /* case MAP_OBJECTS:
                entity = new Entity(
                        new NPCInputComponent(),
                        new NPCPhysicsComponent(),
                        new NPCGraphicsComponent());
                return entity;*/
            default:
                return null;

        }
    }

    public Entity getEntityByName(EntityName entityName){
        EntityConfig config = new EntityConfig(entities.get(entityName.toString()));
        Entity entity = Entity.initEntity(config);
        return entity;
    }

}
