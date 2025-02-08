package ru.mipt.bit.platformer.tank.ui;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.TextureProvider;
import ru.mipt.bit.platformer.UI.Drawer;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.tank.entity.Tank;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/*
 * 
 *  rectangle as a dependency
 * 
 * getRectangle(textureRegion) { 
 * 
 *  
 * 
 * }
 * 
 * textureProvider interface 
 * 
 * tankTextureProvider {
 * 
 *  // imgProvider interface - from network(for storage economy), fs etc
 * 
 *  texture
 * 
 *  getTextureRegion(texture - NewTexture(imgPath))
 *  
 *      return NewTexureReg()
 * 
 * }
 * 
 */

// disposable
// drawable 

// console UI, etc
public class TankGraphicalObject implements Displayable {

    private final TextureRegion textureRegion;

    private final Rectangle tankRectangle;

    private final TileMovement timeMovement;

    private final Tank tank;

    public TankGraphicalObject(Tank tank, TextureProvider textureProvider, TileMovement tileMovement) {
        this.tank = tank;

        this.textureRegion = textureProvider.getTextureRegion();

        this.tankRectangle = createBoundingRectangle(textureRegion);

        this.timeMovement = tileMovement;
    }

    public void display(Drawer drawer) {
        timeMovement.moveRectangleBetweenTileCenters(
                tankRectangle,
                tank.getCurrentCoordinates(),
                tank.getDestinationCoordinates(),
                tank.getRotation());

        drawer.draw(this);
    }

    public Rectangle getRectangle() {
        return tankRectangle;
    }

    public float getRotation() {
        return tank.getRotation();
    }

    public TextureRegion getTexture() {
        return textureRegion;
    }
}
