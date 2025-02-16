package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.entity.Object;

public interface CommandProducer {
    Command produce(Object cmdExecutor);
}
