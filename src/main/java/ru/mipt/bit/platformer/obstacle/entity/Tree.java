package ru.mipt.bit.platformer.obstacle.entity;

import com.badlogic.gdx.math.GridPoint2;

public class Tree implements Obstacle {

    private final GridPoint2 location;

    public Tree(GridPoint2 location) {
        this.location = location;
    }

    public GridPoint2 getLocation() {
        return location;
    }
}
