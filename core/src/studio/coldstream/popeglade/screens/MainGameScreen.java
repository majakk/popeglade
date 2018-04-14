package studio.coldstream.popeglade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;

import studio.coldstream.popeglade.gameworld.GameRenderer;
import studio.coldstream.popeglade.gameworld.GameWorld;
import studio.coldstream.popeglade.profiles.ProfileManager;

public class MainGameScreen implements Screen {
    private static final String TAG = MainGameScreen.class.getSimpleName();

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

    private GameWorld world;
    private GameRenderer renderer;
    private static GameState gameState;
    private float runTime;

    public MainGameScreen() {
        Gdx.app.log(TAG, "Attached");

        setGameState(GameState.RUNNING);

    }

    public static void setGameState(GameState gameState){
        switch(gameState){
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

    public GameWorld getWorld(){
        return world;
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resizing");
        float ar = VIEWPORT.aspectRatio;
        Gdx.graphics.setResizable(false);
        if((width / height) != ar)
            Gdx.graphics.setWindowedMode(width, (int)(width / ar));
        else
            Gdx.graphics.setWindowedMode((int)(height * ar), height);
        Gdx.graphics.setResizable(true);

        //renderer.getHUD().resize((int) VIEWPORT.physicalWidth, (int) VIEWPORT.physicalHeight);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show called");

        setupViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        world = new GameWorld();
        renderer = new GameRenderer(this);
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume called");
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
}


