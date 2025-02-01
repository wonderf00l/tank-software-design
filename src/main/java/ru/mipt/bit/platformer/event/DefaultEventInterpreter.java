package ru.mipt.bit.platformer.event;

import java.util.HashMap;

import ru.mipt.bit.platformer.command.Command;

public class DefaultEventInterpreter implements EventInterpreter {

    private final HashMap<Event, Command> eventToCmdMapping;

    public DefaultEventInterpreter(HashMap<Event, Command> eventToCmdMapping) {
        this.eventToCmdMapping = eventToCmdMapping;
    }

    public Command eventToCommand(Event event) {
        return eventToCmdMapping.get(event);
    }
}
