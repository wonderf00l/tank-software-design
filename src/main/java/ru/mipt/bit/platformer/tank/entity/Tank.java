package ru.mipt.bit.platformer.tank.entity;

import ru.mipt.bit.platformer.entity.Movable;
import ru.mipt.bit.platformer.entity.Oriented;
import ru.mipt.bit.platformer.entity.Updatable;

// potentially will have tanks with different move, shoot logic
public interface Tank extends Updatable, Oriented, Movable {
}
