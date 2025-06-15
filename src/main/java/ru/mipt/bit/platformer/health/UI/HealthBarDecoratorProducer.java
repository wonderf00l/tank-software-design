package ru.mipt.bit.platformer.health.UI;

import ru.mipt.bit.platformer.UI.Displayable;
import ru.mipt.bit.platformer.UI.decorator.Decorator;
import ru.mipt.bit.platformer.UI.decorator.DecoratorProducer;

public class HealthBarDecoratorProducer implements DecoratorProducer {
    public Decorator produce(Displayable objToWrap) {
        return new HealthBarDecorator(objToWrap);
    }
}
