package studio.coldstream.popeglade.gameobjects.maps;

import com.badlogic.gdx.utils.Json;

public class ForestMap extends Map {
    private static final String TAG = ForestMap.class.getSimpleName();

    private static String mapPath = "android/assets/tmx/new_map.tmx";
    private Json json;

    ForestMap() {
        super(MapFactory.MapType.FOREST, mapPath);

        json = new Json();
    }
}