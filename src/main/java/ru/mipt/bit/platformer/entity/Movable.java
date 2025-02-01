package ru.mipt.bit.platformer.entity;

public interface Movable extends Object {
    void move(Direction direction, boolean canMove);
}
