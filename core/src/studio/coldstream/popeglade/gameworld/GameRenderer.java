package studio.coldstream.popeglade.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.gamehelpers.LocationHandler;
import studio.coldstream.popeglade.gameobjects.Level;
import studio.coldstream.popeglade.gameobjects.Player;
import studio.coldstream.popeglade.gameobjects.Pointer;

/**
 * Created by Scalar on 01/08/2017.
 */

public class GameRenderer {
    private GameWorld myWorld;
    private Vector2 midPoints;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private BitmapFont font;

    //Game Level
    private Level level;

    //Game Objects
    private Player player;
    private Pointer pointer;

    //Game Assets
    private Animation playerAnimation[];

    private LocationHandler lh;

    public GameRenderer(GameWorld world, int midPointX, int midPointY) {
        myWorld = world;
        midPoints = new Vector2(midPointX,midPointY);

        font = new BitmapFont();
        font.getData().setScale(.4f);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, midPointX, midPointY);
        //cam.update();

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        lh = new LocationHandler();

        initGameObjects();
        initAssets();

    }

    public void render(float delta, float runTime) {
        //Gdx.app.log("GameRenderer", "render");

        //Clear and draw background
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glEnable(Gdx.gl20.GL_BLEND);

        //Constrain the camera to the map area
        cam.position.x = MathUtils.clamp(player.getX(), cam.viewportWidth / 2, level.getWidth() - cam.viewportWidth / 2);
        cam.position.y = MathUtils.clamp(player.getY(), cam.viewportHeight / 2, level.getHeight() - cam.viewportHeight / 2);
        cam.update();

        //Draw map
        level.getTiledMapRenderer().setView(cam);
        level.getTiledMapRenderer().render();

        //Draw all sprites
        batcher.setProjectionMatrix(cam.combined); //Super important line!
        batcher.begin();

            //Draw Player
            batcher.draw((TextureRegion) playerAnimation[player.getRotation()].getKeyFrame(runTime),
                player.getX()- player.getWidth() / 2, player.getY(), player.getWidth(), player.getHeight());

        batcher.end();

        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            //Draw Player Collision Circle
            shapeRenderer.setColor(1,0,0,0.2f);
            shapeRenderer.rect(player.getBoundingRect().x, player.getBoundingRect().y, player.getBoundingRect().width, player.getBoundingRect().height);
            //shapeRenderer.rect(player.getBoundingRectY().x, player.getBoundingRectY().y, player.getBoundingRectY().width, player.getBoundingRectY().height);

            shapeRenderer.rect(myWorld.getLevel().getRect1().x, myWorld.getLevel().getRect1().y, myWorld.getLevel().getRect1().width, myWorld.getLevel().getRect1().height);
            //shapeRenderer.rect(myWorld.getRect2().x, myWorld.getRect2().y, myWorld.getRect2().width, myWorld.getRect2().height);

            /*for(int i = 0; i < 1; i++){
                shapeRenderer.rect(lh.playerTileRect(player,level).x, lh.playerTileRect(player,level).y, lh.playerTileRect(player,level).width, lh.playerTileRect(player,level).height);
            }*/

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            /*for(int i = 0; i < 1; i++){
                shapeRenderer.rect(lh.playerTileRect(player,level).x, lh.playerTileRect(player,level).y, lh.playerTileRect(player,level).width, lh.playerTileRect(player,level).height);
            }*/

            for(int i = 0; i < 9; i++){
                if(lh.isTileWall(lh.playerNineTile(player,level).get(i)))
                    shapeRenderer.rect(lh.playerNineTileRect(player,level).get(i).x, lh.playerNineTileRect(player,level).get(i).y, lh.playerNineTileRect(player,level).get(i).width, lh.playerNineTileRect(player,level).get(i).height);
            }

        shapeRenderer.end();

        //shapeRenderer.setProjectionMatrix(cam.projection);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(0.5f,1,0.5f,0.2f);
            shapeRenderer.rect(pointer.getPosition().x, pointer.getPosition().y, 10, 10);
        shapeRenderer.end();

        batcher.begin();

            for(int i = 0; i < 9; i++){
                font.draw(batcher, lh.playerNineTile(player, level).get(i).getTile().getId() + "", lh.playerNineTileRect(player,level).get(i).x + 2, lh.playerNineTileRect(player,level).get(i).y + 8);
            }
        batcher.end();


    }

    public OrthographicCamera getCamera(){
        return cam;
    }

    private void initGameObjects() {
        player = myWorld.getPlayer();
        level = myWorld.getLevel();
        pointer = myWorld.getPointer();
    }

    private void initAssets() {
        playerAnimation = AssetLoader.playerAnimation;
    }
}
