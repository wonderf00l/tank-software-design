package ru.mipt.bit.platformer.tank.entity;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.Direction;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.movement.entity.MoveManager;;

public class DefaultTank implements Tank {

    // level - сущности хранят level и отмечают свое местоположение на карте(чтобы
    // уровень быстро получал состояние карты),
    // удаляют себя с логиеского уровня
    // используют api уровня, чтобы проверить свободна ли координата, пределы уровня
    // ? - лучше это будет делать уровне

    private GridPoint2 curLocation;
    private GridPoint2 destLocation;

    private float rotation;

    private float movementProgress;
    private MoveManager movementManager;

    private Level level;

    public DefaultTank(GridPoint2 initLocation, Direction initDirection, MoveManager moveManager, Level level) {
        curLocation = initLocation;
        destLocation = initLocation;

        rotation = initDirection.getRotation();

        movementManager = moveManager;
        movementProgress = MoveManager.MOVEMENT_START;

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

    private void updateDestination(Direction direction, boolean canMove) {
        rotation = direction.getRotation();

        if (!canMove) {
            return;
        }

        destLocation.add(direction.getCoordsDelta());

        movementProgress = MoveManager.MOVEMENT_START;
    }

    public void update(float deltaTime) {
        movementProgress = movementManager.objectMovementProgressAfterDuration(movementProgress, deltaTime);

        if (!movementManager.hasObjectFinishedMovement(movementProgress)) {
            return;
        }

        curLocation.set(destLocation);

        level.setObjectOnLocation(this, curLocation);
    }

    public void move(Direction direction, boolean canMove) {
        if (!movementManager.hasObjectFinishedMovement(movementProgress)) {
            return;
        }

        updateDestination(direction, canMove);
    }
}
