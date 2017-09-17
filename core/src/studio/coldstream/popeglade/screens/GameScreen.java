package studio.coldstream.popeglade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import studio.coldstream.popeglade.gamehelpers.InputHandler;
import studio.coldstream.popeglade.gameworld.GameRenderer;
import studio.coldstream.popeglade.gameworld.GameWorld;

/**
 * Created by Scalar on 01/08/2017.
 */

public class GameScreen implements Screen {
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;

    public GameScreen() {
        Gdx.app.log("GameScreen", "Attached");

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = Gdx.graphics.getWidth(); //Set this to the wanted width
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointX = (int) (gameWidth / 2);
        int midPointY = (int) (gameHeight / 2);

        world = new GameWorld(midPointX, midPointY);
        //Gdx.input.setInputProcessor(new InputHandler(world));
        renderer = new GameRenderer(world, midPointX, midPointY);

        Gdx.input.setInputProcessor(new InputHandler(world.getPlayer()));
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(delta,runTime);
        //FPS check
        //Gdx.app.log("GameScreen FPS", (1/delta) + "");
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {
        // Leave blank
    }
}
