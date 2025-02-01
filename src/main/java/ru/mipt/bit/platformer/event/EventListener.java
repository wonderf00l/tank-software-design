package ru.mipt.bit.platformer.event;

import java.util.ArrayList;

public interface EventListener {
    public ArrayList<Event> happenedEvents();
}

// different listener impls -> provide events to default mapper -> cmd execution

// eventInterpretator - eventToCmdMapper
// defaultIterpteter - map(differently initialized)