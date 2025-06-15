package ru.mipt.bit.platformer.event;

import java.util.ArrayList;

public interface EventListener {
    ArrayList<Event> happenedEvents();
}
