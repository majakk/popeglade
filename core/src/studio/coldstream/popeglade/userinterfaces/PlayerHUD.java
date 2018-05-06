package studio.coldstream.popeglade.userinterfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragScrollListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import studio.coldstream.popeglade.gameobjects.entities.Component;
import studio.coldstream.popeglade.gameobjects.entities.Entity;
import studio.coldstream.popeglade.gameobjects.items.InventoryItem;
import studio.coldstream.popeglade.gameobjects.items.InventoryItemFactory;
import studio.coldstream.popeglade.gameobjects.maps.Map;
import studio.coldstream.popeglade.profiles.ProfileManager;
import studio.coldstream.popeglade.profiles.ProfileObserver;
import studio.coldstream.popeglade.screens.MainGameScreen;

public class PlayerHUD implements Screen, ProfileObserver{
    private static final String TAG = PlayerHUD.class.getSimpleName();

    private Entity player;
    private Camera cam;

    private Stage stage;
    private Viewport viewport;

    private StatusUI statusUI;
    private InventoryBarUI inventoryBarUI;
    private InventoryUI inventoryUI;
    private InventoryItemFactory inventoryItemFactory;

    public PlayerHUD(Camera c, Entity p){
        player = p;
        cam = c;

        viewport = new ScreenViewport(cam);
        stage = new Stage(viewport);
        inventoryItemFactory = InventoryItemFactory.getInstance();

        statusUI = new StatusUI();
        statusUI.setPosition(10, cam.viewportHeight - statusUI.getHeight() - 10);
        statusUI.setVisible(true);
        statusUI.setMovable(true);
        statusUI.setKeepWithinStage(false);

        inventoryBarUI = new InventoryBarUI();
        inventoryBarUI.setMovable(true);
        inventoryBarUI.setVisible(true);
        inventoryBarUI.setKeepWithinStage(false);

        inventoryUI = new InventoryUI();
        inventoryUI.setPosition(
                (cam.viewportWidth - inventoryUI.getWidth()) / 2,
                (cam.viewportHeight - inventoryUI.getHeight()) / 2);
        inventoryUI.setMovable(false);
        inventoryUI.setVisible(false);
        inventoryUI.setKeepWithinStage(false);

        stage.addActor(statusUI);
        stage.addActor(inventoryBarUI);
        stage.addActor(inventoryUI);

        statusUI.validate();
        inventoryBarUI.validate();
        inventoryUI.validate();

        //Listeners
        ImageButton inventoryButton = statusUI.getInventoryButton();
        inventoryButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                inventoryUI.setVisible(inventoryUI.isVisible() ? false : true);

                Gdx.app.log(TAG, event.toString() + "");
                //inventoryItemFactory.testAllItemLoad();
                //inventoryUI.getInventoryTable()..getCells().get(0)add(inventoryItemFactory.getInventoryItem(InventoryItem.ItemTypeID.ARMOR01)); //Kind of works
                //inventoryUI.populateInventory(inventoryUI.getInventoryTable(), new Array<InventoryItemLocation>(2), new DragAndDrop(), "", true);
            }
        });

        //Handling clicks in barSlotTable
        final Table barSlotTable = inventoryBarUI.getBarSlotTable();

        barSlotTable.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, event.toString() + " Bubble");
            }

            /*public boolean scrolled(InputEvent event, float x, float y, int amount){
                Gdx.app.log(TAG, event.toString() + " Bobble");
                return true;
            }*/
        });

        //This should handle scrolling properly!
        stage.addListener(new InputListener() {
            public boolean scrolled(InputEvent event, float x, float y, int amount){
                Gdx.app.log(TAG, event.toString() + " Bobble");
                return true;
            }
        });


        /*barSlotTable.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                //inventoryBarUI.setVisible(inventoryBarUI.isVisible() ? false : true);

                Gdx.app.log(TAG, event.toString() + "HUDDLE");
                //inventoryItemFactory.testAllItemLoad();
                //inventoryUI.getInventoryTable()..getCells().get(0)add(inventoryItemFactory.getInventoryItem(InventoryItem.ItemTypeID.ARMOR01)); //Kind of works
                //inventoryUI.populateInventory(inventoryUI.getInventoryTable(), new Array<InventoryItemLocation>(2), new DragAndDrop(), "", true);
            }

        });*/
    }

    @Override
    public void show() {

    }

    private void updateWindowsPositions(float delta) {
        float yOffset;
        if (player.getCurrentPosition().y / Map.UNIT_SCALE > cam.position.y - 0.5f * (cam.viewportHeight / 2))
            yOffset = cam.position.y - 0.8f * (cam.viewportHeight / 2) - 16;
        else
            yOffset = cam.position.y + 0.8f * (cam.viewportHeight / 2) - 32;

        //dinventoryBarUI.getTitleLabel().setText("fps" + Math.round(1 / delta) );
        statusUI.setPosition(10, cam.viewportHeight - statusUI.getHeight() - 10);

        inventoryBarUI.setPosition(viewport.getScreenWidth() / 2 - 5*32, yOffset);
    }

    @Override
    public void render(float delta){
        updateWindowsPositions(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        updateWindowsPositions(1);
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
