package ru.mipt.bit.platformer.event.keyboard;

import static com.badlogic.gdx.Input.Keys;

import java.util.HashMap;

import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.command.CommandProducer;
import ru.mipt.bit.platformer.command.move.MoveCommandProducer;
import ru.mipt.bit.platformer.entity.Direction;

public class KeyboardEventToCmdMapping {

    private Level level;

    // event type is a simple Integer
    private HashMap<Integer, CommandProducer> mapping = new HashMap<>();

    public KeyboardEventToCmdMapping(Level gameLevel) {
        level = gameLevel;

        initMapping();
    }

    private void initMapping() {
        mapping.put(Keys.W, new MoveCommandProducer(level, Direction.UP));
        mapping.put(Keys.UP, new MoveCommandProducer(level, Direction.UP));
        mapping.put(Keys.A, new MoveCommandProducer(level, Direction.LEFT));
        mapping.put(Keys.LEFT, new MoveCommandProducer(level, Direction.LEFT));
        mapping.put(Keys.S, new MoveCommandProducer(level, Direction.DOWN));
        mapping.put(Keys.DOWN, new MoveCommandProducer(level, Direction.DOWN));
        mapping.put(Keys.D, new MoveCommandProducer(level, Direction.RIGHT));
        mapping.put(Keys.RIGHT, new MoveCommandProducer(level, Direction.RIGHT));
    }

    public HashMap<Integer, CommandProducer> getEventToCmdMapping() {
        return mapping;
    }
}
