package ru.mipt.bit.platformer.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DefaultTextureProvider {

    private final Texture texture;

    // можно передавать и саму текстуру как зависимость
    public DefaultTextureProvider(String imgPath) {
        this.texture = new Texture(imgPath);
    }

    public TextureRegion getTextureRegion() {
        return new TextureRegion(this.texture);
    }

}
