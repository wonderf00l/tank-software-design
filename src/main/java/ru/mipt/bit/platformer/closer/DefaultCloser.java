package ru.mipt.bit.platformer.closer;

import java.util.ArrayList;

public class DefaultCloser implements Closer {

    private ArrayList<Closable> registry = new ArrayList<>();

    public void addClosable(Closable obj) {
        registry.add(obj);
    }

    public void close() {
        for (Closable obj : registry) {
            obj.close();
        }
    }
}
