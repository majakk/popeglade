package studio.coldstream.popeglade.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.gamehelpers.LocationHandler;
import studio.coldstream.popeglade.gameobjects.HeadUpDisplay;
import studio.coldstream.popeglade.gameobjects.Terrain;
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

    //Game Terrain
    private Terrain terrain;

    //Game Objects
    private Player player;
    private Pointer pointer;
    private HeadUpDisplay hud;

    //Game Assets
    private Animation playerAnimation[];

    private LocationHandler lh;

    private TiledMapTileLayer.Cell cell;
    private TiledMapTileLayer tileLayer;

    private int[] toplayers = new int[3];

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

        toplayers = new int[]{2};

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
        cam.position.x = MathUtils.clamp(player.getX(), cam.viewportWidth / 2, terrain.getWidth() - cam.viewportWidth / 2);
        cam.position.y = MathUtils.clamp(player.getY(), cam.viewportHeight / 2, terrain.getHeight() - cam.viewportHeight / 2);
        cam.update();

        //Draw bottom map
        terrain.getTiledMapRenderer().setView(cam);
        terrain.getTiledMapRenderer().render(new int[]{0,1,2});

        //Draw all sprites

        batcher.setProjectionMatrix(cam.combined); //Super important line!
        batcher.begin();

            //Draw Player
            player.render(delta, runTime, batcher);

        batcher.end();

        /*Gdx.gl.glEnable(Gdx.gl20.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(1, 1, 1, 0.5f));
        shapeRenderer.circle(player.getX(), player.getY(), 25);
        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl20.GL_BLEND);
        */


        //Draw top map
        Gdx.gl.glEnable(Gdx.gl20.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
        //terrain.getTiledMap().getLayers().get(1).setOpacity(0.5f);

        /*tileLayer = (TiledMapTileLayer) terrain.getTiledMap().getLayers().get(1);
        cell = tileLayer.getCell((int)Math.floor((double)pointer.getX()/(double) terrain.getSingleTileWidth()),(int)Math.floor((double)pointer.getY()/(double) terrain.getSingleTileHeight()));*/


        terrain.getTiledMapRenderer().render(new int[]{3});
        Gdx.gl.glDisable(Gdx.gl20.GL_BLEND);

        //Draw HUD
        hud.render(delta, runTime, batcher, shapeRenderer, font, cam);


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
                if(lh.isTileWall(lh.playerNineTile(player, terrain).get(i)))
                    shapeRenderer.rect(lh.playerNineTileRect(player, terrain).get(i).x, lh.playerNineTileRect(player, terrain).get(i).y, lh.playerNineTileRect(player, terrain).get(i).width, lh.playerNineTileRect(player, terrain).get(i).height);
            }

        shapeRenderer.end();

        //Draw pointer rect
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            shapeRenderer.setColor(0.3f,1,0.3f,0.2f);
            shapeRenderer.rect(lh.pointerTileRect(pointer, terrain).x, lh.pointerTileRect(pointer, terrain).y, lh.pointerTileRect(pointer, terrain).width, lh.pointerTileRect(pointer, terrain).height);

        shapeRenderer.end();

        //Draw values in rects
        batcher.begin();

            //Ninerect
            /*for(int i = 0; i < 9; i++){
                font.draw(batcher, lh.playerNineTile(player, terrain).get(i).getTile().getId() + "", lh.playerNineTileRect(player, terrain).get(i).x + 2, lh.playerNineTileRect(player, terrain).get(i).y + 8);
            }*/

            //Pointerrect
            font.draw(batcher, lh.pointerTile(pointer, terrain).getTile().getId() + "", lh.pointerTileRect(pointer, terrain).x + 2, lh.pointerTileRect(pointer, terrain).y + 8);

        batcher.end();



    }

    public OrthographicCamera getCamera(){
        return cam;
    }

    private void initGameObjects() {
        player = myWorld.getPlayer();
        terrain = myWorld.getTerrain();
        pointer = myWorld.getPointer();
        hud = new HeadUpDisplay(player);
    }

    private void initAssets() {
        playerAnimation = AssetLoader.playerAnimation;
    }
}
