package studio.coldstream.popeglade.gameobjects.maps;


import com.badlogic.gdx.utils.Json;

public class StubbenMap extends Map {
    private static final String TAG = StubbenMap.class.getSimpleName();

    private static String mapPath = "android/assets/tmx/level1.tmx";
    private Json json;

    StubbenMap() {
        super(MapFactory.MapType.STUBBEN, mapPath);

        json = new Json();



    }
}