package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import studio.coldstream.popeglade.gameobjects.maps.MapManager;

public class PointerPhysicsComponent extends PhysicsComponent {
    private static final String TAG = PointerPhysicsComponent.class.getSimpleName();

    private Entity.State state;

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
                //Gdx.app.log(TAG, "Init select Entity Message ends here in NPCPhysicsComponent");
            }

        }
    }

    @Override
    public void dispose() {}

    @Override
    public void update(Entity entity, MapManager mapMgr, float delta){
        /*setNextPositionToCurrentX(entity);
        setNextPositionToCurrentY(entity);*/
    }
}
