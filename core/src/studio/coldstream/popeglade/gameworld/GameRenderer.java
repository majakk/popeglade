package studio.coldstream.popeglade.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;


import studio.coldstream.popeglade.gamehelpers.LocationHandler;
import studio.coldstream.popeglade.gameobjects.entities.Component;
import studio.coldstream.popeglade.gameobjects.entities.Entity;
import studio.coldstream.popeglade.gameobjects.entities.PlayerInputComponent;
import studio.coldstream.popeglade.gameobjects.entities.PointerInputComponent;
import studio.coldstream.popeglade.gameobjects.maps.Map;
import studio.coldstream.popeglade.gameobjects.maps.MapManager;
import studio.coldstream.popeglade.screens.MainGameScreen;
import studio.coldstream.popeglade.userinterfaces.PlayerHUD;

/**
 * Created by Scalar on 01/08/2017.
 */

public class GameRenderer {
    private static final String TAG = GameRenderer.class.getSimpleName();

    private MainGameScreen myScreen;

    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;

    private OrthographicCamera hudCamera;
    private static PlayerHUD playerHUD;

    private Json json;

    //Game Assets
    private static MapManager mapMgr;
    private static Entity player;
    private static Entity pointer;
    private OrthogonalTiledMapRenderer mapRenderer = null;

    private InputMultiplexer multiplexer;

    //private LocationHandler lh;

    public GameRenderer(MainGameScreen screen) {
        Gdx.app.log(TAG, "Attached");

        json = new Json();

        myScreen = screen;
        myScreen.setupViewport(MainGameScreen.VIEWPORT_WIDTH, MainGameScreen.VIEWPORT_HEIGHT); //How many tiles wide and high to fit

        Gdx.app.log(TAG, "ViewPort: " + myScreen.getViewport().x + ":" + myScreen.getViewport().y);

        mapMgr = screen.getWorld().getMapMgr();
        player = screen.getWorld().getPlayerEntity();
        pointer = screen.getWorld().getPointerEntity();

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, myScreen.getPhysicalViewport().x, myScreen.getPhysicalViewport().y);
        //hudCamera.setToOrtho(false, myScreen.getViewport().x, myScreen.getViewport().y);

        playerHUD = new PlayerHUD(hudCamera, player);
        hudCamera.update();


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

        //playerHUD.getStage().getBatch().setProjectionMatrix(cam.combined); //Somehow the icons do not position themselves properly

        //Input devices and their target processing classes
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(playerHUD.getStage());
        multiplexer.addProcessor(new PlayerInputComponent());
        multiplexer.addProcessor(new PointerInputComponent());
        Gdx.input.setInputProcessor(multiplexer);



        //lh = new LocationHandler();

        //Gdx.app.log(TAG, "MapSize X: " + mapMgr.getMapDimensions().x);
    }

    public void render(float delta, float runTime) {
        if( myScreen.getGameState() == MainGameScreen.GameState.GAMEOVER ){
            //myScreen.setScreen(game.getScreenType(BludBourne.ScreenType.GameOver));
        }

        if( myScreen.getGameState() == MainGameScreen.GameState.PAUSED ){
            player.updateInput(delta);
            playerHUD.render(delta);
            return;
        }

        //Gdx.app.log("GameRenderer", "render");
        //pointer.update(delta, player, mapMgr);
        //Clear and draw background
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.8f, 1);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glEnable(Gdx.gl20.GL_BLEND);

        Gdx.gl.glLineWidth(2);

        mapRenderer.setView(cam);

        mapRenderer.getBatch().enableBlending();
        mapRenderer.getBatch().setBlendFunction(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);

        //Constrain the camera to the map area
        cam.position.x = MathUtils.clamp(mapMgr.getPlayer().getCurrentPosition().x, cam.viewportWidth / 2, mapMgr.getMapDimensions().x * Map.UNIT_SCALE - cam.viewportWidth / 2);
        cam.position.y = MathUtils.clamp(mapMgr.getPlayer().getCurrentPosition().y, cam.viewportHeight / 2, mapMgr.getMapDimensions().y * Map.UNIT_SCALE - cam.viewportHeight / 2);
        cam.update();

        //batcher.setProjectionMatrix(cam.combined); //Super important line!

        if( mapMgr.hasMapChanged() ) {
            mapRenderer.setMap(mapMgr.getCurrentTiledMap());
            player.sendMessage(Component.MESSAGE.INIT_START_POSITION, json.toJson(mapMgr.getPlayerStartUnitScaled()));
            //Gdx.app.log(TAG, "Mapsize X: " + mapMgr.getMapDimensions().x + ", cam.viewportWidth: " + cam.viewportWidth + ", CurrentPosX: " + mapMgr.getPlayer().getCurrentPosition().x);
            //cam.position.x = MathUtils.clamp(mapMgr.getPlayer().getCurrentPosition().x, cam.viewportWidth / 2, mapMgr.getMapDimensions().x - cam.viewportWidth / 2);
            //cam.position.y = MathUtils.clamp(mapMgr.getPlayer().getCurrentPosition().y, cam.viewportHeight / 2, mapMgr.getMapDimensions().y - cam.viewportHeight / 2);
            cam.update();

            mapMgr.setMapChanged(false);
            //playerHUD.addTransitionToScreen();
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

            TiledMapTileLayer backgroundMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.BACKGROUND_LAYER);
            if( backgroundMapLayer != null ){
                mapRenderer.renderTileLayer(backgroundMapLayer);
            }

            TiledMapTileLayer groundMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.GROUND_LAYER);
            if( groundMapLayer != null ) {
                mapRenderer.renderTileLayer(groundMapLayer);
            }

            TiledMapTileLayer decorationMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.DECORATION_LAYER);
            if( decorationMapLayer != null ){
                mapRenderer.renderTileLayer(decorationMapLayer);
            }

            TiledMapTileLayer wallsMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.WALLS_LAYER);
            if( wallsMapLayer != null ){
                mapRenderer.renderTileLayer(wallsMapLayer);
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

        pointer.update(mapMgr, batcher, delta);

        //mapMgr.updateCurrentMapEntities(mapMgr, mapRenderer.getBatch(), delta);
        if(MainGameScreen.isCollisionGridEnabled()) {
            MapObjects objects = mapMgr.getCollisionLayer().getObjects();
            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    // do something with rect...
                    shapeRenderer.setProjectionMatrix(mapRenderer.getBatch().getProjectionMatrix());
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.RED);
                    shapeRenderer.rect(rect.getX() * Map.UNIT_SCALE, rect.getY() * Map.UNIT_SCALE, rect.getWidth() * Map.UNIT_SCALE, rect.getHeight() * Map.UNIT_SCALE);
                    shapeRenderer.end();
                }
            /*else if (object instanceof PolygonMapObject) {
                Polygon polygon = ((PolygonMapObject) object).getPolygon();
                // do something with polygon...
            }
            else if (object instanceof PolylineMapObject) {
                Polyline chain = ((PolylineMapObject) object).getPolyline();
                // do something with chain...
            }
            else if (object instanceof CircleMapObject) {
                Circle circle = ((CircleMapObject) object).getCircle();
                // do something with circle...
            }*/
            }
        }

        playerHUD.render(delta);




        /*** *************************************
         *
         *  The code below is for debugging only
         *
         * **************************************/


        //Used to graphically debug boundingboxes
        /*Rectangle rect = player.getCurrentBoundingBox();
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

        shapeRenderer.end();*/

        //Draw pointer rect
        /*shapeRenderer.setProjectionMatrix(cam.combined); //Super important line!
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            shapeRenderer.setColor(0.3f,1,0.3f,0.2f);
            //shapeRenderer.rect(lh.pointerTileRect(pointer, terrain).x, lh.pointerTileRect(pointer, terrain).y, lh.pointerTileRect(pointer, terrain).width, lh.pointerTileRect(pointer, terrain).height);
            shapeRenderer.rect(lh.pointerTileRect(pointer, mapMgr).x, lh.pointerTileRect(pointer, mapMgr).y, lh.pointerTileRect(pointer, mapMgr).width, lh.pointerTileRect(pointer, mapMgr).height);

        shapeRenderer.end();*/

        //Draw values in rects
        /*batcher.begin();

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

    public PlayerHUD getPlayerHUD(){
        return playerHUD;
    }

    public OrthographicCamera getCamera() {
        return cam;
    }
}
