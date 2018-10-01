package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import studio.coldstream.popeglade.gameobjects.maps.Map;
import studio.coldstream.popeglade.gameobjects.maps.MapManager;

import static studio.coldstream.popeglade.gameobjects.entities.Entity.Direction.*;
import static studio.coldstream.popeglade.gameobjects.entities.PhysicsComponent.BoundingBoxLocation.*;

public abstract class PhysicsComponent implements Component {
    private static final String TAG = PhysicsComponent.class.getSimpleName();

    public abstract void update(Entity entity, MapManager mapMgr, float delta);

    private final static float DEFAULT_VELOCITY = 4.0f;
    private final static float VELOCITY_DIAG_FACTOR = 0.7f;

    public enum BoundingBoxLocation{
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        CENTER,
    }

    protected Vector2 nextEntityPosition;
    protected Vector2 currentEntityPosition;
    protected Entity.Direction currentDirection;
    protected Json json;
    protected Vector2 velocity;

    protected Array<Entity> tempEntities;

    protected Array<Rectangle> boundingBox;
    protected BoundingBoxLocation boundingBoxLocation;
    //protected Ray _selectionRay;

    PhysicsComponent(){
        this.nextEntityPosition = new Vector2(0,0);
        this.currentEntityPosition = new Vector2(0,0);
        this.velocity = new Vector2(DEFAULT_VELOCITY, DEFAULT_VELOCITY);
        this.boundingBox = new Array<Rectangle>(10);
        this.json = new Json();
        this.tempEntities = new Array<>();
        this.boundingBoxLocation = BoundingBoxLocation.BOTTOM_LEFT;
        //_selectionRay = new Ray(new Vector3(), new Vector3());
        //new Rectangle(1,1,1,1);
    }

    @Override
    public void receiveMessage(String message) {}

    protected void setNextPositionToCurrentX(Entity entity){
        this.currentEntityPosition.x = nextEntityPosition.x;
        //this.currentEntityPosition.y = nextEntityPosition.y;

        //Gdx.app.debug(TAG, "SETTING Current Position " + entity.getEntityConfig().getEntityID() + ": (" + currentEntityPosition.x + "," + currentEntityPosition.y + ")");
        entity.sendMessage(MESSAGE.CURRENT_POSITION, json.toJson(currentEntityPosition));
    }

    protected void setNextPositionToCurrentY(Entity entity){
        //this.currentEntityPosition.x = nextEntityPosition.x;
        this.currentEntityPosition.y = nextEntityPosition.y;

        //Gdx.app.debug(TAG, "SETTING Current Position " + entity.getEntityConfig().getEntityID() + ": (" + currentEntityPosition.x + "," + currentEntityPosition.y + ")");
        entity.sendMessage(MESSAGE.CURRENT_POSITION, json.toJson(currentEntityPosition));
    }

    protected void calculateNextPositionX(float deltaTime){
        if( currentDirection == null ) return;

        if( deltaTime > .7) return;

        float testX = currentEntityPosition.x;
        //float testY = currentEntityPosition.y;

        velocity.scl(deltaTime);

        switch (currentDirection) {
            case LEFT_UP :
                testX -= VELOCITY_DIAG_FACTOR * velocity.x;
                break;
            case RIGHT_UP :
                testX += VELOCITY_DIAG_FACTOR * velocity.x;
                break;
            case LEFT_DOWN :
                testX -= VELOCITY_DIAG_FACTOR * velocity.x;
                break;
            case RIGHT_DOWN :
                testX += VELOCITY_DIAG_FACTOR * velocity.x;
                break;
            case LEFT :
                testX -=  velocity.x;
                break;
            case RIGHT :
                testX += velocity.x;
                break;
            default:
                break;
        }

        nextEntityPosition.x = testX;
        //nextEntityPosition.y = testY;

        //velocity
        velocity.scl(1 / deltaTime);
    }

    protected void calculateNextPositionY(float deltaTime){
        if( currentDirection == null ) return;

        if( deltaTime > .7) return;

        //float testX = currentEntityPosition.x;
        float testY = currentEntityPosition.y;

        velocity.scl(deltaTime);

        switch (currentDirection) {
            case LEFT_UP :
                testY += VELOCITY_DIAG_FACTOR * velocity.y;
                break;
            case RIGHT_UP :
                testY += VELOCITY_DIAG_FACTOR * velocity.y;
                break;
            case LEFT_DOWN :
                testY -= VELOCITY_DIAG_FACTOR * velocity.y;
                break;
            case RIGHT_DOWN :
                testY -= VELOCITY_DIAG_FACTOR * velocity.y;
                break;
            case UP :
                testY += velocity.y;
                break;
            case DOWN :
                testY -= velocity.y;
                break;
            default:
                break;
        }

        //nextEntityPosition.x = testX;
        nextEntityPosition.y = testY;

        //velocity
        velocity.scl(1 / deltaTime);
    }

    protected boolean isCollisionWithMapEntities(Entity entity, MapManager mapMgr){
        tempEntities.clear();
        tempEntities.addAll(mapMgr.getCurrentMapEntities());
        //tempEntities.addAll(mapMgr.getCurrentMapQuestEntities());
        boolean isCollisionWithMapEntities = false;

        for(Entity mapEntity: tempEntities){
            //Check for testing against self
            if( mapEntity.equals(entity) ){
                continue;
            }

            Array<Rectangle> targetRect = mapEntity.getCurrentBoundingBoxes();
            for (Rectangle bB : boundingBox) {
                for (Rectangle tR : targetRect) {

                    if (bB.overlaps(tR)) {
                        //Gdx.app.log(TAG, "Testing Collision!: " + boundingBox.get(boundingBox.size-1).x);
                        //Collision
                        entity.sendMessage(MESSAGE.COLLISION_WITH_ENTITY);
                        isCollisionWithMapEntities = true;
                        break;
                    }
                }
            }
        }
        tempEntities.clear();
        return isCollisionWithMapEntities;
    }

    protected boolean isCollision(Entity entitySource, Entity entityTarget){
        boolean isCollisionWithMapEntities = false;

        if( entitySource.equals(entityTarget) ){
            return false;
        }

        if (entitySource.getCurrentBoundingBoxes().get(0).overlaps(entityTarget.getCurrentBoundingBoxes().get(0)) ){
            //Collision
            entitySource.sendMessage(MESSAGE.COLLISION_WITH_ENTITY);
            isCollisionWithMapEntities = true;
        }

        return isCollisionWithMapEntities;
    }

    protected boolean isCollisionWithMapLayer(Entity entity, MapManager mapMgr){
        MapLayer mapCollisionLayer =  mapMgr.getCollisionLayer();

        if( mapCollisionLayer == null ){
            return false;
        }

        Rectangle rectangle = null;

        for( MapObject object: mapCollisionLayer.getObjects()){
            if(object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject)object).getRectangle();
                for (Rectangle bB : boundingBox) {
                    if (bB.overlaps(rectangle)) {
                        //Collision
                        entity.sendMessage(MESSAGE.COLLISION_WITH_MAP);
                        return true;
                    }
                }
            }
        }

        return false;
    }


    /*protected void initBoundingBox(float percentageWidthReduced, float percentageHeightReduced){
        //Update the current bounding box
        float width;
        float height;
        Gdx.app.debug(TAG, "frameDimension: " + Entity.frameDimensions.x + ":" + Entity.frameDimensions.y);
        float origWidth =  Entity.frameDimensions.x;
        float origHeight = Entity.frameDimensions.y;

        float widthReductionAmount = 1.0f - percentageWidthReduced; //.8f for 20% (1 - .20)
        float heightReductionAmount = 1.0f - percentageHeightReduced; //.8f for 20% (1 - .20)

        if( widthReductionAmount > 0 && widthReductionAmount < 1){
            width = Entity.frameDimensions.x * widthReductionAmount;
        }else{
            width = Entity.frameDimensions.x;
        }

        if( heightReductionAmount > 0 && heightReductionAmount < 1){
            height = Entity.frameDimensions.y * heightReductionAmount;
        }else{
            height = Entity.frameDimensions.y;
        }

        if( width == 0 || height == 0){
            Gdx.app.debug(TAG, "Width and Height are 0!! " + width + ":" + height);
        }

        //Need to account for the unitscale, since the map coordinates will be in pixels
        float minX;
        float minY;
        float ratio = origWidth / origHeight;

        if( Map.UNIT_SCALE > 0 ) {
            minX = nextEntityPosition.x / Map.UNIT_SCALE;
            minY = nextEntityPosition.y / Map.UNIT_SCALE;
        }else{
            minX = nextEntityPosition.x;
            minY = nextEntityPosition.y;
        }

        boundingBox.setWidth(width);
        boundingBox.setHeight(height);

        Gdx.app.debug(TAG, "Width and Height are NAUGHTY!! " + width + ":" + height);

        switch(boundingBoxLocation){
            case BOTTOM_LEFT:
                boundingBox.set(minX, minY, width, height);
                break;
            case BOTTOM_CENTER:
                boundingBox.setCenter(minX + origWidth/2, minY + origHeight/4);
                break;
            case CENTER:
                boundingBox.setCenter(minX + origWidth/2, minY + origHeight/2);
                break;
        }

        //Gdx.app.debug(TAG, "SETTING Bounding Box for " + entity.getEntityConfig().getEntityID() + ": (" + minX + "," + minY + ")  width: " + width + " height: " + height);
    }*/

    protected void updatePlayerBoundingBoxPosition(Vector2 position){
        //Need to account for the unitscale, since the map coordinates will be in pixels
        float minX;
        float minY;

        if( Map.UNIT_SCALE > 0 ) {
            minX = position.x / Map.UNIT_SCALE;
            minY = position.y / Map.UNIT_SCALE;
        }else{
            minX = position.x;
            minY = position.y;
        }

        for(Rectangle bB : boundingBox) {
            switch (boundingBoxLocation) {
                case BOTTOM_LEFT:
                    bB.set(minX, minY, bB.getWidth(), bB.getHeight());
                    break;
                case BOTTOM_CENTER:
                    //boundingBox.setCenter(minX + Entity.frameDimensions.x/(2), minY + Entity.frameDimensions.y/(4));
                    bB.set(minX + (Entity.frameDimensions.x - bB.getWidth()) / (2), minY, bB.getWidth(), bB.getHeight());
                    break;
                case CENTER:
                    bB.setCenter(minX + Entity.frameDimensions.x / (2), minY + Entity.frameDimensions.y / (2));
                    break;
            }
        }
        //Gdx.app.debug(TAG, "SETTING Bounding Box for " + entity.getEntityConfig().getEntityID() + ": (" + minX + "," + minY + ")  width: " + width + " height: " + height);
    }

    protected void updateNPCBoundingBoxPosition(Entity entity, Vector2 position){
        //Need to account for the unitscale, since the map coordinates will be in pixels
        float minX;
        float minY;

        if( Map.UNIT_SCALE > 0 ) {
            minX = position.x / Map.UNIT_SCALE;
            minY = position.y / Map.UNIT_SCALE;
        }else{
            minX = position.x;
            minY = position.y;
        }

        int i = 0;
        for(Rectangle bB : this.boundingBox) {
            bB.set(minX + entity.getEntityConfig().getBoundingBox().get(i).getBoundingBox().x, minY + entity.getEntityConfig().getBoundingBox().get(i).getBoundingBox().y, bB.getWidth(), bB.getHeight());
            i++;
        }

    }

    protected void initBoundingBox(float percentageWidth, float percentageHeight){
        float newUnitScaleX = (Entity.frameDimensions.x / Entity.frameDimensions.x) ;
        float newUnitScaleY = (Entity.frameDimensions.y / Entity.frameDimensions.y) ;
        boundingBox.add(new Rectangle(nextEntityPosition.x / Map.UNIT_SCALE, nextEntityPosition.y / Map.UNIT_SCALE, Entity.frameDimensions.x * percentageWidth / newUnitScaleX, Entity.frameDimensions.y * percentageHeight / newUnitScaleY));
        return;
    }

    protected void addBoundingBox(Rectangle adam){
        this.boundingBox.add(new Rectangle(adam));
        Gdx.app.log(TAG, "Bounding Boxes Added: " + boundingBox.get(boundingBox.size-1).x);
    }
}
