package studio.coldstream.popeglade.userinterfaces;

import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;

public class InventoryBarUI extends Window {
    private static final String TAG = InventoryBarUI.class.getSimpleName();

    private static final int SLOT_WIDTH = 32;
    private static final int SLOT_HEIGHT = 32;

    private Table barSlotTable;

    public InventoryBarUI() {
        super("", AssetLoader.STATUSUI_SKIN);

        barSlotTable = new Table();

        //Layout
        for(int i = 0; i < 10; i++){
            InventorySlot inventorySlot =  new InventorySlot();
            barSlotTable.add(inventorySlot).size(SLOT_WIDTH, SLOT_HEIGHT);
        }

        this.pad(0, 2, 0, 2);
        this.add(barSlotTable).colspan(1);
        this.pack();
    }

    public Table getBarSlotTable() {
        return barSlotTable;
    }
}
