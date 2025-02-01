package ru.mipt.bit.platformer.entity;

// smth that can update it's state, that can occur during deltaTime
public interface Updatable extends Object {
    public void update(float deltaTime);
}
