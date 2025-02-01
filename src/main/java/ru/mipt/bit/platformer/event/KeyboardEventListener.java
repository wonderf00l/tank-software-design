package ru.mipt.bit.platformer.event;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys.*;

public class KeyboardEventListener implements EventListener {

    private static int[] keysToCheck = { W, A, S, D };

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
