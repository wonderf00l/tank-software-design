package ru.mipt.bit.platformer.tank.ui;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.GraphicalObjectProducer;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.tank.entity.Tank;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.mipt.bit.platformer.entity.Object;

public class TankGraphicalObjectProducer implements GraphicalObjectProducer {

    private TextureRegion textureRegion;
    private TileMovement tileMovement;

    public TankGraphicalObjectProducer(TextureRegion textureRegion, TileMovement tileMovement) {
        this.textureRegion = textureRegion;
        this.tileMovement = tileMovement;
    }

    public Displayable produce(Object objToDisplay) {
        return new TankGraphicalObject((Tank) objToDisplay, textureRegion, tileMovement);
    }
}
