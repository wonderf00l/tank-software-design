package ru.mipt.bit.platformer.level.entity.filler.file;

public class InvalidCharacterException extends RuntimeException {
    public InvalidCharacterException(char ch) {
        super("invalid character" + ch + "was provided");
    }
}