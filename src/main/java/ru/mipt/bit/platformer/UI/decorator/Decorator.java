package ru.mipt.bit.platformer.UI.decorator;

import ru.mipt.bit.platformer.UI.Displayable;

public interface Decorator {
    Displayable getWrapped();
}
