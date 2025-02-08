package ru.mipt.bit.platformer.command;

public interface CommandProducer {
    Command produce(Object cmdExecutor);
}
