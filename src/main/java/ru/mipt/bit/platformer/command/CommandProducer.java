package ru.mipt.bit.platformer.command;

// используется java.lang.Object, т.к. команды применимы как к логическим сущностям, так и к графическим
// кастим к конкретному типу, нужному конкретному типу команды, в рантайме
public interface CommandProducer {
    Command produce(Object cmdExecutor);
}
