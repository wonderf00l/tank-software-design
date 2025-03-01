package ru.mipt.bit.platformer.event;

import java.util.ArrayList;

public interface PollRegistry {
    void registerEventListener(Object obj, EventListener eventListener);

    Object getEventReceiver(EventListener eventListener);

    ArrayList<ArrayList<Event>> pollEventListeners();
}
