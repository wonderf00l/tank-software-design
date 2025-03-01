package ru.mipt.bit.platformer.level.entity.filler.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.Direction;
import ru.mipt.bit.platformer.entity.Object;
import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.level.entity.filler.LevelFiller;
import ru.mipt.bit.platformer.movement.entity.MoveManager;
import ru.mipt.bit.platformer.obstacle.entity.Tree;
import ru.mipt.bit.platformer.tank.entity.Tank;

public class FromFileLevelFiller implements LevelFiller {
    private static final char PLAYER = 'X';
    private static final char TANK = 'Y';
    private static final char TREE = 'T';
    private static final char EMPTY_CELL = '_';

    private BufferedReader bufReader;

    private MoveManager moveManager;

    private ArrayList<Object> fetchedObjects = new ArrayList<>();

    // потенциально может быть несколько, для простоты храним один
    private Object objControlledByPlayer;

    public FromFileLevelFiller(BufferedReader reader, MoveManager moveManager) {
        bufReader = reader;

        this.moveManager = moveManager;
    }

    public void fillLevel(Level level) throws InvalidLevelSizeException {
        int fileLevelHeight = 0;

        try {
            String line;

            while ((line = bufReader.readLine()) != null) {
                if (fileLevelHeight > level.getHeight() || line.length() > level.getWidth()) {
                    RuntimeException e = new InvalidLevelSizeException();

                    e.printStackTrace();

                    throw e;
                }

                setLineObjectsOnLevelLocations(line, fileLevelHeight, level);

                fileLevelHeight++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLineObjectsOnLevelLocations(
            String fileLine, int lineIdx, Level level)
            throws InvalidCharacterException {

        for (int i = 0; i < fileLine.length(); i++) {
            GridPoint2 location = new GridPoint2(i, (level.getHeight() - 1) - lineIdx);

            char cellCh = fileLine.charAt(i);

            switch (cellCh) {
                case PLAYER, TANK:
                    Tank tank = new Tank(
                            location,
                            Direction.RIGHT,
                            moveManager,
                            level, Tank.INIT_TANK_HEALTH);

                    level.setNewObjectOnLevel(tank, location);

                    if (cellCh == PLAYER) {
                        objControlledByPlayer = tank;
                        break;
                    }

                    fetchedObjects.add(tank);

                    break;
                case TREE:
                    Tree tree = new Tree(location);

                    level.setNewObjectOnLevel(tree, location);

                    fetchedObjects.add(tree);

                    break;
                default:
                    if (cellCh != EMPTY_CELL) {
                        RuntimeException e = new InvalidCharacterException(cellCh);

                        e.printStackTrace();

                        throw e;
                    }

                    break;
            }
        }
    }

    // use in cmd as executor
    public Object getPlayerObject() {
        return objControlledByPlayer;
    }

    public ArrayList<Object> fetchedObjects() {
        return fetchedObjects;
    }
}
