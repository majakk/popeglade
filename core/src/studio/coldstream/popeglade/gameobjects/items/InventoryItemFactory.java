package studio.coldstream.popeglade.gameobjects.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;
import java.util.Hashtable;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;
import studio.coldstream.popeglade.userinterfaces.PlayerHUD;

public class InventoryItemFactory {
    private static final String TAG = InventoryItemFactory.class.getSimpleName();

    private Json json = new Json();
    private final String INVENTORY_ITEM = "android/assets/scripts/inventory_items.json";
    private static InventoryItemFactory instance = null;
    private Hashtable<InventoryItem.ItemTypeID,InventoryItem> inventoryItemList;

    public static InventoryItemFactory getInstance() {
        if (instance == null) {
            instance = new InventoryItemFactory();
        }

        return instance;
    }

    private InventoryItemFactory(){
        ArrayList<JsonValue> list = json.fromJson(ArrayList.class, Gdx.files.internal(INVENTORY_ITEM));
        inventoryItemList = new Hashtable<InventoryItem.ItemTypeID, InventoryItem>();

        for (JsonValue jsonVal : list) {
            InventoryItem inventoryItem = json.readValue(InventoryItem.class, jsonVal);
            inventoryItemList.put(inventoryItem.getItemTypeID(), inventoryItem);
        }
    }

    public InventoryItem getInventoryItem(InventoryItem.ItemTypeID inventoryItemType){
        InventoryItem item = new InventoryItem(inventoryItemList.get(inventoryItemType));
        item.setDrawable(new TextureRegionDrawable(AssetLoader.ITEMS_TEXTURE_ATLAS.findRegion(item.getItemTypeID().toString())));
        item.setScaling(Scaling.none);
        return item;
    }


    /*public void testAllItemLoad(){
        for(InventoryItem.ItemTypeID itemTypeID : InventoryItem.ItemTypeID.values()) {
        //for(int itemTypeID = 0; itemTypeID < 2; itemTypeID++) {
            Gdx.app.log(TAG, "" + itemTypeID + ":" + inventoryItemList.size());
            //InventoryItem item = new InventoryItem(inventoryItemList.get(getInventoryItem(InventoryItem.ItemTypeID.ARMOR01)));
            //InventoryItem item = new InventoryItem(this.getInventoryItem(InventoryItem.ItemTypeID.ARMOR01));
            InventoryItem item = new InventoryItem(inventoryItemList.get(itemTypeID));
            item.setDrawable(new TextureRegionDrawable(AssetLoader.ITEMS_TEXTURE_ATLAS.findRegion(item.getItemTypeID().toString())));
            item.setScaling(Scaling.none);
        }
        Gdx.app.log(TAG, "DONE!!!");
    }*/
}
