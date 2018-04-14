package studio.coldstream.popeglade.userinterfaces;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;

import studio.coldstream.popeglade.gamehelpers.AssetLoader;

public class StatusUI extends Window {
    private Image hpBar;
    private Image xpBar;
    private ImageButton inventoryButton;

    private int hpVal;
    private int xpVal;

    public StatusUI(){
        super("Stats", AssetLoader.STATUSUI_SKIN);


        //groups
        WidgetGroup group = new WidgetGroup();

        inventoryButton = new ImageButton(AssetLoader.STATUSUI_SKIN, "inventory-button");
        inventoryButton.getImageCell().size(32,32);

        defaults().expand().fill();

        this.pad(this.getPadTop()+ 10, 10, 10, 10);
        this.add();
        this.add(inventoryButton).align(Align.right);
        this.row();

        this.pack();


    }

    public ImageButton getInventoryButton() {
        return inventoryButton;
    }


}
