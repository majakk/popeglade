package studio.coldstream.popeglade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;

import studio.coldstream.popeglade.gameworld.GameRenderer;
import studio.coldstream.popeglade.gameworld.GameWorld;
import studio.coldstream.popeglade.profiles.ProfileManager;

public class MainGameScreen implements Screen {
    private static final String TAG = MainGameScreen.class.getSimpleName();

    public static final int VIEWPORT_WIDTH = 28;
    public static final int VIEWPORT_HEIGHT = 18;
    private int firstResize = 2;

    private static boolean COLLISION_GRID_ENABLED;

    public static class VIEWPORT {
        static float viewportWidth;
        static float viewportHeight;
        static float virtualWidth;
        static float virtualHeight;
        static float physicalWidth;
        static float physicalHeight;
        static float aspectRatio;
    }

    public enum GameState {
        PAUSED, MENU, READY, RUNNING, GAMEOVER, HIGHSCORE, SAVING, LOADING
    }

    private static GameWorld world;
    private static GameRenderer renderer;
    private static GameState gameState;
    private float runTime;

    public MainGameScreen() {
        Gdx.app.log(TAG, "Attached");

        //this.setupViewport(MainGameScreen.VIEWPORT_WIDTH, MainGameScreen.VIEWPORT_HEIGHT);
        //this.resize((int)VIEWPORT.physicalWidth, (int)VIEWPORT.physicalHeight);

        setGameState(GameState.RUNNING);

    }

    public static void setGameState(GameState gs){
        switch(gs){
            case RUNNING:
                gameState = GameState.RUNNING;
                break;
            case LOADING:
                ProfileManager.getInstance().loadProfile();
                gameState = GameState.RUNNING;
                break;
            case SAVING:
                ProfileManager.getInstance().saveProfile();
                gameState = GameState.PAUSED;
                break;
            case PAUSED:
                if( gameState == GameState.PAUSED ){
                    gameState = GameState.RUNNING;
                }else if( gameState == GameState.RUNNING ){
                    gameState = GameState.PAUSED;
                }
                break;
            case GAMEOVER:
                gameState = GameState.GAMEOVER;
                break;
            default:
                gameState = GameState.RUNNING;
                break;
        }

    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(delta,runTime);
        //FPS check
        //Gdx.app.log("GameScreen FPS", (1/delta) + "");
    }

    public void setupViewport(int width, int height){
        //Make the viewport a percentage of the total display area
        VIEWPORT.virtualWidth = width;
        VIEWPORT.virtualHeight = height;

        //Current viewport dimension
        VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
        VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;

        //Dimension of display in pixels
        VIEWPORT.physicalWidth = Gdx.graphics.getWidth();
        VIEWPORT.physicalHeight = Gdx.graphics.getHeight();

        //Aspect ratio of current viewport
        VIEWPORT.aspectRatio = (VIEWPORT.virtualWidth / VIEWPORT.virtualHeight);

        //update viewport if there could be skewing
        if( VIEWPORT.physicalWidth / VIEWPORT.physicalHeight >= VIEWPORT.aspectRatio){
            //Letterbox left and right
            VIEWPORT.viewportWidth = VIEWPORT.viewportHeight * (VIEWPORT.physicalWidth/VIEWPORT.physicalHeight);
            VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;
        }else{
            //letterbox above and below
            VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
            VIEWPORT.viewportHeight = VIEWPORT.viewportWidth * (VIEWPORT.physicalHeight/VIEWPORT.physicalWidth);
        }

        Gdx.app.debug(TAG, "SetupViewport: virtual: (" + VIEWPORT.virtualWidth + "," + VIEWPORT.virtualHeight + ")" );
        Gdx.app.debug(TAG, "SetupViewport: viewport: (" + VIEWPORT.viewportWidth + "," + VIEWPORT.viewportHeight + ")" );
        Gdx.app.debug(TAG, "SetupViewport: physical: (" + VIEWPORT.physicalWidth + "," + VIEWPORT.physicalHeight + ")" );
    }

    public static GameWorld getWorld(){
        return world;
    }

    public static GameRenderer getRenderer(){
        return renderer;
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "Resizing: " + width + ":" + height);




        /*float ar = VIEWPORT.aspectRatio;
        //Gdx.graphics.setResizable(false);
        if((width/height) == ar) {
            this.setupViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
            //Gdx.graphics.setWindowedMode(width, (int) (width / ar));
            Gdx.graphics.setWindowedMode((int)VIEWPORT.physicalWidth, (int)VIEWPORT.physicalHeight);
            //
        }
        else {
            //Gdx.graphics.setWindowedMode(width, height);
            this.setupViewport(MainGameScreen.VIEWPORT_HEIGHT * (width/height), MainGameScreen.VIEWPORT_HEIGHT);
            //Gdx.graphics.setWindowedMode(VIEWPORT_HEIGHT * (width/height), height);


        }
        //
        firstResize--;*/

        Gdx.graphics.setWindowedMode(width, height);
        this.setupViewport(MainGameScreen.VIEWPORT_HEIGHT * (width/height), MainGameScreen.VIEWPORT_HEIGHT);
        renderer.getCamera().setToOrtho(false, VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);
        renderer.getPlayerHUD().resize((int) VIEWPORT.physicalWidth, (int) VIEWPORT.physicalHeight);
        //Gdx.graphics.setResizable(true);
        //renderer.getPlayerHUD().resize(width, height);


    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "Show called");
        world = new GameWorld();
        renderer = new GameRenderer(this);
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "Hide called");

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "Pause called");
        //setGameState(GameState.SAVING); //ToDo: This line breaks pause() currently
        renderer.getPlayerHUD().pause();
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "Resume called");
        renderer.getPlayerHUD().resume();
    }

    @Override
    public void dispose() {
        // Leave blank
    }

    public Vector2 getPhysicalViewport(){
        return new Vector2(VIEWPORT.physicalWidth, VIEWPORT.physicalHeight);
    }

    public Vector2 getViewport(){
        return new Vector2(VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static boolean isCollisionGridEnabled() {
        return COLLISION_GRID_ENABLED;
    }

    public static void setCollisionGridEnabled(boolean collisionGridEnabled) {
        COLLISION_GRID_ENABLED = collisionGridEnabled;
        Gdx.app.log(TAG, "Grid: " + collisionGridEnabled);
    }
}


