package ru.mipt.bit.platformer.entity;

// smth that can update it's state, that can change during deltaTime
public interface Updatable extends Object {
    void update(float deltaTime);
}
