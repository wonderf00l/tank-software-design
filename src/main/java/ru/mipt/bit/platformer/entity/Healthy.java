package ru.mipt.bit.platformer.entity;

// isAlive?
public interface Healthy extends Object {
    float getHealth();

    void setHealth(float health);

    boolean isAlive();
}
