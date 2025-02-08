package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;

public interface Movable extends Object {
    GridPoint2 getCurrentCoordinates();

    GridPoint2 getDestinationCoordinates();

    void move(Direction direction, boolean canMove);
}
