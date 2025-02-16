package ru.mipt.bit.platformer.UI;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

import ru.mipt.bit.platformer.closer.Closable;

import com.badlogic.gdx.graphics.g2d.Batch;

public class BatchDrawer implements Drawer, Closable {
    private Batch batch;

    public BatchDrawer(Batch batch) {
        this.batch = batch;
    }

    public void draw(Displayable obj) {
        drawTextureRegionUnscaled(
                batch, obj.getTexture(), obj.getRectangle(), obj.getRotation());
    }

    public void begin() {
        batch.begin();
    }

    public void end() {
        batch.end();
    }

    public void close() {
        batch.dispose();
    }
}
