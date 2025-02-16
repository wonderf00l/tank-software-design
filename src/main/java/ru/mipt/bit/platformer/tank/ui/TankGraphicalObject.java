package ru.mipt.bit.platformer.tank.ui;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.Drawer;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.tank.entity.Tank;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

// desktop UI, CLI, etc
public class TankGraphicalObject implements Displayable {

    private TextureRegion textureRegion;

    private Rectangle tankRectangle;

    private TileMovement timeMovement;

    private Tank tank;

    public TankGraphicalObject(Tank tank, TextureRegion textureRegion, TileMovement tileMovement) {
        this.tank = tank;

        this.textureRegion = textureRegion;

        this.tankRectangle = createBoundingRectangle(textureRegion);

        this.timeMovement = tileMovement;
    }

    public void display(Drawer drawer) {
        timeMovement.moveRectangleBetweenTileCenters(
                tankRectangle,
                tank.getCurrentCoordinates(),
                tank.getDestinationCoordinates(),
                tank.getMovementProgress());

        System.out.printf("cur coords: %d %d\n", tank.getCurrentCoordinates().x, tank.getCurrentCoordinates().y);

        System.out.printf("dest coords: %d %d\n", tank.getDestinationCoordinates().x,
                tank.getDestinationCoordinates().y);

        System.out.printf("obj mov prog: %f\n", tank.getMovementProgress());

        System.out.printf("obj rectangle: %f %f\n", tankRectangle.x, tankRectangle.y);

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
