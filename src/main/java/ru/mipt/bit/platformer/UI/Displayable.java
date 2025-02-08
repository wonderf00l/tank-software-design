package ru.mipt.bit.platformer.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public interface Displayable {
    void display(Drawer drawer);

    TextureRegion getTexture();

    Rectangle getRectangle();

    float getRotation();
}
