package studio.coldstream.popeglade.gameobjects.entities;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

public abstract class InputComponent implements Component, InputProcessor {
    private static final String TAG = GraphicsComponent.class.getSimpleName();

    protected Entity.Direction currentDirection = null;
    protected Entity.State currentState = null;
    protected Json json;

    protected enum Keys {
        LEFT, RIGHT, UP, DOWN, QUIT, PAUSE
    }

    protected enum Mouse {
        SELECT, DOACTION, SCROLL
    }

    protected static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();

    protected static Map<Mouse, Boolean> mouseButtons = new HashMap<Mouse, Boolean>();

    //Initialize HashMaps
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.QUIT, false);
        keys.put(Keys.PAUSE, false);
    }

    static {
        mouseButtons.put(Mouse.SELECT, false);
        mouseButtons.put(Mouse.DOACTION, false);
        mouseButtons.put(Mouse.SCROLL, false);
    }

    InputComponent(){
        json = new Json();
    }

    public abstract void update(Entity entity, float delta);

    @Override
    public void receiveMessage(String message) {

    }
}
