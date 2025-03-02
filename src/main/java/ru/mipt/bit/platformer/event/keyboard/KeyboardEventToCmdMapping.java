package ru.mipt.bit.platformer.event.keyboard;

import static com.badlogic.gdx.Input.Keys;

import java.util.HashMap;

import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.command.CommandProducer;
import ru.mipt.bit.platformer.command.move.MoveCommandProducer;
import ru.mipt.bit.platformer.command.shoot.ShootCommandProducer;
import ru.mipt.bit.platformer.command.toggle.ToggleCommandProducer;
import ru.mipt.bit.platformer.direction.Direction;

public class KeyboardEventToCmdMapping {
    private Level level;

    public KeyboardEventToCmdMapping(Level gameLevel) {
        level = gameLevel;
    }

    private void initCmdMappingForLevel(HashMap<Integer, CommandProducer> mapping) {
        mapping.put(Keys.L, new ToggleCommandProducer());
    }

    // т.к. команды применяются к разным
    // объектам, задаем CommandProducer,
    // конкретный объект будет передан через
    // аргмуенты
    private void initCmdMappingForPlayer(HashMap<Integer, CommandProducer> mapping) {
        mapping.put(Keys.W, new MoveCommandProducer(level, Direction.UP));
        mapping.put(Keys.UP, new MoveCommandProducer(level, Direction.UP));
        mapping.put(Keys.A, new MoveCommandProducer(level, Direction.LEFT));
        mapping.put(Keys.LEFT, new MoveCommandProducer(level, Direction.LEFT));
        mapping.put(Keys.S, new MoveCommandProducer(level, Direction.DOWN));
        mapping.put(Keys.DOWN, new MoveCommandProducer(level, Direction.DOWN));
        mapping.put(Keys.D, new MoveCommandProducer(level, Direction.RIGHT));
        mapping.put(Keys.RIGHT, new MoveCommandProducer(level, Direction.RIGHT));
        mapping.put(Keys.SPACE, new ShootCommandProducer(level));
    }

    public HashMap<Integer, CommandProducer> getEventToCmdMappingForPlayer() {
        // event type is a simple Integer
        HashMap<Integer, CommandProducer> mapping = new HashMap<>();

        initCmdMappingForPlayer(mapping);

        return mapping;
    }

    public HashMap<Integer, CommandProducer> getEventToCmdMappingForLevel() {
        HashMap<Integer, CommandProducer> mapping = new HashMap<>();

        initCmdMappingForLevel(mapping);

        return mapping;
    }
}
