package ru.mipt.bit.platformer.UI.decorator;

import java.util.HashMap;
import java.util.function.Predicate;

import ru.mipt.bit.platformer.health.UI.HealthBarDecoratorProducer;
import ru.mipt.bit.platformer.entity.Object;
import ru.mipt.bit.platformer.entity.Healthy;

public class ApplyDecoratorStrategy {
    HashMap<DecoratorProducer, Predicate<Object>> strategy = new HashMap<>();

    public ApplyDecoratorStrategy() {
        initStrategy();
    }

    public HashMap<DecoratorProducer, Predicate<Object>> getStrategy() {
        return strategy;
    }

    private void initStrategy() {
        strategy.put(new HealthBarDecoratorProducer(), obj -> obj instanceof Healthy);
    }
}
