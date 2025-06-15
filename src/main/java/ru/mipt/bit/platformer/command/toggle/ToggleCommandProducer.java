package ru.mipt.bit.platformer.command.toggle;

import ru.mipt.bit.platformer.level.ui.LevelGraphicalObject;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.command.CommandProducer;

public class ToggleCommandProducer implements CommandProducer {
    public Command produce(Object obj) {
        return new Toggle((LevelGraphicalObject) obj);
    }
}
