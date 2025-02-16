package ru.mipt.bit.platformer.UI;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.mipt.bit.platformer.closer.Closable;

public class TextureProvider implements Closable {
    public static final String tankTextureImgPath = "images/tank_blue.png";
    public static final String treeTextureImgPath = "images/greenTree.png";

    private HashMap<String, TextureRegion> textureRegions = new HashMap<>();

    public TextureProvider() {
    }

    public TextureRegion getTextureRegion(String imgPath) {
        TextureRegion textureRegion;

        if (textureRegions.containsKey(imgPath)) {
            textureRegion = textureRegions.get(imgPath);
        } else {
            textureRegion = new TextureRegion(new Texture(imgPath));

            textureRegions.put(imgPath, textureRegion);
        }

        return textureRegion;
    }

    public void close() {
        for (TextureRegion textureRegion : textureRegions.values()) {
            textureRegion.getTexture().dispose();
        }
    }
}
