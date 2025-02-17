package ru.mipt.bit.platformer.command.move;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.entity.Direction;
import ru.mipt.bit.platformer.entity.Movable;
import ru.mipt.bit.platformer.level.entity.Level;

public class Move implements Command {

    private Movable obj;
    private final Direction direction;
    private Level level;

    public Move(Movable objToMove, Direction objDirection, Level gameLevel) {
        obj = objToMove;
        direction = objDirection;
        level = gameLevel;
    }

    public void exec() {
        GridPoint2 curObjLocation = obj.getLocation().cpy();

        GridPoint2 destLocation = curObjLocation.add(direction.getCoordsDelta());

        boolean canMove = level.isLocationWithinLevel(destLocation)
                && !level.isLocationOccupied(destLocation)
                && !level.isLocationBooked(destLocation);

        obj.move(direction, canMove);
    }

}
