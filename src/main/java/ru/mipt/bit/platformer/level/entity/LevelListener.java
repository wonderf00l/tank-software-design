package ru.mipt.bit.platformer.level.entity;

import ru.mipt.bit.platformer.entity.Object;

public interface LevelListener {
    void notifyAboutObjectCreation(Object object);

    void notifyAboutObjectDeletion(Object object);
}
