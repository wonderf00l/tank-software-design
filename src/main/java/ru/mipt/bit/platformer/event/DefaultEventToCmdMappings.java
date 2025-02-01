package ru.mipt.bit.platformer.event;

import static com.badlogic.gdx.Input.Keys.*;

import java.util.Map;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.command.Move;

// provide level, create cmdProducer
public class DefaultEventToCmdMappings {
    public static final Map<Event, Command> KEYBOARD_EVENTS_MAPPING = Map.of(
            new Event(W), new Move(null, null, false));
}
