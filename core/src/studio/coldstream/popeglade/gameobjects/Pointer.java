package studio.coldstream.popeglade.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Scalar on 23/09/2017.
 */

public class Pointer {

    private Vector2 position;

    public Pointer() {
        position = new Vector2(0, 0);
    }

    public void update(float delta) {

    }

    public void setPosition(Vector2 v) {
        position = v;
    }

    public void click() {
        Gdx.app.log("Pointer","Click!");
    }

    public Vector2 getPosition() {
        return position;
    }
}
