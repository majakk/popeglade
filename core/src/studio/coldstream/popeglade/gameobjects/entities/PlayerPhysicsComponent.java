package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

import studio.coldstream.popeglade.gameobjects.maps.MapManager;

public class PlayerPhysicsComponent extends PhysicsComponent {
    private static final String TAG = PlayerPhysicsComponent.class.getSimpleName();

    private Entity.State state;

    public PlayerPhysicsComponent(){
        boundingBoxLocation = BoundingBoxLocation.BOTTOM_LEFT;
        //initBoundingBox(0.5f, 0.5f);
        mattiasBoundingBox(1.0f,1.0f);
        //_previousDiscovery = "";
        //_previousEnemySpawn = "0";
        //_mouseSelectCoordinates = new Vector3(0,0,0);
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
                //Gdx.app.log(TAG, "Init select Entity Message ends here");
            }
            //Gdx.app.log(TAG, "Key Pressed: " + currentEntityPosition + " : " + currentDirection);
        }
    }

    @Override
    public void dispose() {}

    @Override
    public void update(Entity entity, MapManager mapMgr, float delta) {
        //We want the hitbox to be at the feet for a better feel
        updateBoundingBoxPosition(nextEntityPosition);

        //updatePortalLayerActivation(mapMgr);
        //updateDiscoverLayerActivation(mapMgr);
        //updateEnemySpawnLayerActivation(mapMgr);

        /*if( _isMouseSelectEnabled ){
            selectMapEntityCandidate(mapMgr);
            _isMouseSelectEnabled = false;
        }*/

        //X-axis collisions
        //updateX(entity, delta);

        /*if(!anyWallCollision(player, terrain)){
            player.makeMoveX();
        }*/

        //Y-axis collisions
        //updateY(entity, delta);

        /*if(!anyWallCollision(player, terrain)){
            player.makeMoveY();
        }*/


        //X-axis collisions
        calculateNextPositionX(delta);

        //updateBoundingBoxPosition(currentEntityPosition);
        if (    !isCollisionWithMapLayer(entity, mapMgr) &&
                !isCollisionWithMapEntities(entity, mapMgr) &&
                state == Entity.State.WALKING){

            setNextPositionToCurrentX(entity);

            Camera camera = mapMgr.getCamera();
            camera.position.set(currentEntityPosition.x, currentEntityPosition.y, 0f);
            camera.update();
        }else{
            //updateBoundingBoxPosition(currentEntityPosition);
        }
        //calculateNextPositionX(delta);


        //Y-axis collisions

        calculateNextPositionY(delta);
        //updateBoundingBoxPosition(currentEntityPosition);
        if (    !isCollisionWithMapLayer(entity, mapMgr) &&
                !isCollisionWithMapEntities(entity, mapMgr) &&
                state == Entity.State.WALKING){

            setNextPositionToCurrentY(entity);

            Camera camera = mapMgr.getCamera();
            camera.position.set(currentEntityPosition.x, currentEntityPosition.y, 0f);
            camera.update();
        }else{
            //updateBoundingBoxPosition(currentEntityPosition);
        }
        //calculateNextPositionY(delta);
        updateBoundingBoxPosition(currentEntityPosition);


    }





}
