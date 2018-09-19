package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gameobjects.maps.Map;
import studio.coldstream.popeglade.gameobjects.maps.MapManager;
import studio.coldstream.popeglade.screens.MainGameScreen;

public class NPCGraphicsComponent extends GraphicsComponent {
    private static final String TAG = NPCGraphicsComponent.class.getSimpleName();

    public static final float NPC_SPRITE_SCALE = 1.0f;

    private boolean isSelected = false;
    private boolean wasSelected = false;

    public NPCGraphicsComponent(){}

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if( string.length == 0 ) return;

        if( string.length == 1 ) {
            if (string[0].equalsIgnoreCase(MESSAGE.ENTITY_SELECTED.toString())) {
                if( wasSelected ){
                    isSelected = false;
                }else{
                    isSelected = true;
                }
            }else if (string[0].equalsIgnoreCase(MESSAGE.ENTITY_DESELECTED.toString())) {
                wasSelected = isSelected;
                isSelected = false;
            }
        }

        //Specifically for messages with 1 object payload
        if( string.length == 2 ) {
            if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_POSITION.toString())) {
                currentPosition = json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())) {
                currentPosition = json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_STATE.toString())) {
                currentState = json.fromJson(Entity.State.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_FRAME_DIMENSIONS.toString())) {
                frameDimensions = json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_NUM_OF_TILES_DIMENSIONS.toString())) {
                numOfTilesDimensions = json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.LOAD_ANIMATIONS.toString())) {
                EntityConfig entityConfig = json.fromJson(EntityConfig.class, string[1]);
                Array<EntityConfig.AnimationConfig> animationConfigs = entityConfig.getAnimationConfig();
                Vector2 frameDimensions = entityConfig.getFrameDimensions();
                Vector2 numOfTilesDimensions = entityConfig.getNumOfTilesDimensions();
                for( EntityConfig.AnimationConfig animationConfig : animationConfigs ){
                    Array<String> textureNames = animationConfig.getTexturePaths();
                    Array<GridPoint2> points = animationConfig.getGridPoints();
                    Entity.AnimationType animationType = animationConfig.getAnimationType();
                    float frameDuration = animationConfig.getFrameDuration();
                    Animation<TextureRegion> animation = null;

                    if( textureNames.size == 1) {
                        animation = loadAnimation(textureNames.get(0), frameDimensions, numOfTilesDimensions, points, frameDuration);
                    }else if( textureNames.size == 2){
                        //Gdx.app.log(TAG, "SIZE 2222222222222222222222222");
                        animation = loadAnimation(textureNames.get(0), textureNames.get(1), points, frameDuration);
                    }

                    animations.put(animationType, animation);
                }
            }
        }
    }

    @Override
    public void dispose() {}

    @Override
    public void update(Entity entity, MapManager mapMgr, Batch batch, float delta){
        updateAnimations(delta);

        //Camera camera = mapMgr.getCamera();
        //camera.update();
        //Gdx.app.log(TAG, "" + (currentFrame == null));
        batch.begin();
        //Gdx.app.log(TAG, "" + entity.getEntityConfig().getNumOfTilesDimensions().x);
        batch.draw(currentFrame, currentPosition.x, currentPosition.y,
                frameDimensions.x * numOfTilesDimensions.x * Map.UNIT_SCALE * NPC_SPRITE_SCALE,
                frameDimensions.y * numOfTilesDimensions.y * Map.UNIT_SCALE * NPC_SPRITE_SCALE); //Should it be compensated for the ratio between texture_width (player) and MapTileWidth?
        batch.end();

        /*if(MainGameScreen.isCollisionGridEnabled()) {
            Rectangle rect = entity.getCurrentBoundingBox();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(rect.getX() * Map.UNIT_SCALE, rect.getY() * Map.UNIT_SCALE, rect.getWidth() * Map.UNIT_SCALE, rect.getHeight() * Map.UNIT_SCALE);
            shapeRenderer.end();
        }*/

    }
}
