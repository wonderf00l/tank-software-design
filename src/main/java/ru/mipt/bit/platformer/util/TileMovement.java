package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TileMovement {

    private final TiledMapTileLayer tileLayer;
    private final Interpolation interpolation;

    public TileMovement(TiledMapTileLayer tileLayer, Interpolation interpolation) {
        this.tileLayer = tileLayer;
        this.interpolation = interpolation;
    }

    public Rectangle moveRectangleBetweenTileCenters1(
            Rectangle rectangle,
            float fromTileBottomLeftX, float fromTileBottomLeftY,
            float toTileBottomLeftX, float toTileBottomLeftY,
            float progress) {
        // подсчет bottom left x, y можно делать единожды вне этой функции. само же
        // перемещение заключается в получении новых intermediate boot left x/y
        float intermediateBottomLeftX = interpolation.apply(fromTileBottomLeftX, toTileBottomLeftX, progress);
        float intermediateBottomLeftY = interpolation.apply(fromTileBottomLeftY, toTileBottomLeftY, progress);

        return rectangle
                .setX(intermediateBottomLeftX)
                .setY(intermediateBottomLeftY);
    }

    public Rectangle moveRectangleBetweenTileCenters(Rectangle rectangle, GridPoint2 fromTileCoordinates,
            GridPoint2 toTileCoordinates, float progress) {

        moveRectangleAtTileCenter(tileLayer, rectangle, fromTileCoordinates);
        float fromTileBottomLeftX = rectangle.x;
        float fromTileBottomLeftY = rectangle.y;

        moveRectangleAtTileCenter(tileLayer, rectangle, toTileCoordinates);
        float toTileBottomLeftX = rectangle.x;
        float toTileBottomLeftY = rectangle.y;

        float intermediateBottomLeftX = interpolation.apply(fromTileBottomLeftX, toTileBottomLeftX, progress);
        float intermediateBottomLeftY = interpolation.apply(fromTileBottomLeftY, toTileBottomLeftY, progress);

        return rectangle
                .setX(intermediateBottomLeftX)
                .setY(intermediateBottomLeftY);
    }
}
