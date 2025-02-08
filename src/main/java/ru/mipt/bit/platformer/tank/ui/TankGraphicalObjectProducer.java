package ru.mipt.bit.platformer.tank.ui;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.GraphicalObjectProducer;
import ru.mipt.bit.platformer.UI.TextureProvider;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.tank.entity.Tank;
import ru.mipt.bit.platformer.tank.ui.TankGraphicalObject;

public class TankGraphicalObjectProducer implements GraphicalObjectProducer {

    private TextureProvider textureProvider;
    private TileMovement tileMovement;

    public TankGraphicalObjectProducer(TextureProvider textureProvider, TileMovement tileMovement) {
        this.textureProvider = textureProvider;
        this.tileMovement = tileMovement;
    }

    public Displayable produce(Object objToDisplay) {
        return new TankGraphicalObject((Tank) objToDisplay, textureProvider, tileMovement);
    }
}
