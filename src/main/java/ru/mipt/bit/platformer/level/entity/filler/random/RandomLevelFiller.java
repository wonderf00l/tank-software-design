package ru.mipt.bit.platformer.level.entity.filler.random;

import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.direction.Direction;
import ru.mipt.bit.platformer.entity.Object;
import ru.mipt.bit.platformer.level.entity.filler.LevelFiller;
import ru.mipt.bit.platformer.obstacle.entity.Tree;
import ru.mipt.bit.platformer.tank.entity.Tank;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.GridPoint2;

public class RandomLevelFiller implements LevelFiller {

    private static final int OBJECTS_TYPE_QUANTITY = 2;
    private static final int OBJECTS_GEN_FACTOR = 5;
    
    private Random rand = new Random();

    private ArrayList<Object> fetchedObjects = new ArrayList<>();

    private Object objControlledByPlayer;

    public void fillLevel(Level level) {
        for (int y = 0; y < level.getHeight(); y++) {
            setObjectsOnLevelLocations(level, y);
        }
    }

    private void setObjectsOnLevelLocations(Level level, int y) {
        for (int x = 0; x < level.getWidth(); x++) {

            if (rand.nextInt(OBJECTS_GEN_FACTOR) != 0) {
                continue;
            }

            int objType = rand.nextInt(OBJECTS_TYPE_QUANTITY);

            GridPoint2 location = new GridPoint2(x, y);

            switch (objType) {
                case 0:
                    Tank tank = new Tank(
                            location,
                            Direction.RIGHT,
                            Tank.TANK_MOVEMENT_SPEED,
                            level, Tank.INIT_TANK_HEALTH);

                    level.setNewObjectOnLevel(tank, location.cpy());

                    if (objControlledByPlayer == null) {
                        objControlledByPlayer = tank;
                        break;
                    }

                    fetchedObjects.add(tank);

                    break;
                default:
                    Tree tree = new Tree(location);

                    level.setNewObjectOnLevel(tree, location);

                    fetchedObjects.add(tree);

                    break;
            }
        }
    }

    public Object getPlayerObject() {
        return objControlledByPlayer;
    }

    public ArrayList<Object> fetchedObjects() {
        return fetchedObjects;
    }
}
