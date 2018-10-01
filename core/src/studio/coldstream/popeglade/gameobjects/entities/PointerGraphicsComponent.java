package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.gamehelpers.LocationHandler;
import studio.coldstream.popeglade.gameobjects.maps.Map;
import studio.coldstream.popeglade.gameobjects.maps.MapManager;
import studio.coldstream.popeglade.screens.MainGameScreen;

import static studio.coldstream.popeglade.gameobjects.maps.Map.UNIT_SCALE;

public class PointerGraphicsComponent extends GraphicsComponent {
    private static final String TAG = PointerGraphicsComponent.class.getSimpleName();

    LocationHandler lh;
    //private int cellID;
    private boolean clickFlag = false;

    public PointerGraphicsComponent(){
        AssetLoader.loadPointer();
        lh = new LocationHandler();
        currentPosition = new Vector2(0,0);
    }

    @Override
    public void receiveMessage(String message) {
        //Gdx.app.debug(TAG, "Got message " + message);
        String[] string = message.split(MESSAGE_TOKEN);

        if( string.length == 0 ) return;

        //Specifically for messages with 1 object payload
        if( string.length == 2 ) {
            if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_POSITION.toString())) {
                currentPosition = json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())) {
                currentPosition = json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_SELECT_ENTITY.toString())) {
                currentPosition = json.fromJson(Vector2.class, string[1]);
                //Gdx.app.debug(TAG, "PRESSED!! " + currentPosition.x + ":" + currentPosition.y);
                clickFlag = true;
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.LOAD_ANIMATIONS.toString())) {
                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
                Array<EntityConfig.AnimationConfig> animationConfigs = entityConfig.getAnimationConfig();
                Vector2 frameDimensions = entityConfig.getFrameDimensions();
                Vector2 numOfTilesDimensions = entityConfig.getNumOfTilesDimensions();
                for (EntityConfig.AnimationConfig animationConfig : animationConfigs) {
                    Array<String> textureNames = animationConfig.getTexturePaths();
                    Array<GridPoint2> points = animationConfig.getGridPoints();
                    Entity.AnimationType animationType = animationConfig.getAnimationType();
                    float frameDuration = animationConfig.getFrameDuration();
                    Animation<TextureRegion> animation = null;

                    if (textureNames.size == 1) {
                        animation = loadAnimation(textureNames.get(0), frameDimensions, numOfTilesDimensions, points, frameDuration);
                    } else if (textureNames.size == 2) {
                        animation = loadAnimation(textureNames.get(0), textureNames.get(1), points, frameDuration);
                    }

                    animations.put(animationType, animation);
                }
            }
        }
    }

    @Override
    public void dispose() {
        AssetLoader.unloadPointer();
    }

    @Override
    public void update(Entity entity, MapManager mapMgr, Batch batch, float delta){
        //Gdx.app.debug(TAG, "INFO " + entity.getCurrentPosition().x + ":" + entity.getCurrentPosition().y);
        //updateAnimations(delta);

        if(clickFlag) {
            TiledMapTileLayer.Cell adam = lh.pointerTile(entity, mapMgr, batch);
            Gdx.app.log(TAG,"Cell ID: " + adam.getTile().getId());

            Rectangle aR = new Rectangle(lh.actionTileRect(mapMgr.getPlayer(), mapMgr));
            aR.setSize(0.9f/UNIT_SCALE);
            aR.setPosition(aR.x/UNIT_SCALE,aR.y/UNIT_SCALE);
            //Gdx.app.log(TAG, "ActionTileRect: " + aR);

            boolean isCollisionWithMapEntities = false;
            String id = "";

            Rectangle bB = new Rectangle(lh.pointerTileRect(entity,mapMgr,batch));
            bB.setSize(1/UNIT_SCALE);
            bB.setPosition(bB.x/UNIT_SCALE,bB.y/UNIT_SCALE);

            //Gdx.app.log(TAG, "PointerTileRect: " + bB);

            //Gdx.app.log(TAG, "Testing Point/Action Intersect: " + (bB.x == aR.x && bB.y == aR.y) );
            //Rectangle bB = entity.getCurrentBoundingBoxes().get(0);
            for(Entity mapEntity: mapMgr.getCurrentMapEntities()){
                //Check for testing against self
                /*if( mapEntity.equals(entity) ){
                    continue;
                }*/
                //Gdx.app.log(TAG, "Testing Collision! " + mapEntity.getCurrentBoundingBoxes().get(0));
                //Gdx.app.log(TAG, "Testing Collision! " + bB);
                if(mapEntity.getEntityID() != EntityFactory.EntityType.PLAYER.toString()) {
                    Array<Rectangle> targetRect = mapEntity.getCurrentBoundingBoxes();

                    for (Rectangle tR : targetRect) {

                        //If both pointer and player rectangles overlap a bounding box
                        if (bB.overlaps(tR) && aR.overlaps(tR)) {
                            //Gdx.app.log(TAG, "Testing Collision!: " + bB.overlaps(tR) + " : " + aR.overlaps(tR));

                            //entity.sendMessage(MESSAGE.COLLISION_WITH_ENTITY);
                            id = mapEntity.getEntityID();
                            mapEntity.sendMessage(MESSAGE.INIT_SELECT_ENTITY, json.toJson("TESTTEST"));
                            isCollisionWithMapEntities = true;
                            break;
                        }
                    }
                }

            }

            Gdx.app.log(TAG,"Entity Clicked: " + isCollisionWithMapEntities + " : " + id);

            clickFlag = false;
        }

        if(MainGameScreen.isCollisionGridEnabled()) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(0.3f, 1, 0.3f, 0.2f);
            shapeRenderer.rect(lh.pointerTileRect(entity, mapMgr, batch).x, lh.pointerTileRect(entity, mapMgr, batch).y, lh.pointerTileRect(entity, mapMgr, batch).width, lh.pointerTileRect(entity, mapMgr, batch).height);
            shapeRenderer.end();
        }

    }
}