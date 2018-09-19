package studio.coldstream.popeglade.gameobjects.entities;

public interface Component {
    String MESSAGE_TOKEN = ":::::";

    enum MESSAGE{
        CURRENT_POSITION,
        INIT_START_POSITION,
        CURRENT_DIRECTION,
        INIT_DIRECTION,
        CURRENT_STATE,
        INIT_STATE,
        COLLISION_WITH_MAP,
        COLLISION_WITH_ENTITY,
        INTERACTION_WITH_MAP,
        INTERACTION_WITH_ENTITY,
        LOAD_ANIMATIONS,
        INIT_SELECT_ENTITY,
        ENTITY_SELECTED,
        ENTITY_DESELECTED,
        INIT_FRAME_DIMENSIONS,
        INIT_NUM_OF_TILES_DIMENSIONS,
        INIT_BOUNDING_BOX
    }

    void dispose();
    void receiveMessage(String message);

}
