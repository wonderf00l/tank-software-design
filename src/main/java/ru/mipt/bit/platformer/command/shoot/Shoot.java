package ru.mipt.bit.platformer.command.shoot;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.bullet.entity.Bullet;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.direction.Direction;
import ru.mipt.bit.platformer.entity.Healthy;
import ru.mipt.bit.platformer.level.entity.Level;

public class Shoot implements Command {

    private GridPoint2 shootingObjLocation;
    private final Direction direction;
    private Level level;

    public Shoot(GridPoint2 shootingObjLocation, Direction bulletDirection, Level gameLevel) {
        this.shootingObjLocation = shootingObjLocation;
        direction = bulletDirection;
        level = gameLevel;
    }

    public void exec() {
        GridPoint2 bulletInitLocation = shootingObjLocation.cpy().add(direction.getCoordsDelta());

        boolean canShoot = level.isLocationWithinLevel(bulletInitLocation);

        if (!canShoot) {
            return;
        }

        Bullet bullet = new Bullet(bulletInitLocation, direction, Bullet.BULLET_MOVEMENT_SPEED, level);

        Object obj = level.getObjectOnLocation(bulletInitLocation);

        if (obj != null) {
            if (obj instanceof Healthy) {
                bullet.hit((Healthy) obj);
            }

            return;
        }

        level.setNewObjectOnLevel(bullet, bulletInitLocation.cpy());

        bullet.move(direction, true);
    }
}
