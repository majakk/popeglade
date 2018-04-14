package studio.coldstream.popeglade.userinterfaces;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import studio.coldstream.popeglade.gameobjects.entities.Entity;
import studio.coldstream.popeglade.profiles.ProfileManager;
import studio.coldstream.popeglade.profiles.ProfileObserver;

public class PlayerHUD implements Screen, ProfileObserver{
    private Stage stage;
    private Viewport viewport;

    private StatusUI statusUI;

    public PlayerHUD(Camera camera, Entity player){
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport);

        statusUI = new StatusUI();

        stage.addActor(statusUI);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta){
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void onNotify(ProfileManager profileManager, ProfileEvent event) {

    }

    public Stage getStage() {
        return stage;
    }
}
