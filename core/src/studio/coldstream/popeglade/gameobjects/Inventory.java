package studio.coldstream.popeglade.gameobjects;

import java.util.LinkedList;

/**
 * Created by Scalar on 24/09/2017.
 */

public class Inventory {

    private int pocketSize = 10;
    private static int gearSize = 4;
    private int backpackSize;

    private int activeItemSlot;
    private Collectable activeItem;

    private LinkedList<Collectable> pocket;
    private LinkedList<Collectable> backpack;
    private LinkedList<Collectable> gear;

    public Inventory(int bSize) {
        this.pocketSize = bSize;
        activeItemSlot = 0;

        pocket = new LinkedList<Collectable>();
        backpack = new LinkedList<Collectable>();
        gear = new LinkedList<Collectable>();
    }


    public void moveActiveItem(int amount) {
        activeItemSlot += amount;
        while (activeItemSlot < 0)
            activeItemSlot += pocketSize;

        activeItemSlot %= pocketSize;

    }

    public int getActiveItemSlot() {
        return activeItemSlot;
    }

    public int getMaxItemSlots() {
        return pocketSize;
    }

    public LinkedList<Collectable> getPocket() {
        return pocket;
    }

    public void addToPocket(Collectable item) {
        pocket.add(item);
    }
}
