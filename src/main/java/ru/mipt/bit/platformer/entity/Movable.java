package ru.mipt.bit.platformer.entity;

public interface Movable {
    void move(Direction direction, boolean hasObstacleOnAWay);
}
