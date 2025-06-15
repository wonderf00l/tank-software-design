package ru.mipt.bit.platformer.closer;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class TilledMapClosable implements Closable {
    private TiledMap tilledMap;

    public TilledMapClosable(TiledMap tilledMap) {
        this.tilledMap = tilledMap;
    }

    public void close() {
        tilledMap.dispose();
    }
}
