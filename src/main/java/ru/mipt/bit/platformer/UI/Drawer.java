package ru.mipt.bit.platformer.UI;

import ru.mipt.bit.platformer.closer.Closable;

public interface Drawer extends Closable {
    void draw(Displayable obj);
}
