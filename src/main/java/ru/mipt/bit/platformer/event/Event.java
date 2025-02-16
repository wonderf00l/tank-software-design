package ru.mipt.bit.platformer.event;

public class Event {
    private final Integer type;
    private final EventListener whoGot;

    public Event(Integer type, EventListener whoGot) {
        this.type = type;
        this.whoGot = whoGot;
    }

    public Integer getType() {
        return type;
    }

    public EventListener whoGot() {
        return whoGot;
    }
}
