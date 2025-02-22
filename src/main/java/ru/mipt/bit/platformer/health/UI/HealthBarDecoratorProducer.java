package ru.mipt.bit.platformer.health.UI;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.Decorator;
import ru.mipt.bit.platformer.UI.DecoratorProducer;

public class HealthBarDecoratorProducer implements DecoratorProducer {
    public Decorator produce(Displayable objToWrap) {
        return new HealthBarDecorator(objToWrap);
    }
}
