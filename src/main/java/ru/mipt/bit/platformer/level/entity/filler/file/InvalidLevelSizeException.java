package ru.mipt.bit.platformer.level.entity.filler.file;

public class InvalidLevelSizeException extends RuntimeException {
    public InvalidLevelSizeException() {
        super("invalid level size was provided");
    }
}
