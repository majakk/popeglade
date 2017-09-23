package studio.coldstream.popeglade.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Scalar on 02/09/2017.
 */

public class Player {

    private Vector2 position;
    private Vector2 tempPosition;
    private int rotation;

    private int width;
    private int height;

    private int moveX, moveY;

    private float velocity;
    private Rectangle boundingRect;
    private float speedFactor;

    public Player(float x, float y, int w, int h) {
        width = w;
        height = h;
        moveX = 0;
        moveY = 0;
        position = new Vector2(x, y);
        tempPosition = new Vector2(x, y);
        rotation = 0;
        velocity = 2.3f;
        boundingRect = new Rectangle();
    }

    public void updateX(float delta) {

        tempPosition.set(position);

        if(this.moveY == 0)
            speedFactor=1.5f;
        else
            speedFactor=1.0f;

        if (this.moveX < 0)
            tempPosition.add(-velocity * speedFactor, 0);
        else if (this.moveX > 0)
            tempPosition.add(velocity * speedFactor, 0);

        boundingRect.set(tempPosition.x - width * 0.25f, tempPosition.y - height * 0.05f, width * (1.0f - 0.5f), height * 0.4f);
    }

    public void updateY(float delta) {

        tempPosition.set(position);

        if(this.moveX == 0)
            speedFactor=1.5f;
        else
            speedFactor=1.0f;

        if (this.moveY < 0 )
            tempPosition.add(0, velocity * speedFactor );
        else if (this.moveY > 0 )
            tempPosition.add(0, -velocity * speedFactor );

        boundingRect.set(tempPosition.x - width * 0.25f, tempPosition.y - height * 0.05f, width * (1.0f - 0.5f), height * 0.4f);
    }

    public void moveX(int m) {
        this.moveX = m;
    }

    public void moveY(int m) {
        this.moveY = m;
    }

    public Vector2 getPosition() { return position; }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rot) {
        rotation = rot;
    }

    public Rectangle getBoundingRect(){
        return boundingRect;
    }

    public void makeMoveX() {
        position.set(tempPosition.x, position.y);
    }
    public void makeMoveY() {
        position.set(position.x, tempPosition.y);

    }


}
