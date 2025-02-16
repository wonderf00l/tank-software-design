package ru.mipt.bit.platformer.level.entity.filler;

public class InvalidCharacterException extends RuntimeException {
    public InvalidCharacterException(char ch) {
        super("invalid character" + ch + "was provided");
    }
}