package ru.mipt.bit.platformer.command.move;

import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.command.CommandProducer;
import ru.mipt.bit.platformer.entity.Direction;
import ru.mipt.bit.platformer.entity.Movable;
import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.entity.Object;

public class MoveCommandProducer implements CommandProducer {

    private Level level;
    private final Direction objDirection;

    public MoveCommandProducer(Level gameLevel, Direction direction) {
        level = gameLevel;
        objDirection = direction;
    }

    // принимаем Object, чтобы рабоать и с графическими объектами
    public Command produce(Object cmdExecutor) {
        return new Move((Movable) cmdExecutor, objDirection, level);
    }

}
