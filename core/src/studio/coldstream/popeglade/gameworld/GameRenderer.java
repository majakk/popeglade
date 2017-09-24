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

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.gamehelpers.LocationHandler;
import studio.coldstream.popeglade.gameobjects.HeadUpDisplay;
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
    private HeadUpDisplay hud;

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
            player.render(delta, runTime, batcher);

        batcher.end();

        //Draw HUD
        hud.render(delta, runTime, batcher, shapeRenderer, cam);


        /*** *************************************
         *
         *  The code below is for debugging only
         *
         * **************************************/

        Gdx.gl.glLineWidth(2);

        //Draw collision rects
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            //Draw Player Collision Rect
            shapeRenderer.setColor(1,0,0,0.2f);
            shapeRenderer.rect(player.getBoundingRect().x, player.getBoundingRect().y, player.getBoundingRect().width, player.getBoundingRect().height);

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            for(int i = 0; i < 9; i++){
                if(lh.isTileWall(lh.playerNineTile(player,level).get(i)))
                    shapeRenderer.rect(lh.playerNineTileRect(player,level).get(i).x, lh.playerNineTileRect(player,level).get(i).y, lh.playerNineTileRect(player,level).get(i).width, lh.playerNineTileRect(player,level).get(i).height);
            }

        shapeRenderer.end();

        //Draw pointer rect
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            shapeRenderer.setColor(0.3f,1,0.3f,0.2f);
            shapeRenderer.rect(lh.pointerTileRect(pointer,level).x, lh.pointerTileRect(pointer,level).y, lh.pointerTileRect(pointer,level).width, lh.pointerTileRect(pointer,level).height);

        shapeRenderer.end();

        //Draw values in rects
        batcher.begin();

            //Ninerect
            for(int i = 0; i < 9; i++){
                font.draw(batcher, lh.playerNineTile(player, level).get(i).getTile().getId() + "", lh.playerNineTileRect(player,level).get(i).x + 2, lh.playerNineTileRect(player,level).get(i).y + 8);
            }

            //Pointerrect
            font.draw(batcher, lh.pointerTile(pointer, level).getTile().getId() + "", lh.pointerTileRect(pointer,level).x + 2, lh.pointerTileRect(pointer,level).y + 8);

        batcher.end();



    }

    public OrthographicCamera getCamera(){
        return cam;
    }

    private void initGameObjects() {
        player = myWorld.getPlayer();
        level = myWorld.getLevel();
        pointer = myWorld.getPointer();
        hud = new HeadUpDisplay(player);
    }

    private void initAssets() {
        playerAnimation = AssetLoader.playerAnimation;
    }
}
