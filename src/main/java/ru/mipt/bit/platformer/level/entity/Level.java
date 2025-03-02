package ru.mipt.bit.platformer.level.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.Object;
import ru.mipt.bit.platformer.entity.Updatable;

public class Level {

    private final int width;
    private final int height;

    private ArrayList<LevelListener> subscribers = new ArrayList<>();

    // маппинг для ускоренного доступа к объектам уровня
    private HashMap<GridPoint2, Object> objectsLocations = new HashMap<>();

    // резервирование dst локации - для обработки коллизий между объектами при
    // движении
    private HashSet<GridPoint2> bookedLocations = new HashSet<>();

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void update(float deltaTime) {
        // to prevent concurrent modification exception
        HashMap<GridPoint2, Object> objectsLocationsCpy = new HashMap<>(objectsLocations);

        for (Object gameObj : objectsLocationsCpy.values()) {
            if (gameObj instanceof Updatable) {
                ((Updatable) gameObj).update(deltaTime);
            }
        }
    }

    public boolean isLocationWithinLevel(GridPoint2 location) {
        return 0 <= location.x && location.x <= (width - 1) &&
                0 <= location.y && location.y <= (height - 1);
    }

    public boolean isLocationOccupied(GridPoint2 location) {
        return objectsLocations.containsKey(location);
    }

    public Object getObjectOnLocation(GridPoint2 location) {
        return objectsLocations.get(location);
    }

    private void setObjectOnLocation(Object obj, GridPoint2 location) {
        objectsLocations.put(location, obj);
    }

    private void removeObjectFromLocation(GridPoint2 location) {
        objectsLocations.remove(location);
    }

    // используется объектами уровня
    public void replaceObject(Object obj, GridPoint2 curLocation, GridPoint2 destLocation) {
        removeObjectFromLocation(curLocation);
        setObjectOnLocation(obj, destLocation);
    }

    public void bookLocation(GridPoint2 location) {
        if (isLocationOccupied(location)) {
            return;
        }

        bookedLocations.add(location);
    }

    public void unbookLocation(GridPoint2 location) {
        bookedLocations.remove(location);
    }

    public boolean isLocationBooked(GridPoint2 location) {
        return bookedLocations.contains(location);
    }

    public void setNewObjectOnLevel(Object obj, GridPoint2 location) {
        setObjectOnLocation(obj, location);

        for (LevelListener subscriber : subscribers) {
            subscriber.notifyAboutObjectCreation(obj);
        }
    }

    public void deleteObjectFromLevel(GridPoint2 location) {
        Object obj = objectsLocations.get(location);

        bookedLocations.remove(location);
        removeObjectFromLocation(location);

        for (LevelListener subscriber : subscribers) {
            subscriber.notifyAboutObjectDeletion(obj);
        }

        removeObjectFromLocation(location);
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
