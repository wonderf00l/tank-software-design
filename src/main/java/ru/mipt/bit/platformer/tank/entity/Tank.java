package ru.mipt.bit.platformer.tank.entity;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.direction.Direction;
import ru.mipt.bit.platformer.entity.Healthy;
import ru.mipt.bit.platformer.entity.Updatable;
import ru.mipt.bit.platformer.entity.Oriented;
import ru.mipt.bit.platformer.entity.Movable;
import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.movement.entity.MoveManager;;

public class Tank implements Updatable, Oriented, Movable, Healthy {

    public static final float INIT_TANK_HEALTH = 80f;
    public static final float TANK_MOVEMENT_SPEED = 2.0f;

    private GridPoint2 curLocation;
    private GridPoint2 destLocation;

    private float rotation;

    private float movementProgress;
    private float speed;

    private float health;

    private Level level;

    public Tank(GridPoint2 initLocation, Direction initDirection, float speed, Level level, float health) {
        curLocation = initLocation;
        destLocation = curLocation.cpy();

        rotation = initDirection.getRotation();

        this.speed = speed;
        movementProgress = MoveManager.MOVEMENT_FINISH;

        this.level = level;
        this.health = health;
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

    private void startMovement(Direction direction, boolean canMove) {
        rotation = direction.getRotation();

        if (!canMove) {
            return;
        }

        // book location(add destLoc to book)
        destLocation.add(direction.getCoordsDelta());

        level.bookLocation(destLocation);

        movementProgress = MoveManager.MOVEMENT_START;
    }

    public void move(Direction direction, boolean canMove) {
        if (!MoveManager.hasObjectFinishedMovement(movementProgress)) {
            return;
        }

        startMovement(direction, canMove);
    }

    public void update(float deltaTime) {

        if (!isAlive()) {
            level.deleteObjectFromLevel(curLocation);

            return;
        }

        movementProgress = MoveManager.objectMovementProgressAfterDuration(movementProgress, deltaTime, speed);

        if (!MoveManager.hasObjectFinishedMovement(movementProgress)) {
            return;
        }
        // movement is finished, booked point is occupied now

        level.unbookLocation(destLocation);

        GridPoint2 curLocationCpy = curLocation.cpy();
        curLocation.set(destLocation);
        level.replaceObject(this, curLocationCpy, curLocation.cpy());
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
