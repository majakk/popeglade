package studio.coldstream.popeglade.gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import studio.coldstream.popeglade.gameobjects.Player;
import studio.coldstream.popeglade.gameobjects.Pointer;
import studio.coldstream.popeglade.gameworld.GameRenderer;

/**
 * Created by Scalar on 01/08/2017.
 */

public class InputHandler implements InputProcessor {

    private Player myPlayer;
    private Pointer myPointer;
    private OrthographicCamera myCamera;
    private int keycountX, keycountY;

    public InputHandler(Player player, Pointer pointer, OrthographicCamera cam) {
        myPlayer = player;
        myPointer = pointer;
        myCamera = cam;

        keycountX = 0;
        keycountY = 0;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            // Some stuff
            return true;
        }
        return false;
    }



    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            myPlayer.setRotation(3);
            keycountX++;
            myPlayer.moveX(-1);
        }
        if(keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            myPlayer.setRotation(1);
            keycountX++;
            myPlayer.moveX(1);
        }
        if(keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            myPlayer.setRotation(0);
            keycountY++;
            myPlayer.moveY(-1);
        }
        if(keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            myPlayer.setRotation(2);
            keycountY++;
            myPlayer.moveY(1);
        }



        return true;

    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            keycountX--;
        }
        if(keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            keycountX--;
        }
        if(keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            keycountY--;
        }
        if(keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            keycountY--;
        }
        if(keycountX <= 0) {
            keycountX = 0;
            myPlayer.moveX(0);
        }
        if(keycountY <= 0) {
            keycountY = 0;
            myPlayer.moveY(0);
        }



        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.LEFT)
            myPointer.click();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        myPointer.setPosition(new Vector2(getMousePosInGameWorld().x, getMousePosInGameWorld().y));

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    Vector3 getMousePosInGameWorld() {
        return myCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

}
