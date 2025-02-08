package ru.mipt.bit.platformer.level.entity;

import java.util.HashMap;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.Object;

// levelProvider - provide level class instance from file, random etc

public class Level {

    private final int width;
    private final int height;

    // маппинг для ускоренного доступа к объектам уровня
    private HashMap<GridPoint2, Object> objectsLocations = new HashMap<>();

    // checks if obj impl updatagle -> upd it's state(чтобы не держать 2 мапы и не
    // усложнять логику)

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isLocationWithinLevel(GridPoint2 location) {
        return 0 <= location.x && location.x <= width &&
                0 <= location.y && location.y <= height;
    }

    public boolean isLocationOccupied(GridPoint2 location) {
        return objectsLocations.containsKey(location);
    }

    // api ниже используется объектами уровня

    // добавление объекта, изменение позиции
    public void setObjectOnLocation(Object obj, GridPoint2 location) {

        // if was not in map, creation --> notify listeners

        objectsLocations.put(location, obj);
    }

    // удаление объекта
    public void releaseLocation(GridPoint2 location) {
        objectsLocations.remove(location);
    }

}
