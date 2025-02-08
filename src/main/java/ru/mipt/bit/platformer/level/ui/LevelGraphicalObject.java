package ru.mipt.bit.platformer.level.ui;

import java.util.HashMap;

import com.badlogic.gdx.maps.MapRenderer;

import ru.mipt.bit.platformer.level.entity.LevelListener;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.Drawer;
import ru.mipt.bit.platformer.UI.GraphicalObjectProducer;

public class LevelGraphicalObject implements LevelListener {

    private final HashMap<Object, Displayable> levelObjects = new HashMap<>();

    private final HashMap<Class<?>, GraphicalObjectProducer> displayStrategy;

    private final Drawer drawer;

    private final MapRenderer levelRenderer;

    public LevelGraphicalObject(
            MapRenderer levelRenderer, Drawer drawer, HashMap<Class<?>, GraphicalObjectProducer> displayStrategy) {

        this.levelRenderer = levelRenderer;

        this.drawer = drawer;

        this.displayStrategy = displayStrategy;
    }

    public void render() {
        levelRenderer.render();

        for (Displayable graphObj : levelObjects.values()) {
            graphObj.display(drawer);
        }
    }

    // methods to invoke on a publisher side: add/del graphical obj

    // get graphical obj from logic-graphic mapping

}
