package ru.mipt.bit.platformer.closer;

public interface Closer {
    void addClosable(Closable obj);

    void close();
}
