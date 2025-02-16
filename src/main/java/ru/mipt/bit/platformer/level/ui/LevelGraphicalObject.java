package ru.mipt.bit.platformer.level.ui;

import java.util.HashMap;

import com.badlogic.gdx.maps.MapRenderer;

import ru.mipt.bit.platformer.level.entity.LevelListener;

import ru.mipt.bit.platformer.entity.Object;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.Drawer;
import ru.mipt.bit.platformer.UI.GraphicalObjectProducer;

public class LevelGraphicalObject implements LevelListener {

    private HashMap<Object, Displayable> levelObjects = new HashMap<>();

    private final HashMap<Class<?>, GraphicalObjectProducer> displayStrategy;

    private Drawer drawer;

    private MapRenderer levelRenderer;

    public LevelGraphicalObject(
            MapRenderer levelRenderer, Drawer drawer, HashMap<Class<?>, GraphicalObjectProducer> displayStrategy) {

        this.levelRenderer = levelRenderer;

        this.drawer = drawer;

        this.displayStrategy = displayStrategy;
    }

    public void render() {
        levelRenderer.render();

        drawer.begin();

        for (Displayable graphObj : levelObjects.values()) {
            graphObj.display(drawer);
        }

        drawer.end();
    }

    public void notifyAboutObjectCreation(Object object) {
        GraphicalObjectProducer graphObjProducer = displayStrategy.get(object.getClass());

        Displayable graphObj = graphObjProducer.produce(object);

        levelObjects.put(object, graphObj);
    }

    public void notifyAboutObjectDeletion(Object object) {
        levelObjects.remove(object);
    }
}
