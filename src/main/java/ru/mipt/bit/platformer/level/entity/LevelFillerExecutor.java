package ru.mipt.bit.platformer.level.entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ru.mipt.bit.platformer.level.entity.filler.LevelFiller;
import ru.mipt.bit.platformer.level.entity.filler.file.FromFileLevelFiller;
import ru.mipt.bit.platformer.level.entity.filler.random.RandomLevelFiller;
import ru.mipt.bit.platformer.movement.entity.MoveManager;

public class LevelFillerExecutor {
    private static String LVL_FILE_PATH = "src/main/resources/level.txt";

    public static final int FROM_FILE = 0;
    public static final int RANDOM = 1;

    private LevelFiller levelFiller;

    private MoveManager moveManager;

    public LevelFillerExecutor(MoveManager moveManager, int fillStrategy) {
        this.moveManager = moveManager;

        setLevelFillerFromFillStrategy(fillStrategy);
    }

    private void setLevelFillerFromFillStrategy(int strategy) {
        if (strategy == FROM_FILE) {
            FileReader levelFile = null;

            try {
                levelFile = new FileReader(LVL_FILE_PATH);

                levelFiller = new FromFileLevelFiller(
                        new BufferedReader(levelFile), moveManager);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        levelFiller = new RandomLevelFiller(moveManager);
    }

    public void fillLevel(Level level) {
        levelFiller.fillLevel(level);
    }

    public LevelFiller getFiller() {
        return levelFiller;
    }
}
