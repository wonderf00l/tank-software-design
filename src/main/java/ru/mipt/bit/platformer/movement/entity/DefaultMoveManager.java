package ru.mipt.bit.platformer.movement.entity;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class DefaultMoveManager implements MoveManager {

    private float speed;

    public DefaultMoveManager(float speed) {
        this.speed = speed;
    }

    public float continueObjectMovementProgress(float curObjMoveProgress, float moveDuration) {
        return clamp(curObjMoveProgress + moveDuration / speed, MOVEMENT_START, MOVEMENT_FINISH);
    }

    public boolean hasObjectFinishedMovement(float objMoveProgress) {
        return objMoveProgress == MOVEMENT_FINISH;
    }

}
