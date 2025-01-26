package ru.mipt.bit.platformer.movement.entity;

// можно было сделать частью api танка, но потенциально эту логику можно переиспользовать для др. объектов
public interface MoveManager {

    public static final float MOVEMENT_START = 0f;
    public static final float MOVEMENT_FINISH = 1f;

    public float continueObjectMovementProgress(float curObjMoveProgress, float moveDuration);

    public boolean hasObjectFinishedMovement(float objMoveProgress);
}
