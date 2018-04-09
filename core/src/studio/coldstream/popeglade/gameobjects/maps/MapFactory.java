package studio.coldstream.popeglade.gameobjects.maps;

import java.util.Hashtable;

public class MapFactory {

    private static Hashtable<MapType, Map> mapTable = new Hashtable<MapType, Map>();

    public static enum MapType{
        STUBBEN,
        FOREST,
        LAKE
    }

    static public Map getMap(MapType mapType){
        Map map = null;
        switch(mapType){
            case STUBBEN:
                map = mapTable.get(MapType.STUBBEN);
                if( map == null ){
                    map = new StubbenMap();
                    mapTable.put(MapType.STUBBEN, map);
                }
                break;
            case FOREST:
                map = mapTable.get(MapType.FOREST);
                if( map == null ){
                    map = new ForestMap();
                    mapTable.put(MapType.FOREST, map);
                }
                break;
            /*case CASTLE_OF_DOOM:
                map = mapTable.get(MapType.CASTLE_OF_DOOM);
                if( map == null ){
                    map = new CastleDoomMap();
                    mapTable.put(MapType.CASTLE_OF_DOOM, map);
                }
                break;*/
            default:
                break;
        }
        return map;
    }

    public static void clearCache(){
        for( Map map: mapTable.values()){
            map.dispose();
        }
        mapTable.clear();
    }

}
