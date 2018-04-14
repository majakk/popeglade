package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PointerInputComponent extends InputComponent {
    private static final String TAG = PointerInputComponent.class.getSimpleName();
    private Vector2 lastMouseCoordinates;

    public PointerInputComponent(){
        Gdx.app.debug(TAG, "Attached Pointer Controller" );
        this.lastMouseCoordinates = new Vector2(0, 0);
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if( string.length == 0 ) return;

        //Specifically for messages with 1 object payload
        /*if( string.length == 2 ) {
            if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            }
        }*/
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void update(Entity entity, float delta){
        //Gdx.app.debug(TAG, "Mouse pressed : (" + keys.toString());
        entity.setCurrentPosition(Gdx.input.getX(),Gdx.input.getY());

        //Mouse input
        if( mouseButtons.get(Mouse.SELECT)) {
            lastMouseCoordinates.set(Gdx.input.getX(), Gdx.input.getY()); //Something fishy since we need this here again!

            Gdx.app.debug(TAG, "Mouse LEFT click at : (" + lastMouseCoordinates.x + "," + lastMouseCoordinates.y + ")" ); //Something fishy

            entity.sendMessage(MESSAGE.INIT_SELECT_ENTITY, json.toJson(lastMouseCoordinates));
            mouseButtons.put(Mouse.SELECT, false);
        }

    }



    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if( button == Input.Buttons.LEFT || button == Input.Buttons.RIGHT ){
            //Gdx.app.debug(TAG, "MOUSE DOWN........: (" + screenX + "," + screenY + ")" );
            this.setClickedMouseCoordinates(screenX, screenY);
            //lastMouseCoordinates.set(screenX,screenY);
        }

        //left is selection, right is context menu
        if( button == Input.Buttons.LEFT){
            this.selectMouseButtonPressed(screenX, screenY);
        }
        if( button == Input.Buttons.RIGHT){
            this.doActionMouseButtonPressed(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //left is selection, right is context menu
        if( button == Input.Buttons.LEFT){
            this.selectMouseButtonReleased(screenX, screenY);
        }
        if( button == Input.Buttons.RIGHT){
            this.doActionMouseButtonReleased(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void setClickedMouseCoordinates(int x, int y){
        //lastMouseCoordinates = new Vector2(0,0);
        lastMouseCoordinates.set(x,y);
        //this.lastMouseCoordinates.add(x, y, 0.0f);
        //Gdx.app.debug(TAG, "MOUSE DOWN SET........: (" + lastMouseCoordinates.x + "," + y + ")" );
    }

    public void selectMouseButtonPressed(int x, int y){
        mouseButtons.put(Mouse.SELECT, true);
    }

    public void doActionMouseButtonPressed(int x, int y){
        mouseButtons.put(Mouse.DOACTION, true);
    }

    //Releases
    public void selectMouseButtonReleased(int x, int y){
        mouseButtons.put(Mouse.SELECT, false);
    }

    public void doActionMouseButtonReleased(int x, int y){
        mouseButtons.put(Mouse.DOACTION, false);
    }

}
