package studio.coldstream.popeglade.gameobjects;

import java.util.LinkedList;

/**
 * Created by Scalar on 24/09/2017.
 */

public class Inventory {

    private static int pocketSize = 10;
    private static int gearSize = 4;
    private int backpackSize;

    private int activeItemSlot;
    private Collectable activeItem;

    private LinkedList<Collectable> pocket;
    private LinkedList<Collectable> backpack;
    private LinkedList<Collectable> gear;

    public Inventory(int bSize) {
        this.backpackSize = bSize;
        activeItemSlot = 0;
    }


    public void moveActiveItem(int amount) {
        activeItemSlot += amount;
        while (activeItemSlot < 0)
            activeItemSlot += 10;

        //activeItemSlot = Math.abs(activeItemSlot);
        activeItemSlot %= 10;

    }

    public int getActiveItemSlot() {
        return activeItemSlot;
    }
}
