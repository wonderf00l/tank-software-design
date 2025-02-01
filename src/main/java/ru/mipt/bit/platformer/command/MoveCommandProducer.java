package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.entity.Direction;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Movable;

public class MoveCommandProducer implements CommandProducer<Movable> {

    private final Level level;
    private final Direction objDirection;

    public MoveCommandProducer(Level gameLevel, Direction direction) {
        level = gameLevel;
        objDirection = direction;
    }

    public Command produce(Movable cmdExecutor) {
        return new Move(cmdExecutor, objDirection, level);
    }

}
