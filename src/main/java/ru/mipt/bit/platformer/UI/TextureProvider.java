package ru.mipt.bit.platformer.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureProvider {
    public static final String tankTextureImgPath = "images/tank_blue.png";
    public static final String treeTextureImgPath = "images/greenTree.png";

    public static TextureRegion getTextureRegionFromImgPath(String imgPath) {
        return new TextureRegion(new Texture(imgPath));
    }

    public static TextureRegion getTextureRegionFromPixmap(Pixmap pixmap) {
        return new TextureRegion(new Texture(pixmap));
    }

    public static Pixmap getPixmap(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, width, 20);

        return pixmap;
    }
}
