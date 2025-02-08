package ru.mipt.bit.platformer.command.move;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.entity.Direction;
import ru.mipt.bit.platformer.entity.Movable;
import ru.mipt.bit.platformer.level.entity.Level;

public class Move implements Command {

    private final Movable obj;
    private final Direction direction;
    private final Level level;

    public Move(Movable objToMove, Direction objDirection, Level gameLevel) {
        obj = objToMove;
        direction = objDirection;
        level = gameLevel;
    }

    public void exec() {
        GridPoint2 destLocation = obj.getLocation().add(direction.getCoordsDelta());

        boolean canMove = level.isLocationWithinLevel(destLocation) && !level.isLocationOccupied(destLocation);

        obj.move(direction, canMove);
    }

}
