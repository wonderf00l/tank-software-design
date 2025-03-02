package ru.mipt.bit.platformer.bullet.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.GraphicalObjectProducer;
import ru.mipt.bit.platformer.bullet.entity.Bullet;
import ru.mipt.bit.platformer.util.TileMovement;

import ru.mipt.bit.platformer.entity.Object;

public class BulletGraphicalObjectProducer implements GraphicalObjectProducer {

    private TextureRegion textureRegion;
    private TileMovement tileMovement;

    public BulletGraphicalObjectProducer(TextureRegion textureRegion, TileMovement tileMovement) {
        this.textureRegion = textureRegion;
        this.tileMovement = tileMovement;
    }

    public Displayable produce(Object objToDisplay) {
        return new BulletGraphicalObject((Bullet) objToDisplay, textureRegion, tileMovement);
    }
}
