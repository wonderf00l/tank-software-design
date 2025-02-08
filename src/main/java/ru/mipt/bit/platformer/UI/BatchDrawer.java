package ru.mipt.bit.platformer.UI;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

import com.badlogic.gdx.graphics.g2d.Batch;

public class BatchDrawer implements Drawer {
    private Batch batch;

    public BatchDrawer(Batch batch) {
        this.batch = batch;
    }

    public void draw(Displayable obj) {
        drawTextureRegionUnscaled(
                batch, obj.getTexture(), obj.getRectangle(), obj.getRotation());
    }

    public void close() {
        batch.dispose();
    }
}
