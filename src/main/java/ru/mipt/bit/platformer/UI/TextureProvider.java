package ru.mipt.bit.platformer.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mipt.bit.platformer.closer.Closable;

public interface TextureProvider extends Closable {
    TextureRegion getTextureRegion();
}
