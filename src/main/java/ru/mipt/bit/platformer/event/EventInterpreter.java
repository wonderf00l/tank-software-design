package ru.mipt.bit.platformer.event;

import ru.mipt.bit.platformer.command.Command;

public interface EventInterpreter {
    public Command eventToCommand(Event event);
}
