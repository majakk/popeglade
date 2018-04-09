package studio.coldstream.popeglade.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.gamehelpers.LocationHandler;
import studio.coldstream.popeglade.gameobjects.HeadUpDisplay;
import studio.coldstream.popeglade.gameobjects.Terrain;
import studio.coldstream.popeglade.gameobjects.Player;
import studio.coldstream.popeglade.gameobjects.Pointer;
import studio.coldstream.popeglade.gameobjects.entities.Component;
import studio.coldstream.popeglade.gameobjects.entities.Entity;
import studio.coldstream.popeglade.gameobjects.maps.Map;
import studio.coldstream.popeglade.gameobjects.maps.MapManager;
import studio.coldstream.popeglade.screens.MainGameScreen;

/**
 * Created by Scalar on 01/08/2017.
 */

public class GameRenderer {
    private static final String TAG = GameRenderer.class.getSimpleName();

    private MainGameScreen myScreen;
    //private GameWorld myWorld;
    //private Vector2 midPoints;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    //private BitmapFont font;

    //Game Terrain
    //private Terrain terrain;

    //Game Objects
    //private Player player;
    private Pointer pointer;
    private HeadUpDisplay hud;

    //Game Assets
    //private Animation playerAnimation[];

    //private LocationHandler lh;

    //private TiledMapTileLayer.Cell cell;
    //private TiledMapTileLayer tileLayer;

    //private int[] toplayers = new int[3];

    private Json json;

    private static MapManager mapMgr;
    private static Entity player;
    private OrthogonalTiledMapRenderer mapRenderer = null;

    public GameRenderer(MainGameScreen screen) {
        Gdx.app.log(TAG, "Attached");

        myScreen = screen;
        myScreen.setupViewport(16,10);

        //midPoints = new Vector2(midPointX, midPointY);
        json = new Json();

        mapMgr = screen.getWorld().getMapMgr();
        player = screen.getWorld().getPlayerEntity();
        pointer = new Pointer();

        if( mapRenderer == null ){
            mapRenderer = new OrthogonalTiledMapRenderer(mapMgr.getCurrentTiledMap(), Map.UNIT_SCALE);
        }

        /*font = new BitmapFont();
        font.getData().setScale(.4f);*/

        cam = new OrthographicCamera();
        cam.setToOrtho(false, myScreen.getViewport().x, myScreen.getViewport().y);
        cam.update();

        mapMgr.setCamera(cam);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        //lh = new LocationHandler();

        //toplayers = new int[]{2};

        //initGameObjects();
        //initAssets();
        Gdx.app.log(TAG, "Mapsize X: " + mapMgr.getMapDimensions().x);
    }







    public void render(float delta, float runTime) {
        //Gdx.app.log("GameRenderer", "render");
        pointer.update(delta, player, mapMgr);
        //Clear and draw background
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glEnable(Gdx.gl20.GL_BLEND);

        //mapRenderer.setView(cam);

        mapRenderer.getBatch().enableBlending();
        mapRenderer.getBatch().setBlendFunction(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);

        //Constrain the camera to the map area
        cam.position.x = MathUtils.clamp(mapMgr.getPlayer().getCurrentPosition().x, cam.viewportWidth / 2, mapMgr.getMapDimensions().x * Map.UNIT_SCALE - cam.viewportWidth / 2);
        cam.position.y = MathUtils.clamp(mapMgr.getPlayer().getCurrentPosition().y, cam.viewportHeight / 2, mapMgr.getMapDimensions().y * Map.UNIT_SCALE - cam.viewportHeight / 2);
        cam.update();

        if( mapMgr.hasMapChanged() ) {
            mapRenderer.setMap(mapMgr.getCurrentTiledMap());
            player.sendMessage(Component.MESSAGE.INIT_START_POSITION, json.toJson(mapMgr.getPlayerStartUnitScaled()));
            //Gdx.app.log(TAG, "Mapsize X: " + mapMgr.getMapDimensions().x + ", cam.viewportWidth: " + cam.viewportWidth + ", CurrentPosX: " + mapMgr.getPlayer().getCurrentPosition().x);
            //cam.position.x = MathUtils.clamp(mapMgr.getPlayer().getCurrentPosition().x, cam.viewportWidth / 2, mapMgr.getMapDimensions().x - cam.viewportWidth / 2);
            //cam.position.y = MathUtils.clamp(mapMgr.getPlayer().getCurrentPosition().y, cam.viewportHeight / 2, mapMgr.getMapDimensions().y - cam.viewportHeight / 2);
            //cam.update();

            mapMgr.setMapChanged(false);
        }

        mapRenderer.setView(cam);


        //Draw bottom map



        //Draw all sprites
        batcher.setProjectionMatrix(cam.combined); //Super important line!
        //shapeRenderer.setProjectionMatrix(cam.combined); //Super important line!
        //batcher.begin();

        //Draw Player
        //player.render(delta, runTime, batcher);


        //batcher.end();

       /* Gdx.gl.glEnable(Gdx.gl20.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);*/
        //shapeRenderer.setProjectionMatrix(cam.combined);
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(1, 1, 1, 0.5f));
        shapeRenderer.circle(player.getX(), player.getY(), 25);
        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl20.GL_BLEND);*/



        //Draw top map
        Gdx.gl.glEnable(Gdx.gl20.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);

        /*tileLayer = (TiledMapTileLayer) terrain.getTiledMap().getLayers().get(1);
        cell = tileLayer.getCell((int)Math.floor((double)pointer.getX()/(double) terrain.getSingleTileWidth()),(int)Math.floor((double)pointer.getY()/(double) terrain.getSingleTileHeight()));*/

        //Ground level stuff
        mapRenderer.getBatch().begin();
        TiledMapTileLayer groundMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.GROUND_LAYER);
        if( groundMapLayer != null ) {
            mapRenderer.renderTileLayer(groundMapLayer);
        }

        TiledMapTileLayer backgroundMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.BACKGROUND_LAYER);
        if( backgroundMapLayer != null ){
            mapRenderer.renderTileLayer(backgroundMapLayer);
        }

        TiledMapTileLayer decorationMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.DECORATION_LAYER);
        if( decorationMapLayer != null ){
            mapRenderer.renderTileLayer(decorationMapLayer);
        }
        mapRenderer.getBatch().end();

        //Player
        player.update(mapMgr, batcher, delta);

        //Top level stuff
        mapRenderer.getBatch().begin();
        TiledMapTileLayer topMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.TOP_LAYER);
        if( topMapLayer != null ){
            mapRenderer.renderTileLayer(topMapLayer);
        }
        mapRenderer.getBatch().end();



        //mapMgr.updateCurrentMapEntities(mapMgr, mapRenderer.getBatch(), delta);

        //terrain.getTiledMapRenderer().render(new int[]{3});
        Gdx.gl.glDisable(Gdx.gl20.GL_BLEND);

        //Draw HUD
        //hud.render(delta, runTime, batcher, shapeRenderer, font, cam);


        /*** *************************************
         *
         *  The code below is for debugging only
         *
         * **************************************/


        //Used to graphically debug boundingboxes
        /*Rectangle rect = _player.getCurrentBoundingBox();
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(rect.getX() * Map.UNIT_SCALE , rect.getY() * Map.UNIT_SCALE, rect.getWidth() * Map.UNIT_SCALE, rect.getHeight()*Map.UNIT_SCALE);
        shapeRenderer.end();*/


        /*Gdx.gl.glLineWidth(2);

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
            //for(int i = 0; i < 9; i++){
            //    font.draw(batcher, lh.playerNineTile(player, terrain).get(i).getTile().getId() + "", lh.playerNineTileRect(player, terrain).get(i).x + 2, lh.playerNineTileRect(player, terrain).get(i).y + 8);
            //}

            //Pointerrect
            font.draw(batcher, lh.pointerTile(pointer, terrain).getTile().getId() + "", lh.pointerTileRect(pointer, terrain).x + 2, lh.pointerTileRect(pointer, terrain).y + 8);

        batcher.end();*/



    }

    /*public OrthographicCamera getCamera(){
        return cam;
    }*/

    /*private void initGameObjects() {
        //player = myWorld.getPlayer();
        //terrain = myWorld.getTerrain();
        //pointer = myWorld.getPointer();
        //hud = new HeadUpDisplay(player);
    }*/

    /*private void initAssets() {
        //playerAnimation = AssetLoader.playerAnimation;
    }*/
}
