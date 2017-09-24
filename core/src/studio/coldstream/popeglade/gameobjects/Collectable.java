package studio.coldstream.popeglade.gameobjects;

/**
 * Created by Scalar on 24/09/2017.
 */

public class Collectable {

    private int id;
    private boolean stackable;
    private int stackSize;
    private int value;
    private String name;

    public Collectable(int id) {
        this.id = id;
        this.stackSize = 1;
        if(id == 0)
            stackable = true;
        else
            stackable = false;
    }

    public Collectable(String name) {
        this.name = name;
        this.stackSize = 1;
    }

    public int getId() {
        return id;
    }

    public void StackInc(){
        stackSize++;
    }

    public void StackDec(){
        if(stackSize > 1)
            stackSize--;
    }

    public int getStackSize(){
        return stackSize;
    }

    public boolean isStackable() {
        return stackable;
    }
}
