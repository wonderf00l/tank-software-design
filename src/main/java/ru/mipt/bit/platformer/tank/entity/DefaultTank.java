package ru.mipt.bit.platformer.tank.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.Direction;;

public class DefaultTank implements Tank {

    private GridPoint2 location;
    private float rotation;

    public GridPoint2 getLocation() {
        return location;
    }

    public float getRotation() {
        return rotation;
    }

    public void move(Direction direction, boolean hasObstacleOnAWay) {

        this.rotation = direction.getRotation();

        if (hasObstacleOnAWay) {
            return;
        }

        this.location.add(direction.getCoordsDelta());
    }
}
