package ru.mipt.bit.platformer.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.entity.Object;

// TODO: graphicalObj - view of some logical obj
// displayable - smth that just can be displyed. not linked to some logical obj, no getObject() API
public interface Displayable {
    void display(Drawer drawer);

    TextureRegion getTexture();

    Rectangle getRectangle();

    float getRotation();

    Object getObject();
}
