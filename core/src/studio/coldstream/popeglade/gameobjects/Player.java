package studio.coldstream.popeglade.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
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
    private Rectangle boundingRectX, boundingRectY;

    public Player(float x, float y, int w, int h) {
        width = w;
        height = h;
        moveX = 0;
        moveY = 0;
        position = new Vector2(x, y);
        tempPosition = new Vector2(x, y);
        rotation = 0;
        velocity = 1.5f;
        boundingRectX = new Rectangle();
        boundingRectY = new Rectangle();
    }

    public void update(float delta) {


        tempPosition.set(position);

        if (this.moveX < 0 && this.moveY == 0)
            tempPosition.add(-velocity * 1.5f, 0);
        else if (this.moveX > 0 && this.moveY == 0)
            tempPosition.add(velocity * 1.5f, 0);


        if (this.moveY < 0 && this.moveX == 0)
            tempPosition.add(0, velocity * 1.5f);
        else if (this.moveY > 0 && this.moveX == 0)
            tempPosition.add(0, -velocity * 1.5f);

        else if (this.moveX < 0 && this.moveY < 0)
            tempPosition.add(-velocity, velocity);
        else if (this.moveX < 0 && this.moveY > 0)
            tempPosition.add(-velocity, -velocity);
        else if (this.moveX > 0 && this.moveY < 0)
            tempPosition.add(velocity, velocity);
        else if (this.moveX > 0 && this.moveY > 0)
            tempPosition.add(velocity, -velocity);

        boundingRectX.set(tempPosition.x + width * 0.2f, tempPosition.y, width * (1.0f - 0.4f), height * 0.3f);
        boundingRectY.set(tempPosition.x + width * 0.25f, tempPosition.y - height * 0.05f, width * (1.0f - 0.5f), height * 0.4f);

        return;

    }

    public void moveX(int m) {
        this.moveX = m;
        return;
    }

    public void moveY(int m) {
        this.moveY = m;
        return;
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

    public Rectangle getBoundingRectX(){
        return boundingRectX;
    }
    public Rectangle getBoundingRectY(){
        return boundingRectY;
    }

    public void makeMoveX() {
        position.set(tempPosition.x, position.y);
        //position.add(position.x - tempPosition.x, 0);
        //tempPosition.set(position.x, tempPosition.y);
    }
    public void makeMoveY() {
        position.set(position.x, tempPosition.y);
        //tempPosition.set(tempPosition.x, position.y);
    }
}
