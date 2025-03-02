package ru.mipt.bit.platformer.bullet.UI;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.Drawer;
import ru.mipt.bit.platformer.bullet.entity.Bullet;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.entity.Object;

public class BulletGraphicalObject implements Displayable {

    public static final String bulletTextureImgPath = "images/bullet.png";

    private TextureRegion textureRegion;

    private Rectangle bulltetRectangle;

    private TileMovement timeMovement;

    private Bullet bullet;

    public BulletGraphicalObject(Bullet bullet, TextureRegion textureRegion, TileMovement tileMovement) {
        this.bullet = bullet;

        this.textureRegion = textureRegion;

        this.bulltetRectangle = createBoundingRectangle(textureRegion);

        this.timeMovement = tileMovement;
    }

    public void display(Drawer drawer) {
        timeMovement.moveRectangleBetweenTileCenters(
                bulltetRectangle,
                bullet.getCurrentCoordinates(),
                bullet.getDestinationCoordinates(),
                bullet.getMovementProgress());

        drawer.draw(this);
    }

    public Rectangle getRectangle() {
        return bulltetRectangle;
    }

    public float getRotation() {
        return bullet.getRotation();
    }

    public TextureRegion getTexture() {
        return textureRegion;
    }

    public Object getObject() {
        return bullet;
    }
}
