package studio.coldstream.popeglade.userinterfaces;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.gameobjects.items.InventoryItem;
import studio.coldstream.popeglade.gameobjects.items.InventoryItemFactory;

public class InventoryUI extends Window {
    private static final String TAG = InventoryUI.class.getSimpleName();

    private static final int SLOT_WIDTH = 32;
    private static final int SLOT_HEIGHT = 32;

    private Table inventoryTableRow;
    private Table inventoryTable;

    public InventoryUI() {
        super("", AssetLoader.STATUSUI_SKIN);

        inventoryTable = new Table();
        inventoryTable.defaults().space(0);

        //Layout
        for(int i = 0; i < 6; i++){
            inventoryTableRow = new Table();
            for(int j = 0; j < 12; j++) {
                InventorySlot inventorySlot = new InventorySlot();
                inventoryTableRow.add(inventorySlot).size(SLOT_WIDTH, SLOT_HEIGHT);
            }
            this.inventoryTable.row();
            this.inventoryTable.add(inventoryTableRow);
        }

        this.pad(16, 16, 16, 16);
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



}
