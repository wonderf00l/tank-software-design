package ru.mipt.bit.platformer.event;

public class Event {
    private final Integer type;

    Event(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
