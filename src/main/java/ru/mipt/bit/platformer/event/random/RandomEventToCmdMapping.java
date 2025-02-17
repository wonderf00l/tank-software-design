package ru.mipt.bit.platformer.event.random;

import java.util.HashMap;

import ru.mipt.bit.platformer.command.CommandProducer;
import ru.mipt.bit.platformer.command.move.MoveCommandProducer;
import ru.mipt.bit.platformer.entity.Direction;
import ru.mipt.bit.platformer.level.entity.Level;

public class RandomEventToCmdMapping {

    private Level level;

    // event type is a simple Integer
    private HashMap<Integer, CommandProducer> mapping = new HashMap<>();

    public RandomEventToCmdMapping(Level gameLevel) {
        level = gameLevel;

        initMapping();
    }

    private void initMapping() {
        mapping.put(RandomEventListener.UP, new MoveCommandProducer(level, Direction.UP));
        mapping.put(RandomEventListener.LEFT, new MoveCommandProducer(level, Direction.LEFT));
        mapping.put(RandomEventListener.DOWN, new MoveCommandProducer(level, Direction.DOWN));
        mapping.put(RandomEventListener.RIGHT, new MoveCommandProducer(level, Direction.RIGHT));
    }

    public HashMap<Integer, CommandProducer> getEventToCmdMapping() {
        return mapping;
    }
}
