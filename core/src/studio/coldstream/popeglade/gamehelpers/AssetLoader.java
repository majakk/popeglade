package studio.coldstream.popeglade.gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Scalar on 01/08/2017.
 */

public class AssetLoader {

    private static Texture playerTexture, itemTexture;
    public static TextureRegion items[];

    public static Animation playerAnimation[];
    private static TextureRegion player[][];

    private static Pixmap pm;

    //public static TextureRegion skullUp, skullDown, bar;

    public static void load() {

        //player[direction][animation]
        player = new TextureRegion[4][3];
        playerAnimation = new Animation[4];
        playerTexture = new Texture(Gdx.files.internal("android/assets/gfx/player_second.png"));
        playerTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                player[i][j] = new TextureRegion(playerTexture, j * 24, i * 32, 24, 32);
                //player[i][j].flip(false, true);
            }
        }

        for(int i = 0; i < 4; i++) {
            TextureRegion[] players = {player[i][0], player[i][1], player[i][2]};
            playerAnimation[i] = new Animation(0.2f, players);
            playerAnimation[i].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        }

        //Items
        itemTexture = new Texture(Gdx.files.internal("android/assets/gfx/items.png"));
        itemTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        items = new TextureRegion[4];
        for(int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                items[i *2 +j] = new TextureRegion(itemTexture, j*32, i*32, 32, 32);
            }
        }

        //Cursor
        pm = new Pixmap(Gdx.files.internal("android/assets/gfx/cursor_image.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));

    }


    public static void dispose() {
        // We must dispose of the texture when we are finished.
        playerTexture.dispose();
        itemTexture.dispose();
        pm.dispose();
    }
}
