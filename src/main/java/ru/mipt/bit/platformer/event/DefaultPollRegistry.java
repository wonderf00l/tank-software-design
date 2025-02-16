package ru.mipt.bit.platformer.event;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mipt.bit.platformer.entity.Object;

// default listeners polling in sync mode
public class DefaultPollRegistry implements PollRegistry {
    private HashMap<EventListener, Object> registry = new HashMap<>();

    public void registerEventListener(Object obj, EventListener eventListener) {
        registry.put(eventListener, obj);
    }

    public Object getEventReceiver(EventListener eventListener) {
        return registry.get(eventListener);
    }

    public ArrayList<ArrayList<Event>> pollEventListeners() {
        ArrayList<ArrayList<Event>> happenedEvents = new ArrayList<ArrayList<Event>>();

        for (EventListener eListener : registry.keySet()) {
            happenedEvents.add(eListener.happenedEvents());
        }

        return happenedEvents;
    }
}
