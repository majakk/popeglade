package studio.coldstream.popeglade.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.gameobjects.Level;
import studio.coldstream.popeglade.gameobjects.Player;

/**
 * Created by Scalar on 01/08/2017.
 */

public class GameRenderer {
    private GameWorld myWorld;
    private Vector2 midPoints;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;

    //Game Level
    private Level level;

    //Game Objects
    private Player player;


    //Game Assets
    private Animation playerAnimation[];

    public GameRenderer(GameWorld world, int midPointX, int midPointY) {
        myWorld = world;
        midPoints = new Vector2(midPointX,midPointY);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, midPointX, midPointY);
        //cam.update();

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();

    }

    public void render(float delta, float runTime) {
        //Gdx.app.log("GameRenderer", "render");

        //Clear and draw background
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glEnable(Gdx.gl20.GL_BLEND);

        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
                player.getX(), player.getY(), player.getWidth(), player.getHeight());

        batcher.end();

        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            //Draw Player Collision Circle
            shapeRenderer.setColor(1,0,0,0.2f);
            shapeRenderer.rect(player.getBoundingRectX().x, player.getBoundingRectX().y, player.getBoundingRectX().width, player.getBoundingRectX().height);
            shapeRenderer.rect(player.getBoundingRectY().x, player.getBoundingRectY().y, player.getBoundingRectY().width, player.getBoundingRectY().height);

            shapeRenderer.rect(myWorld.getLevel().getRect1().x, myWorld.getLevel().getRect1().y, myWorld.getLevel().getRect1().width, myWorld.getLevel().getRect1().height);
            //shapeRenderer.rect(myWorld.getRect2().x, myWorld.getRect2().y, myWorld.getRect2().width, myWorld.getRect2().height);

        shapeRenderer.end();

    }

    /*public OrthographicCamera getCamera(){
        return cam;
    }*/

    private void initGameObjects() {
        player = myWorld.getPlayer();
        level = myWorld.getLevel();
    }

    private void initAssets() {
        playerAnimation = AssetLoader.playerAnimation;
    }
}
