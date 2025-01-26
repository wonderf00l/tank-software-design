package ru.mipt.bit.platformer.tank.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.Level;
import ru.mipt.bit.platformer.entity.Direction;
import ru.mipt.bit.platformer.movement.entity.MoveManager;;

public class DefaultTank implements Tank { // Tank implements ObjectWithState interface

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

    private void updateDestination(Direction direction, boolean hasObstacleOnAWay) {
        rotation = direction.getRotation();

        if (hasObstacleOnAWay) {
            return;
        }

        destLocation.add(direction.getCoordsDelta());

        movementProgress = MoveManager.MOVEMENT_START;
    }

    /*
     * 
     * isMOving() {
     * return movProg < 1f
     * }
     * 
     * 
     * moveDOne() { reutrn prog == 1f }
     * 
     */

    // upddate() { общий случай обновления сотсония - contniue prog } {
    //
    // continueProg()
    //
    // if hasFInishedMOv() {
    // curLocation.set(destLocation);
    // level.setObjectOnLocation(this, curLocation);
    // }
    //
    //
    // }

    public void move(Direction direction, boolean hasObstacleOnAWay, float deltaTime) {

        if (!movementManager.hasObjectFinishedMovement(movementProgress)) {
            // movementManager.continueObjectMovementProgress(movementProgress, deltaTime);

            return;
        }

        // долдно быить ТОЛЬКО В НАЧАЛЕ
        updateDestination(direction, hasObstacleOnAWay);

        // должно быть ТОЛЬКО В конце движения
        // curLocation.set(destLocation);
        // level.setObjectOnLocation(this, curLocation);

        /*
         * 
         * if !moveManager.isMoving() {
         * 
         * updateDest() *
         * 
         * return
         * }
         * 
         * 
         * moveManager.doMovePortion() // aka continueProgress()
         * 
         * if moveManager.MoveFinished() {
         * 
         * curLoc = destLoc
         * 
         * }
         * 
         * 
         * 
         */
    }
}
