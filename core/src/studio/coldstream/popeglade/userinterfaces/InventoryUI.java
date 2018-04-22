package studio.coldstream.popeglade.userinterfaces;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.gameobjects.entities.Entity;
import studio.coldstream.popeglade.gameobjects.items.InventoryItem;
import studio.coldstream.popeglade.gameobjects.items.InventoryItemFactory;

public class InventoryUI extends Window {
    private static final String TAG = InventoryUI.class.getSimpleName();

    private static final int SLOT_WIDTH = 32;
    private static final int SLOT_HEIGHT = 32;

    private Table inventoryTableRow;
    private Table inventoryTable;

    private DragAndDrop dragAndDrop;
    private Array<Actor> inventoryActors;

    public InventoryUI() {
        super("Inventory", AssetLoader.STATUSUI_SKIN);

        dragAndDrop = new DragAndDrop();
        inventoryActors = new Array<>();

        inventoryTable = new Table();
        inventoryTable.defaults().space(0);

        //Layout
        for(int i = 0; i < 4; i++){
            inventoryTableRow = new Table();
            for(int j = 0; j < 10; j++) {
                InventorySlot inventorySlot = new InventorySlot();
                inventoryTableRow.add(inventorySlot).size(SLOT_WIDTH, SLOT_HEIGHT);
            }
            this.inventoryTable.row();
            this.inventoryTable.add(inventoryTableRow);
        }

        this.pad(this.getPadTop() + 16, 16, 16, 16);
        this.add(inventoryTable).colspan(1).pad(0);
        this.pack();
    }

    public Table getInventoryTable() {
        return inventoryTable;
    }

    public static void clearInventoryItems(Table targetTable){
        Array<Cell> cells = targetTable.getCells();
        for( int i = 0; i < cells.size; i++){
            InventorySlot inventorySlot = (InventorySlot)cells.get(i).getActor();
            if( inventorySlot == null ) continue;
            inventorySlot.clearAllInventoryItems(false);
        }
    }

    public static Array<InventoryItemLocation> removeInventoryItems(String name, Table inventoryTable){
        Array<Cell> cells = inventoryTable.getCells();
        Array<InventoryItemLocation> items = new Array<InventoryItemLocation>();
        for(int i = 0; i < cells.size; i++){
            InventorySlot inventorySlot =  ((InventorySlot)cells.get(i).getActor());
            if( inventorySlot == null ) continue;
            inventorySlot.removeAllInventoryItemsWithName(name);
        }
        return items;
    }

    public static void populateInventory(Table targetTable, Array<InventoryItemLocation> inventoryItems, DragAndDrop draganddrop, String defaultName, boolean disableNonDefaultItems){
        clearInventoryItems(targetTable);

        Array<Cell> cells = targetTable.getCells();
        for(int i = 0; i < inventoryItems.size; i++){
            InventoryItemLocation itemLocation = inventoryItems.get(i);
            InventoryItem.ItemTypeID itemTypeID = InventoryItem.ItemTypeID.valueOf(itemLocation.getItemTypeAtLocation());
            InventorySlot inventorySlot =  ((InventorySlot)cells.get(itemLocation.getLocationIndex()).getActor());

            for( int index = 0; index < itemLocation.getNumberItemsAtLocation(); index++ ){
                InventoryItem item = InventoryItemFactory.getInstance().getInventoryItem(itemTypeID);
                String itemName =  itemLocation.getItemNameProperty();
                if( itemName == null || itemName.isEmpty() ){
                    item.setName(defaultName);
                }else{
                    item.setName(itemName);
                }

                inventorySlot.add(item);
                if( item.getName().equalsIgnoreCase(defaultName) ){
                    draganddrop.addSource(new InventorySlotSource(inventorySlot, draganddrop));
                }else if( disableNonDefaultItems == false ){
                    draganddrop.addSource(new InventorySlotSource(inventorySlot, draganddrop));
                }
            }
        }
    }

    public static void setInventoryItemNames(Table targetTable, String name){
        Array<Cell> cells = targetTable.getCells();
        for(int i = 0; i < cells.size; i++){
            InventorySlot inventorySlot =  ((InventorySlot)cells.get(i).getActor());
            if( inventorySlot == null ) continue;
            inventorySlot.updateAllInventoryItemNames(name);
        }
    }

    public boolean doesInventoryHaveSpace(){
        Array<Cell> sourceCells = inventoryTable.getCells();
        int index = 0;

        for (; index < sourceCells.size; index++) {
            InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(index).getActor());
            if (inventorySlot == null) continue;
            int numItems = inventorySlot.getNumItems();
            if (numItems == 0) {
                return true;
            }else{
                index++;
            }
        }
        return false;
    }

    public void addEntityToInventory(Entity entity, String itemName){
        Array<Cell> sourceCells = inventoryTable.getCells();
        int index = 0;

        for (; index < sourceCells.size; index++) {
            InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(index).getActor());
            if (inventorySlot == null) continue;
            int numItems = inventorySlot.getNumItems();
            if (numItems == 0) {
                InventoryItem inventoryItem = InventoryItemFactory.getInstance().getInventoryItem(InventoryItem.ItemTypeID.valueOf(entity.getEntityConfig().getItemTypeID()));
                inventoryItem.setName(itemName);
                inventorySlot.add(inventoryItem);
                this.dragAndDrop.addSource(new InventorySlotSource(inventorySlot, this.dragAndDrop));
                break;
            }
        }
    }

    public void removeQuestItemFromInventory(String questID){
        Array<Cell> sourceCells = inventoryTable.getCells();
        for (int index = 0; index < sourceCells.size; index++) {
            InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(index).getActor());
            if (inventorySlot == null) continue;
            InventoryItem item = inventorySlot.getTopInventoryItem();
            if( item == null ) continue;
            String inventoryItemName = item.getName();
            if (inventoryItemName != null && inventoryItemName.equals(questID) ) {
                inventorySlot.clearAllInventoryItems(false);
            }
        }
    }

    public Array<Actor> getInventoryActors(){
        return inventoryActors;
    }


}