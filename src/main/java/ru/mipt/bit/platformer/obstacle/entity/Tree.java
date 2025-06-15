package ru.mipt.bit.platformer.obstacle.entity;

import ru.mipt.bit.platformer.entity.Object;
import com.badlogic.gdx.math.GridPoint2;

public class Tree implements Object {

    private final GridPoint2 location;

    public Tree(GridPoint2 location) {
        this.location = location;
    }

    public GridPoint2 getLocation() {
        return location;
    }
}
