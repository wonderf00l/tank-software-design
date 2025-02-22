package ru.mipt.bit.platformer.obstacle.ui;

import ru.mipt.bit.platformer.UI.Drawer;
import ru.mipt.bit.platformer.entity.Object;
import ru.mipt.bit.platformer.obstacle.entity.Tree;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.UI.Displayable;

public class TreeGraphicalObject implements Displayable {

    private TextureRegion textureRegion;

    private Rectangle treeRectangle;

    private Tree tree;

    public TreeGraphicalObject(Tree tree, TextureRegion textureRegion, TiledMapTileLayer tileLayer) {
        this.tree = tree;

        this.textureRegion = textureRegion;

        this.treeRectangle = createBoundingRectangle(textureRegion);

        moveRectangleAtTileCenter(tileLayer, treeRectangle, this.tree.getLocation());
    }

    public void display(Drawer drawer) {
        drawer.draw(this);
    }

    public Rectangle getRectangle() {
        return treeRectangle;
    }

    public float getRotation() {
        return 0f;
    }

    public TextureRegion getTexture() {
        return textureRegion;
    }

    public Object getObject() {
        return tree;
    }
}
