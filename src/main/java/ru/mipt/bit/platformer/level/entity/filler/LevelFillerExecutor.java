package ru.mipt.bit.platformer.level.entity.filler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ru.mipt.bit.platformer.level.entity.Level;

import ru.mipt.bit.platformer.level.entity.filler.file.FromFileLevelFiller;
import ru.mipt.bit.platformer.level.entity.filler.random.RandomLevelFiller;

public class LevelFillerExecutor {
    private static String LVL_FILE_PATH = "src/main/resources/level.txt";

    public static final int FROM_FILE = 0;
    public static final int RANDOM = 1;

    private LevelFiller levelFiller;

    public LevelFillerExecutor(int fillStrategy) {
        setLevelFillerFromFillStrategy(fillStrategy);
    }

    private void setLevelFillerFromFillStrategy(int strategy) {
        if (strategy == FROM_FILE) {
            FileReader levelFile = null;

            try {
                levelFile = new FileReader(LVL_FILE_PATH);

                levelFiller = new FromFileLevelFiller(
                        new BufferedReader(levelFile));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        levelFiller = new RandomLevelFiller();
    }

    public void fillLevel(Level level) {
        levelFiller.fillLevel(level);
    }

    public LevelFiller getFiller() {
        return levelFiller;
    }
}
