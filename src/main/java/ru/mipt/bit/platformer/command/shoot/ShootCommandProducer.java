package ru.mipt.bit.platformer.command.shoot;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.command.CommandProducer;
import ru.mipt.bit.platformer.direction.Direction;
import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.entity.Oriented;

public class ShootCommandProducer implements CommandProducer {

    private Level level;

    public ShootCommandProducer(Level gameLevel) {
        level = gameLevel;
    }

    public Command produce(Object cmdExecutor) {
        Oriented shootingObject = (Oriented) cmdExecutor;

        GridPoint2 shootingObjLocation = shootingObject.getLocation();

        Direction bulletDirection = Direction.directionFromRotation(shootingObject.getRotation());

        return new Shoot(shootingObjLocation, bulletDirection, level);
    }
}
