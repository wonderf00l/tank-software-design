package ru.mipt.bit.platformer.health.UI;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.Drawer;
import ru.mipt.bit.platformer.UI.TextureProvider;
import ru.mipt.bit.platformer.UI.decorator.Decorator;
import ru.mipt.bit.platformer.closer.Closable;
import ru.mipt.bit.platformer.entity.Toggable;
import ru.mipt.bit.platformer.entity.Healthy;
import ru.mipt.bit.platformer.entity.Object;

public class HealthBarDecorator implements Decorator, Displayable, Toggable, Closable {

    private boolean isToggled = false;

    private Displayable wrappedGraphObj;

    private Healthy healthyObj;

    private ArrayList<Disposable> disposables = new ArrayList<>();

    public HealthBarDecorator(Displayable objectToWrap) {
        wrappedGraphObj = objectToWrap;

        healthyObj = (Healthy) wrappedGraphObj.getObject();
    }

    public void display(Drawer drawer) {
        wrappedGraphObj.display(drawer);

        if (!isToggled) {
            return;
        }

        Pixmap curHealthBarPixmap = TextureProvider.getPixmap(
                HealthBarGraphicalObject.getCurHealthBarPixmapWidth(healthyObj.getHealth()),
                HealthBarGraphicalObject.HEALTH_BAR_PIXMAP_HEIGHT,
                Color.RED);

        TextureRegion curHealthTextureRegion = TextureProvider.getTextureRegionFromPixmap(curHealthBarPixmap);

        HealthBarGraphicalObject curHealthBarObj = new HealthBarGraphicalObject(
                curHealthTextureRegion,
                wrappedGraphObj.getRectangle());

        drawer.draw(curHealthBarObj);

        disposables.add(curHealthTextureRegion.getTexture());
    }

    public TextureRegion getTexture() {
        return wrappedGraphObj.getTexture();
    }

    public Rectangle getRectangle() {
        return wrappedGraphObj.getRectangle();
    }

    public float getRotation() {
        return wrappedGraphObj.getRotation();
    }

    public Displayable getWrapped() {
        return wrappedGraphObj;
    }

    public void toggle() {
        isToggled = !isToggled;
    }

    public Object getObject() {
        return wrappedGraphObj.getObject();
    }

    public void close() {
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
    }
}
