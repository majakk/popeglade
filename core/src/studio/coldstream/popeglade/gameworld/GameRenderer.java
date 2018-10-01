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
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;


import java.util.Comparator;

import javafx.scene.effect.BlendMode;
import studio.coldstream.popeglade.gamehelpers.LocationHandler;
import studio.coldstream.popeglade.gameobjects.entities.Component;
import studio.coldstream.popeglade.gameobjects.entities.Entity;
import studio.coldstream.popeglade.gameobjects.entities.EntityConfig;
import studio.coldstream.popeglade.gameobjects.entities.EntityFactory;
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
    private Array<Entity> mapEntities;
    private final yPositionComparator entityComparator;

    private OrthogonalTiledMapRenderer mapRenderer = null;

    private InputMultiplexer multiplexer;

    private LocationHandler lh;

    public GameRenderer(MainGameScreen screen) {
        Gdx.app.log(TAG, "Attached");

        json = new Json();

        myScreen = screen;
        myScreen.setupViewport(MainGameScreen.VIEWPORT_WIDTH, MainGameScreen.VIEWPORT_HEIGHT); //How many tiles wide and high to fit

        Gdx.app.log(TAG, "ViewPort: " + myScreen.getViewport().x + ":" + myScreen.getViewport().y);

        mapMgr = screen.getWorld().getMapMgr();
        player = screen.getWorld().getPlayerEntity();
        pointer = screen.getWorld().getPointerEntity();

        this.entityComparator = new yPositionComparator();

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, myScreen.getPhysicalViewport().x, myScreen.getPhysicalViewport().y);

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

        //Input devices and their target processing classes
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(playerHUD.getStage());
        multiplexer.addProcessor(new PlayerInputComponent());
        multiplexer.addProcessor(new PointerInputComponent());
        Gdx.input.setInputProcessor(multiplexer);

        mapEntities = mapMgr.getCurrentMapEntities();
        mapEntities.add(player);
        lh = new LocationHandler();

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

        mapEntities.sort(entityComparator);

        //Gdx.app.log("GameRenderer", "render");
        //pointer.update(mapMgr, batcher, delta);
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

        batcher.setProjectionMatrix(cam.combined); //Super important line!

        Gdx.gl.glEnable(Gdx.gl20.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);

        //Map
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

        //Update and Draw mapEntities (including player)
        mapMgr.updateCurrentMapEntities(mapMgr, batcher, delta);

        //Player
        //player.update(mapMgr, batcher, delta);

        //Top level stuff
        mapRenderer.getBatch().begin();

            TiledMapTileLayer topMapLayer = (TiledMapTileLayer)mapMgr.getCurrentTiledMap().getLayers().get(Map.TOP_LAYER);
            if( topMapLayer != null ){
                mapRenderer.renderTileLayer(topMapLayer);
            }

        mapRenderer.getBatch().end();

        //Debug Lines Rendering
        if(MainGameScreen.isCollisionGridEnabled()) {
            MapObjects objects = mapMgr.getCollisionLayer().getObjects();
            for (MapObject object : objects) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
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
            for (Entity entity : mapEntities){
                Array<Rectangle> rect = entity.getCurrentBoundingBoxes();
                for (Rectangle r : rect) {
                    shapeRenderer.setProjectionMatrix(mapRenderer.getBatch().getProjectionMatrix());
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    if (entity.getEntityID() == EntityFactory.EntityType.PLAYER.toString()) {
                        shapeRenderer.setColor(Color.BLUE);
                    } else {
                        shapeRenderer.setColor(Color.RED);
                    }
                    shapeRenderer.rect(r.getX() * Map.UNIT_SCALE, r.getY() * Map.UNIT_SCALE, r.getWidth() * Map.UNIT_SCALE, r.getHeight() * Map.UNIT_SCALE);
                    shapeRenderer.end();
                }
            }

            Rectangle rect = lh.actionTileRect(player,mapMgr);
            shapeRenderer.setProjectionMatrix(mapRenderer.getBatch().getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.rect(
                    rect.x,
                    rect.y ,
                    rect.width,
                    rect.height);
            shapeRenderer.end();


        }

        playerHUD.render(delta);

        //Pointer (Top of top)
        pointer.update(mapMgr, batcher, delta);

    }

    private static class yPositionComparator implements Comparator<Entity> {
        //private final Entity yCoordinate;

        private yPositionComparator() {
            //this.yCoordinate = Entity;
        }

        @Override
        public int compare(Entity o1, Entity o2) {
            if (o1 == o2) {
                return 0;
            } else if (o1 == null) {
                return -1;
            } else if (o2 == null) {
                return 1;
            }

            return o1.getCurrentPosition().y > o2.getCurrentPosition().y ? -1 : 1;
        }
    }


    public PlayerHUD getPlayerHUD(){
        return playerHUD;
    }

    public OrthographicCamera getCamera() {
        return cam;
    }
}
