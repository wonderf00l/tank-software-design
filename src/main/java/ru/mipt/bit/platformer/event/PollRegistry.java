package ru.mipt.bit.platformer.event;

import java.util.ArrayList;

// работаем с java.lang.Object, т.к. listener может быть привязан и к логическому, и к граф. объекту
public interface PollRegistry {
    void registerEventListener(Object obj, EventListener eventListener);

    Object getEventReceiver(EventListener eventListener);

    ArrayList<ArrayList<Event>> pollEventListeners();
}
