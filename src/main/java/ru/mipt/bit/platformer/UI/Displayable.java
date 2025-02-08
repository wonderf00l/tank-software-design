package ru.mipt.bit.platformer.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public interface Displayable {
    public void display(Drawer drawer);

    public TextureRegion getTexture();

    public Rectangle getRectangle();

    public float getRotation();
}
