package ru.mipt.bit.platformer.movement.entity;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class MoveManager {

    public static final float MOVEMENT_START = 0f;
    public static final float MOVEMENT_FINISH = 1f;

    public static float objectMovementProgressAfterDuration(float curObjMoveProgress, float moveDuration, float speed) {
        return clamp(curObjMoveProgress + moveDuration / speed, MOVEMENT_START, MOVEMENT_FINISH);
    }

    public static boolean hasObjectFinishedMovement(float objMoveProgress) {
        return objMoveProgress == MOVEMENT_FINISH;
    }

}
