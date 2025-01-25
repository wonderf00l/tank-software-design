package ru.mipt.bit.platformer.tank.ui;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

import ru.mipt.bit.platformer.UI.TextureProvider;

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
public class DesktopUI implements UI {

    private final Rectangle tankRectangle;

    private final TextureProvider textureProvider;

    // batch

    public DesktopUI(TextureProvider textureProvider) {
        this.textureProvider = textureProvider;

        TextureRegion textureRegion = textureProvider.getTextureRegion();

        this.tankRectangle = createBoundingRectangle(textureRegion);
    }

    public void DisplayTank() {
        // moveTankRectange
        // batch.region.draw
    }
}
