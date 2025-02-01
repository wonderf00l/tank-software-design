package ru.mipt.bit.platformer.command;

// шаблон, т.к. команду можно применить и к графике 
public interface CommandProducer<T> {
    Command produce(T cmdExecutor);
}
