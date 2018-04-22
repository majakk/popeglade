package studio.coldstream.popeglade.userinterfaces;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.gameobjects.items.InventoryItem;

class InventorySlot extends Stack implements InventorySlotSubject {
    private int filterItemType;
    private Stack defaultBackground;
    private Image customBackgroundDecal;
    private Label numItemsLabel;
    private int numItemsVal = 0;

    private Array<InventorySlotObserver> observers;


    public InventorySlot() {
        filterItemType = 0; //filter nothing
        defaultBackground = new Stack();
        customBackgroundDecal = new Image();
        observers = new Array<InventorySlotObserver>();

        Image image = new Image(new NinePatch(AssetLoader.STATUSUI_TEXTURE_ATLAS.createPatch("dialog")));

        numItemsLabel = new Label(String.valueOf(numItemsVal), AssetLoader.STATUSUI_SKIN, "inventory-item-count");
        numItemsLabel.setAlignment(Align.bottomRight);
        numItemsLabel.setVisible(false);

        defaultBackground.add(image);

        defaultBackground.setName("background");
        numItemsLabel.setName("numitems");

        this.add(defaultBackground);
        this.add(numItemsLabel);
    }

    public InventorySlot(int fit, Image cbd){
        this();
        filterItemType = fit;
        customBackgroundDecal = cbd;
        defaultBackground.add(customBackgroundDecal);
    }

    public void decrementItemCount(boolean sendRemoveNotification) {
        numItemsVal--;
        numItemsLabel.setText(String.valueOf(numItemsVal));
        if( defaultBackground.getChildren().size == 1 ){
            defaultBackground.add(customBackgroundDecal);
        }
        checkVisibilityOfItemCount();
        if( sendRemoveNotification ){
            notify(this, InventorySlotObserver.SlotEvent.REMOVED_ITEM);
        }

    }

    public void incrementItemCount(boolean sendAddNotification) {
        numItemsVal++;
        numItemsLabel.setText(String.valueOf(numItemsVal));
        if( defaultBackground.getChildren().size > 1 ){
            defaultBackground.getChildren().pop();
        }
        checkVisibilityOfItemCount();
        if( sendAddNotification ){
            notify(this, InventorySlotObserver.SlotEvent.ADDED_ITEM);
        }
    }

    @Override
    public void add(Actor actor) {
        super.add(actor);

        if( numItemsLabel == null ){
            return;
        }

        if( !actor.equals(defaultBackground) && !actor.equals(numItemsLabel) ) {
            incrementItemCount(true);
        }
    }

    public void remove(Actor actor) {
        super.removeActor(actor);

        if( numItemsLabel == null ){
            return;
        }

        if( !actor.equals(defaultBackground) && !actor.equals(numItemsLabel) ) {
            decrementItemCount(true);
        }
    }

    private void checkVisibilityOfItemCount(){
        if( numItemsVal < 2){
            numItemsLabel.setVisible(false);
        }else{
            numItemsLabel.setVisible(true);
        }
    }

    public void add(Array<Actor> array) {
        for( Actor actor : array){
            super.add(actor);

            if( numItemsLabel == null ){
                return;
            }

            if( !actor.equals(defaultBackground) && !actor.equals(numItemsLabel) ) {
                incrementItemCount(true);
            }
        }
    }

    public Array<Actor> getAllInventoryItems() {
        Array<Actor> items = new Array<Actor>();
        if( hasItem() ){
            SnapshotArray<Actor> arrayChildren = this.getChildren();
            int numInventoryItems =  arrayChildren.size - 2;
            for(int i = 0; i < numInventoryItems; i++) {
                decrementItemCount(true);
                items.add(arrayChildren.pop());
            }
        }
        return items;
    }

    public void updateAllInventoryItemNames(String name){
        if( hasItem() ){
            SnapshotArray<Actor> arrayChildren = this.getChildren();
            //skip first two elements
            for(int i = arrayChildren.size - 1; i > 1 ; i--) {
                arrayChildren.get(i).setName(name);
            }
        }
    }

    public void removeAllInventoryItemsWithName(String name){
        if( hasItem() ){
            SnapshotArray<Actor> arrayChildren = this.getChildren();
            //skip first two elements
            for(int i = arrayChildren.size - 1; i > 1 ; i--) {
                String itemName = arrayChildren.get(i).getName();
                if( itemName.equalsIgnoreCase(name)){
                    decrementItemCount(true);
                    arrayChildren.removeIndex(i);
                }
            }
        }
    }


    public void clearAllInventoryItems(boolean sendRemoveNotifications) {
        if( hasItem() ){
            SnapshotArray<Actor> arrayChildren = this.getChildren();
            int numInventoryItems =  getNumItems();
            for(int i = 0; i < numInventoryItems; i++) {
                decrementItemCount(sendRemoveNotifications);
                arrayChildren.pop();
            }
        }
    }

    public boolean hasItem(){
        if( hasChildren() ){
            SnapshotArray<Actor> items = this.getChildren();
            if( items.size > 2 ){
                return true;
            }
        }
        return false;
    }

    public int getNumItems(){
        if( hasChildren() ){
            SnapshotArray<Actor> items = this.getChildren();
            return items.size - 2;
        }
        return 0;
    }

    public int getNumItems(String name){
        if( hasChildren() ){
            SnapshotArray<Actor> items = this.getChildren();
            int totalFilteredSize = 0;
            for( Actor actor: items ){
                if( actor.getName().equalsIgnoreCase(name)){
                    totalFilteredSize++;
                }
            }
            return totalFilteredSize;
        }
        return 0;
    }

    public boolean doesAcceptItemUseType(int itemUseType){
        if( filterItemType == 0 ){
            return true;
        }else {
            return ((filterItemType & itemUseType) == itemUseType);
        }
    }

    public InventoryItem getTopInventoryItem(){
        InventoryItem actor = null;
        if( hasChildren() ){
            SnapshotArray<Actor> items = this.getChildren();
            if( items.size > 2 ){
                actor = (InventoryItem) items.peek();
            }
        }
        return actor;
    }

    @Override
    public void addObserver(InventorySlotObserver slotObserver) {
        observers.add(slotObserver);
    }

    @Override
    public void removeObserver(InventorySlotObserver slotObserver) {
        observers.removeValue(slotObserver, true);
    }

    @Override
    public void removeAllObservers() {
        for(InventorySlotObserver observer: observers){
            observers.removeValue(observer, true);
        }
    }

    @Override
    public void notify(final InventorySlot slot, InventorySlotObserver.SlotEvent event) {
        for(InventorySlotObserver observer: observers){
            observer.onNotify(slot, event);
        }
    }

}
