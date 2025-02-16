package ru.mipt.bit.platformer.level.entity.filler;

import java.util.ArrayList;

import ru.mipt.bit.platformer.level.entity.Level;

import ru.mipt.bit.platformer.entity.Object;

public interface LevelFiller {
    void fillLevel(Level level);

    ArrayList<Object> fetchedObjects();

    Object getPlayerObject();
}
