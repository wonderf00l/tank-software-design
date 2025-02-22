package ru.mipt.bit.platformer.UI;

public interface DecoratorProducer {
    Decorator produce(Displayable objToWrap);
}
