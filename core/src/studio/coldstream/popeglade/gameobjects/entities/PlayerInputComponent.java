package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import studio.coldstream.popeglade.screens.MainGameScreen;

public class PlayerInputComponent extends InputComponent {
    private final static String TAG = PlayerInputComponent.class.getSimpleName();
    private Vector2 lastMouseCoordinates;

    public PlayerInputComponent(){
        Gdx.app.debug(TAG, "Attached Controller!!!" );
        this.lastMouseCoordinates = new Vector2(0, 0);
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if( string.length == 0 ) return;

        //Specifically for messages with 1 object payload
        if( string.length == 2 ) {
            if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            }
        }
    }

    @Override
    public void dispose(){
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void update(Entity entity, float delta){
        //Gdx.app.debug(TAG, "Key pressed : (" + keys.toString());
        //Keyboard input
        if(keys.get(Keys.PAUSE)) {
            MainGameScreen.setGameState(MainGameScreen.GameState.PAUSED);
            pauseReleased();

        }else if( keys.get(Keys.LEFT) && keys.get(Keys.UP)){
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.LEFT_UP));
        }else if( keys.get(Keys.RIGHT) && keys.get(Keys.UP)){
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.RIGHT_UP));
        }else if( keys.get(Keys.LEFT) && keys.get(Keys.DOWN)){
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.LEFT_DOWN));
        }else if(keys.get(Keys.RIGHT) && keys.get(Keys.DOWN)){
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.RIGHT_DOWN));

        }else if( keys.get(Keys.LEFT)){
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.LEFT));
        }else if( keys.get(Keys.RIGHT)){
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.RIGHT));
        }else if( keys.get(Keys.UP)){
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.UP));
        }else if(keys.get(Keys.DOWN)){
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
            entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.DOWN));

        }else if(keys.get(Keys.QUIT)) {
            quitReleased();
            Gdx.app.exit();

        }else{
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.IDLE));
            if( currentDirection == null ){
                entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.DOWN));
            }
        }

        //Mouse input
        /*if( mouseButtons.get(Mouse.SELECT)) {
            lastMouseCoordinates.set(Gdx.input.getX(), Gdx.input.getY()); //Something fishy since we need this here again!

            //Gdx.app.debug(TAG, "Mouse LEFT click at : (" + lastMouseCoordinates.x + "," + lastMouseCoordinates.y + ")" ); //Something fishy

            entity.sendMessage(MESSAGE.INIT_SELECT_ENTITY, json.toJson(lastMouseCoordinates));
            mouseButtons.put(Mouse.SELECT, false);
        }*/


    }

    @Override
    public boolean keyDown(int keycode) {
        if( keycode == Input.Keys.LEFT || keycode == Input.Keys.A){
            this.leftPressed();
        }
        if( keycode == Input.Keys.RIGHT || keycode == Input.Keys.D){
            this.rightPressed();
        }
        if( keycode == Input.Keys.UP || keycode == Input.Keys.W){
            this.upPressed();
        }
        if( keycode == Input.Keys.DOWN || keycode == Input.Keys.S){
            this.downPressed();
        }
        if( keycode == Input.Keys.Q){
            this.quitPressed();
        }
        if( keycode == Input.Keys.P ){
            this.pausePressed();
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if( keycode == Input.Keys.LEFT || keycode == Input.Keys.A){
            this.leftReleased();
        }
        if( keycode == Input.Keys.RIGHT || keycode == Input.Keys.D){
            this.rightReleased();
        }
        if( keycode == Input.Keys.UP || keycode == Input.Keys.W ){
            this.upReleased();
        }
        if( keycode == Input.Keys.DOWN || keycode == Input.Keys.S){
            this.downReleased();
        }
        if( keycode == Input.Keys.Q){
            this.quitReleased();
        }
        if( keycode == Input.Keys.P ){
            this.pauseReleased();
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        /*if( button == Input.Buttons.LEFT || button == Input.Buttons.RIGHT ){
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
        return true;*/
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //left is selection, right is context menu
        /*if( button == Input.Buttons.LEFT){
            this.selectMouseButtonReleased(screenX, screenY);
        }
        if( button == Input.Buttons.RIGHT){
            this.doActionMouseButtonReleased(screenX, screenY);
        }
        return true;*/
        return false;
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

    //Key presses
    public void leftPressed(){
        keys.put(Keys.LEFT, true);
    }

    public void rightPressed(){
        keys.put(Keys.RIGHT, true);
    }

    public void upPressed(){
        keys.put(Keys.UP, true);
    }

    public void downPressed(){
        keys.put(Keys.DOWN, true);
    }
    public void quitPressed(){
        keys.put(Keys.QUIT, true);
    }

    public void pausePressed() {
        keys.put(Keys.PAUSE, true);
    }

    /*public void setClickedMouseCoordinates(int x, int y){
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
    }*/

    //Releases

    public void leftReleased(){
        keys.put(Keys.LEFT, false);
    }

    public void rightReleased(){
        keys.put(Keys.RIGHT, false);
    }

    public void upReleased(){
        keys.put(Keys.UP, false);
    }

    public void downReleased(){
        keys.put(Keys.DOWN, false);
    }

    public void quitReleased(){
        keys.put(Keys.QUIT, false);
    }

    public void pauseReleased() { keys.put(Keys.PAUSE, false);}

    /*public void selectMouseButtonReleased(int x, int y){
        mouseButtons.put(Mouse.SELECT, false);
    }

    public void doActionMouseButtonReleased(int x, int y){
        mouseButtons.put(Mouse.DOACTION, false);
    }*/

    public static void clear(){
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.QUIT, false);
    }
}