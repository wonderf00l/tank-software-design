package ru.mipt.bit.platformer.level.entity;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.Object;
import ru.mipt.bit.platformer.entity.Updatable;

public class Level {

    private final int width;
    private final int height;

    public ArrayList<LevelListener> subscribers;

    // маппинг для ускоренного доступа к объектам уровня
    private HashMap<GridPoint2, Object> objectsLocations = new HashMap<>();

    // checks if obj impl updatagle -> upd it's state(чтобы не держать 2 мапы и не
    // усложнять логику)

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void update(float deltaTime) {
        for (Object gameObj : objectsLocations.values()) {
            if (gameObj instanceof Updatable) {
                ((Updatable) gameObj).update(deltaTime);
            }
        }
    }

    public boolean isLocationWithinLevel(GridPoint2 location) {
        return 0 <= location.x && location.x <= width &&
                0 <= location.y && location.y <= height;
    }

    public boolean isLocationOccupied(GridPoint2 location) {
        return objectsLocations.containsKey(location);
    }

    // api ниже используется объектами уровня

    // изменение позиции
    public void setObjectOnLocation(Object obj, GridPoint2 location) {
        if (isLocationOccupied(location)) {
            return;
        }

        objectsLocations.put(location, obj);
    }

    public void setNewObjectOnLevel(Object obj, GridPoint2 location) {
        setObjectOnLocation(obj, location);

        for (LevelListener subscriber : subscribers) {
            subscriber.notifyAboutObjectCreation(obj);
        }
    }

    public void deleteObjectFromLevel(GridPoint2 location) {
        Object obj = objectsLocations.get(location);

        objectsLocations.remove(location);

        for (LevelListener subscriber : subscribers) {
            subscriber.notifyAboutObjectDeletion(obj);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void subscribeToLevelEvents(LevelListener listener) {
        subscribers.add(listener);
    }
}
