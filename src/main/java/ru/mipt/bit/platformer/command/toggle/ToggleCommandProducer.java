package ru.mipt.bit.platformer.command.toggle;

import ru.mipt.bit.platformer.level.ui.LevelGraphicalObject;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.command.CommandProducer;
import ru.mipt.bit.platformer.entity.Object;

public class ToggleCommandProducer implements CommandProducer {

    public ToggleCommandProducer() {
    }

    public Command produce(Object obj) {
        return new Toggle((LevelGraphicalObject) obj);
    }
}
