package ru.mipt.bit.platformer.direction;

public class InvalidRotationException extends RuntimeException {
    public InvalidRotationException(float rotation) {
        super("invalid rotation" + rotation + "was provided");
    }
}