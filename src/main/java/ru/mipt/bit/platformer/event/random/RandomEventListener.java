package ru.mipt.bit.platformer.event.random;

import java.util.ArrayList;
import java.util.Random;

import ru.mipt.bit.platformer.event.Event;
import ru.mipt.bit.platformer.event.EventListener;

public class RandomEventListener implements EventListener {

    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    public static final int SHOOT = 4;

    private static final int EVENT_GEN_FACTOR = 20;
    private static final int EVENT_COUNT = 5;

    private Random rand = new Random();

    public ArrayList<Event> happenedEvents() {
        ArrayList<Event> happenedEvents = new ArrayList<>();

        if (rand.nextInt(EVENT_GEN_FACTOR) != 0) {
            return happenedEvents;
        }

        happenedEvents.add(new Event(rand.nextInt(EVENT_COUNT), this));

        return happenedEvents;
    }
}
