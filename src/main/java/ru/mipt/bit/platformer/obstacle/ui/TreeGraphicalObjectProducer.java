package ru.mipt.bit.platformer.obstacle.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.GraphicalObjectProducer;
import ru.mipt.bit.platformer.obstacle.entity.Tree;
import ru.mipt.bit.platformer.entity.Object;

public class TreeGraphicalObjectProducer implements GraphicalObjectProducer {
    private TextureRegion textureRegion;
    private TiledMapTileLayer tileLayer;

    public TreeGraphicalObjectProducer(TextureRegion textureRegion, TiledMapTileLayer tileLayer) {
        this.textureRegion = textureRegion;
        this.tileLayer = tileLayer;
    }

    public Displayable produce(Object objToDisplay) {
        return new TreeGraphicalObject((Tree) objToDisplay, textureRegion, tileLayer);
    }
}
