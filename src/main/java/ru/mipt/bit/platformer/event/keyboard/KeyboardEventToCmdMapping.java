package ru.mipt.bit.platformer.event.keyboard;

import static com.badlogic.gdx.Input.Keys;

import java.util.HashMap;

import ru.mipt.bit.platformer.event.Event;
import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.command.CommandProducer;
import ru.mipt.bit.platformer.command.move.MoveCommandProducer;
import ru.mipt.bit.platformer.entity.Direction;

public class KeyboardEventToCmdMapping {

    private Level level;
    private HashMap<Event, CommandProducer> mapping;

    public KeyboardEventToCmdMapping(Level gameLevel) {
        level = gameLevel;

        initMapping();
    }

    private void initMapping() {
        mapping.put(new Event(Keys.W), new MoveCommandProducer(level, Direction.UP));
        mapping.put(new Event(Keys.UP), new MoveCommandProducer(level, Direction.UP));
        mapping.put(new Event(Keys.A), new MoveCommandProducer(level, Direction.LEFT));
        mapping.put(new Event(Keys.LEFT), new MoveCommandProducer(level, Direction.LEFT));
        mapping.put(new Event(Keys.S), new MoveCommandProducer(level, Direction.DOWN));
        mapping.put(new Event(Keys.DOWN), new MoveCommandProducer(level, Direction.DOWN));
        mapping.put(new Event(Keys.D), new MoveCommandProducer(level, Direction.RIGHT));
        mapping.put(new Event(Keys.RIGHT), new MoveCommandProducer(level, Direction.RIGHT));
    }

}
