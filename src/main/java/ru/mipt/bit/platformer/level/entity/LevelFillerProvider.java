package ru.mipt.bit.platformer.level.entity;

import java.io.BufferedReader;

import ru.mipt.bit.platformer.level.entity.filler.LevelFiller;
import ru.mipt.bit.platformer.level.entity.filler.file.FromFileLevelFiller;
import ru.mipt.bit.platformer.level.entity.filler.random.RandomLevelFiller;
import ru.mipt.bit.platformer.movement.entity.MoveManager;

public class LevelFillerProvider {
    public static final int FROM_FILE = 0;
    public static final int RANDOM = 1;

    private BufferedReader bufReader;

    private MoveManager moveManager;

    public LevelFillerProvider(BufferedReader reader, MoveManager moveManager) {
        bufReader = reader;
        this.moveManager = moveManager;
    }

    LevelFiller getLevelFiller(int fillerType) {
        if (fillerType == FROM_FILE) {
            return new FromFileLevelFiller(bufReader, moveManager);
        }

        return new RandomLevelFiller(moveManager);
    }
}
