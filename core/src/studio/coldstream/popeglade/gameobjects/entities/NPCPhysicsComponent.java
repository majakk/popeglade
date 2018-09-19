package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gameobjects.maps.MapManager;

public class NPCPhysicsComponent extends PhysicsComponent {
    private static final String TAG = NPCPhysicsComponent.class.getSimpleName();

    private Entity.State state;

    public NPCPhysicsComponent(){
        boundingBoxLocation = BoundingBoxLocation.BOTTOM_CENTER;
        initBoundingBox(0.6f,0.25f);
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(Component.MESSAGE_TOKEN);

        if(string.length == 0) return;

        //Messages with one object payload
        if(string.length == 2) {
            if(string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())){
                currentEntityPosition = json.fromJson(Vector2.class, string[1]);
                nextEntityPosition.set(currentEntityPosition.x, currentEntityPosition.y);
            } else if(string[0].equalsIgnoreCase(MESSAGE.CURRENT_STATE.toString())) {
                state = json.fromJson(Entity.State.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_SELECT_ENTITY.toString())) {
                Gdx.app.log(TAG, "Init select Entity Message ends here in NPCPhysicsComponent");
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_BOUNDING_BOX.toString())) {
                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
                Array<EntityConfig.BoundingBox> boundingBoxes = entityConfig.getBoundingBox();
                for( EntityConfig.BoundingBox boundingBox : boundingBoxes ){
                    initBoundingBox(boundingBox.getBoundingBox()); //ToDO Here we need something like addBoundingBox...
                }
            }

        }
    }

    @Override
    public void dispose() {}

    @Override
    public void update(Entity entity, MapManager mapMgr, float delta){
        updateBoundingBoxPosition(nextEntityPosition);
    }
}
