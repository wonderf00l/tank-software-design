package ru.mipt.bit.platformer.event.keyboard;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import ru.mipt.bit.platformer.event.Event;
import ru.mipt.bit.platformer.event.EventListener;

import static com.badlogic.gdx.Input.Keys.*;

public class KeyboardEventListener implements EventListener {

    private static int[] keysToCheck = { W, UP, A, LEFT, S, DOWN, D, RIGHT };

    public ArrayList<Event> happenedEvents() {
        ArrayList<Event> happenedEvents = new ArrayList<>();

        for (int key : keysToCheck) {
            if (Gdx.input.isKeyPressed(key)) {
                happenedEvents.add(new Event(key));
            }
        }

        return happenedEvents;
    }
}
