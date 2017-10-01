package studio.coldstream.popeglade.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static studio.coldstream.popeglade.gamehelpers.AssetLoader.playerAnimation;

/**
 * Created by Scalar on 02/09/2017.
 */

public class Player {

    //Statics
    private int width;
    private int height;
    private Rectangle boundingRect;

    //Dynamics
    private Vector2 position;
    private Vector2 tempPosition;
    private int rotation;
    private int moveX, moveY;

    private float velocity;
    private float speedFactor;

    //Data
    private Inventory inventory;

    private int maxHealth;
    private int currentHealth;
    private int maxStamina;
    private int currentStamina;
    private int maxMana;
    private int currentMana;

    private int currentLevel;
    private int currentExperience;

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

        inventory = new Inventory(10);
        currentHealth = 6;
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

        boundingRect.set(tempPosition.x - width * 0.3f, tempPosition.y - height * 0.05f, width * (1.0f - 0.4f), height * 0.2f);
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

        boundingRect.set(tempPosition.x - width * 0.3f, tempPosition.y - height * 0.05f, width * (1.0f - 0.4f), height * 0.2f);
    }

    public void render(float delta, float runTime, SpriteBatch batcher) {
        batcher.draw((TextureRegion) playerAnimation[this.getRotation()].getKeyFrame(runTime),
                this.getX()- this.getWidth() / 2, this.getY(), this.getWidth(), this.getHeight());
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
    public void makeMoveY() { position.set(position.x, tempPosition.y); }

    public void pickUpCollectable(Collectable item) {
        boolean foundItem = false;
        //Check inventory for stackables
        if(item.isStackable()) {
            for (int i = 0; i < inventory.getPocket().size(); i++) {
                if (inventory.getPocket().get(i).getId() == item.getId()) {
                    inventory.getPocket().get(i).StackInc();
                    foundItem = true;
                    //Gdx.app.log("Pointer", inventory.getPocket().get(i).getStackSize() + "");
                }
            }
        }
        //new item!
        if(!foundItem)
            inventory.addToPocket(item);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
}
