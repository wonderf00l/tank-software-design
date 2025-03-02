package ru.mipt.bit.platformer.level.ui;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.Collection;

import com.badlogic.gdx.maps.MapRenderer;

import ru.mipt.bit.platformer.level.entity.LevelListener;

import ru.mipt.bit.platformer.entity.Object;
import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.Drawer;
import ru.mipt.bit.platformer.UI.GraphicalObjectProducer;
import ru.mipt.bit.platformer.UI.decorator.DecoratorProducer;
import ru.mipt.bit.platformer.closer.Closable;

public class LevelGraphicalObject implements LevelListener {

    private IdentityHashMap<Object, Displayable> levelObjects = new IdentityHashMap<>();

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

        // освобождение промежуточных ресурсов - чтобы не копились
        for (Displayable graphObj : levelObjects.values()) {
            if (graphObj instanceof Closable) {
                ((Closable) graphObj).close();
            }
        }
    }

    public void notifyAboutObjectCreation(Object object) {
        GraphicalObjectProducer graphObjProducer = displayStrategy.get(object.getClass());

        Displayable graphObj = graphObjProducer.produce(object);

        levelObjects.put(object, graphObj);
    }

    public void notifyAboutObjectDeletion(Object object) {
        levelObjects.remove(object);
    }

    public void applyGraphicDecorators(HashMap<DecoratorProducer, Predicate<Object>> applyStrategy) {
        for (Map.Entry<DecoratorProducer, Predicate<Object>> entry : applyStrategy.entrySet()) {
            for (Map.Entry<Object, Displayable> objsEntry : levelObjects.entrySet()) {
                Predicate<Object> shouldApplyDeco = entry.getValue();

                Object obj = objsEntry.getKey();

                if (!shouldApplyDeco.test(obj)) {
                    continue;
                }

                Displayable graphObj = objsEntry.getValue();

                DecoratorProducer decoProd = entry.getKey();

                Displayable decoratedGraphicalObj = (Displayable) decoProd.produce(graphObj);

                levelObjects.put(obj, decoratedGraphicalObj);
            }
        }
    }

    public Collection<Displayable> getGraphicalObjects() {
        return levelObjects.values();
    }
}
