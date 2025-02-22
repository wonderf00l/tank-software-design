package ru.mipt.bit.platformer.health.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.Drawer;

import ru.mipt.bit.platformer.entity.Object;

public class HealthBarGraphicalObject implements Displayable {
    private TextureRegion textureRegion;

    private Rectangle healthBarRectangle;

    public static int FULL_HEALTH_BAR_PIXMAP_WIDTH = 100;

    public static int HEALTH_BAR_PIXMAP_HEIGHT = 20;

    public static int getCurHealthBarPixmapWidth(float health) {
        return (int) (90 * health / 100);
    }

    public HealthBarGraphicalObject(TextureRegion textureRegion, Rectangle objRectangle) {
        this.textureRegion = textureRegion;

        healthBarRectangle = new Rectangle(objRectangle);

        healthBarRectangle.y += 90;
    }

    public void display(Drawer drawer) {
        drawer.draw(this);
    }

    public Rectangle getRectangle() {
        return healthBarRectangle;
    }

    public float getRotation() {
        return 0f;
    }

    public TextureRegion getTexture() {
        return textureRegion;
    }

    public Object getObject() {
        return null; // пока null для getObj(), позже разграничить
    }
}
