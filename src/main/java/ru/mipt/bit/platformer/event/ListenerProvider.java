package ru.mipt.bit.platformer.event;

import ru.mipt.bit.platformer.entity.Object;
import ru.mipt.bit.platformer.event.keyboard.KeyboardEventListener;
import ru.mipt.bit.platformer.event.random.RandomEventListener;

public class ListenerProvider {
    public static EventListener provideListener(Object obj, boolean isPlayer) {
        if (isPlayer) {
            return new KeyboardEventListener();
        }

        return new RandomEventListener();
    }
}
