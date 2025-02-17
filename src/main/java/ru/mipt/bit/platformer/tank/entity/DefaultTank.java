package ru.mipt.bit.platformer.tank.entity;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.Direction;
import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.movement.entity.MoveManager;;

public class DefaultTank implements Tank {

    private GridPoint2 curLocation;
    private GridPoint2 destLocation;

    private float rotation;

    private float movementProgress;
    private MoveManager movementManager;

    private Level level;

    public DefaultTank(GridPoint2 initLocation, Direction initDirection, MoveManager moveManager, Level level) {
        curLocation = initLocation;
        destLocation = curLocation.cpy();

        rotation = initDirection.getRotation();

        movementManager = moveManager;
        movementProgress = MoveManager.MOVEMENT_FINISH;

        this.level = level;
    }

    public GridPoint2 getLocation() {
        return curLocation;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getCurrentCoordinates() {
        return curLocation;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destLocation;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    private void updateDestination(Direction direction, boolean canMove) {
        rotation = direction.getRotation();

        if (!canMove) {
            return;
        }

        // book location(add destLoc to book)
        destLocation.add(direction.getCoordsDelta());

        level.bookLocation(destLocation);

        movementProgress = MoveManager.MOVEMENT_START;
    }

    public void update(float deltaTime) {
        movementProgress = movementManager.objectMovementProgressAfterDuration(movementProgress, deltaTime);

        if (!movementManager.hasObjectFinishedMovement(movementProgress)) {
            return;
        }
        // movement is finished, booked point is occupied now

        level.unbookLocation(destLocation);

        curLocation.set(destLocation);

        // and del destLoc from booked point

        level.setObjectOnLocation(this, curLocation);
    }

    public void move(Direction direction, boolean canMove) {
        if (!movementManager.hasObjectFinishedMovement(movementProgress)) {
            return;
        }

        updateDestination(direction, canMove);
    }
}
