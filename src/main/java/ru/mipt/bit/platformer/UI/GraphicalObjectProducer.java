package ru.mipt.bit.platformer.UI;

import ru.mipt.bit.platformer.entity.Object;

public interface GraphicalObjectProducer {
    Displayable produce(Object objToDisplay);
}
