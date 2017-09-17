package studio.coldstream.popeglade.gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Scalar on 01/08/2017.
 */

public class AssetLoader {

    public static Texture texture;
    //public static TextureRegion bg, grass;

    public static Animation playerAnimation[];
    public static TextureRegion player[][];


    //public static TextureRegion skullUp, skullDown, bar;

    public static void load() {

        //player[direction][animation]
        player = new TextureRegion[4][3];
        playerAnimation = new Animation[4];
        texture = new Texture(Gdx.files.internal("android/assets/gfx/player_second.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                player[i][j] = new TextureRegion(texture, j * 24, i * 32, 24, 32);
                //player[i][j].flip(false, true);
            }
        }

        for(int i = 0; i < 4; i++) {
            TextureRegion[] players = {player[i][0], player[i][1], player[i][2]};
            playerAnimation[i] = new Animation(0.2f, players);
            playerAnimation[i].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        }

    }


    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }
}
