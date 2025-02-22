package ru.mipt.bit.platformer.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.entity.Object;

// graphicalObj - displayable that is above some logical obj

// displayable - smth that just can be displyed. not linked to some logical obj
public interface Displayable {
    void display(Drawer drawer);

    TextureRegion getTexture();

    Rectangle getRectangle();

    float getRotation();

    Object getObject();
}
