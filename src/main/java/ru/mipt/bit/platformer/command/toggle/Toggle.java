package ru.mipt.bit.platformer.command.toggle;

import ru.mipt.bit.platformer.level.ui.LevelGraphicalObject;
import ru.mipt.bit.platformer.UI.Decorator;
import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.health.UI.HealthBarDecorator;

public class Toggle implements Command {

    private LevelGraphicalObject levelGraphic;

    public Toggle(LevelGraphicalObject levelGraphic) {
        this.levelGraphic = levelGraphic;
    }

    public void exec() {
        for (Displayable graphicalObj : levelGraphic.getGraphicalObjects()) {
            if (!(graphicalObj instanceof Decorator)) {
                continue;
            }

            Decorator deco = null;

            while (graphicalObj instanceof Decorator &&
                    !(graphicalObj instanceof HealthBarDecorator)) {

                deco = (Decorator) graphicalObj;

                graphicalObj = deco.getWrapped();
            }

            if (!(graphicalObj instanceof HealthBarDecorator)) {
                continue;
            }

            HealthBarDecorator healthDeco = (HealthBarDecorator) graphicalObj;

            healthDeco.toggle();
        }
    }

}
