package ru.mipt.bit.platformer.bullet.entity;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.direction.Direction;
import ru.mipt.bit.platformer.entity.Healthy;
import ru.mipt.bit.platformer.entity.Injuring;
import ru.mipt.bit.platformer.entity.Movable;
import ru.mipt.bit.platformer.entity.Oriented;
import ru.mipt.bit.platformer.entity.Updatable;
import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.movement.entity.MoveManager;

public class Bullet implements Updatable, Oriented, Movable, Injuring {

    public static final float BULLET_MOVEMENT_SPEED = 0.8f;
    private static final float DAMAGE = 20f;

    private GridPoint2 curLocation;
    private GridPoint2 destLocation;

    private Direction direction;

    private float speed;
    private float movementProgress;

    private Level level;

    public Bullet(GridPoint2 initLocation, Direction direction, float speed, Level level) {
        curLocation = initLocation;
        destLocation = curLocation.cpy();

        this.direction = direction;

        this.speed = speed;
        movementProgress = MoveManager.MOVEMENT_FINISH;

        this.level = level;
    }

    public void move(Direction direction, boolean canMove) {
        if (!MoveManager.hasObjectFinishedMovement(movementProgress)) {
            return;
        }

        startMovement(direction);
    }

    private void startMovement(Direction direction) {
        destLocation.add(direction.getCoordsDelta());

        movementProgress = MoveManager.MOVEMENT_START;
    }

    public void update(float deltaTime) {
        movementProgress = MoveManager.objectMovementProgressAfterDuration(movementProgress, deltaTime, speed);

        if (!MoveManager.hasObjectFinishedMovement(movementProgress)) {
            return;
        }

        Object obj = level.getObjectOnLocation(destLocation);

        if (obj == null) {
            GridPoint2 curLocationCpy = curLocation.cpy();
            curLocation.set(destLocation);
            level.replaceObject(this, curLocationCpy, curLocation.cpy());

            // чтобы не было утечек пуль, если улетят за пределы уровня
            if (!level.isLocationWithinLevel(curLocation.cpy().add(direction.getCoordsDelta()))) {

                level.deleteObjectFromLevel(curLocation);

                return;
            }

            startMovement(direction);

            return;
        }

        if (obj instanceof Healthy) {
            hit((Healthy) obj);
        }

        level.deleteObjectFromLevel(curLocation);
    }

    public void hit(Healthy healthyObj) {
        float curObjHealth = healthyObj.getHealth();

        healthyObj.setHealth(curObjHealth - DAMAGE);
    }

    public GridPoint2 getLocation() {
        return curLocation;
    }

    public GridPoint2 getCurrentCoordinates() {
        return curLocation;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destLocation;
    }

    public float getRotation() {
        return direction.getRotation();
    }

    public float getMovementProgress() {
        return movementProgress;
    }
}
