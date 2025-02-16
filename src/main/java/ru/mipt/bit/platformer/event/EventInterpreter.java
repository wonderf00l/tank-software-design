package ru.mipt.bit.platformer.event;

import java.util.HashMap;

import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.command.CommandProducer;
import ru.mipt.bit.platformer.entity.Object;

// дефолтная реализация интерпретатора событий:
// каждому объекту назначается свой мапинг событий на команды - возможность гибкой настройки
// согласно текущим требованиям для всех танков, кроме танка игрока, будет использоваться одна мапа
// аналогично для таких танков будет использовать одна и та же ссылка на RandomEventListener для экономии памяти
public class EventInterpreter {

    // Integer is an event type
    private final HashMap<Object, HashMap<Integer, CommandProducer>> interpretationStrategies = new HashMap<>();

    public void addMappingForObject(Object gameObj, HashMap<Integer, CommandProducer> eventToCmdMapping) {
        interpretationStrategies.put(gameObj, eventToCmdMapping);
    }

    public Command eventToCommandForObject(Object gameObj, Event event) {
        HashMap<Integer, CommandProducer> eventInterpretationStrategyForObject = interpretationStrategies.get(gameObj);

        CommandProducer cmdProducerForObject = eventInterpretationStrategyForObject.get(event.getType());

        Command cmdForObjectToExecute = cmdProducerForObject.produce(gameObj);

        return cmdForObjectToExecute;
    }
}
