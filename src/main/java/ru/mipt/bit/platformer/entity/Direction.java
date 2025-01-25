package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    RIGHT(0f, new GridPoint2(1, 0)),
    LEFT(-180f, new GridPoint2(-1, 0)),
    UP(90f, new GridPoint2(0, 1)),
    DOWN(-90f, new GridPoint2(0, -1)),;

    private final float rotation;
    private final GridPoint2 coordsDelta;

    Direction(float rotation, GridPoint2 coordsDelta) {
        this.rotation = rotation;
        this.coordsDelta = coordsDelta;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getCoordsDelta() {
        return coordsDelta;
    }
}