package ru.mipt.bit.platformer.closer;

import com.badlogic.gdx.graphics.Texture;

public class ClosableTexture implements Closable {
    private Texture texture;

    public ClosableTexture(Texture texture) {
        this.texture = texture;
    }

    public void close() {
        texture.dispose();
    }
}
