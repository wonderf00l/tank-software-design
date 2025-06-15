package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.command.CommandProducer;
import ru.mipt.bit.platformer.event.keyboard.KeyboardEventListener;
import ru.mipt.bit.platformer.event.keyboard.KeyboardEventToCmdMapping;
import ru.mipt.bit.platformer.event.random.RandomEventListener;
import ru.mipt.bit.platformer.event.random.RandomEventToCmdMapping;
import ru.mipt.bit.platformer.event.EventInterpreter;
import ru.mipt.bit.platformer.event.PollRegistry;
import ru.mipt.bit.platformer.event.DefaultPollRegistry;
import ru.mipt.bit.platformer.event.Event;
import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.level.entity.filler.LevelFiller;
import ru.mipt.bit.platformer.level.entity.filler.LevelFillerExecutor;
import ru.mipt.bit.platformer.level.ui.LevelGraphicalObject;
import ru.mipt.bit.platformer.UI.BatchDrawer;
import ru.mipt.bit.platformer.UI.GraphicalObjectProducer;
import ru.mipt.bit.platformer.UI.TextureProvider;
import ru.mipt.bit.platformer.UI.decorator.ApplyDecoratorStrategy;
import ru.mipt.bit.platformer.UI.decorator.DecoratorProducer;
import ru.mipt.bit.platformer.bullet.entity.Bullet;
import ru.mipt.bit.platformer.tank.entity.Tank;
import ru.mipt.bit.platformer.tank.ui.TankGraphicalObject;
import ru.mipt.bit.platformer.tank.ui.TankGraphicalObjectProducer;
import ru.mipt.bit.platformer.obstacle.entity.Tree;
import ru.mipt.bit.platformer.obstacle.ui.TreeGraphicalObject;
import ru.mipt.bit.platformer.obstacle.ui.TreeGraphicalObjectProducer;
import ru.mipt.bit.platformer.bullet.UI.BulletGraphicalObject;
import ru.mipt.bit.platformer.bullet.UI.BulletGraphicalObjectProducer;
import ru.mipt.bit.platformer.entity.Object;
import ru.mipt.bit.platformer.entity.Updatable;
import ru.mipt.bit.platformer.event.EventListener;
import ru.mipt.bit.platformer.closer.ClosableTexture;
import ru.mipt.bit.platformer.closer.Closer;
import ru.mipt.bit.platformer.closer.DefaultCloser;
import ru.mipt.bit.platformer.closer.TilledMapClosable;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Level gameLevel; // логическое представление уровня

    private LevelGraphicalObject levelGraphic; // графическое представление уровня

    private PollRegistry pollRegistry; // registry event listener'ов: keyboard, random etc

    private EventInterpreter eventInterpreter; // интерпретатор событий event listener'ов из poll registry(набор
                                               // мапингов событий на объекты, производящие команды)

    private Closer closer = new DefaultCloser(); // для освобождения ресурсов

    private HashMap<Class<?>, GraphicalObjectProducer> getDisplayStrategy(TiledMapTileLayer groundLayer) {
        HashMap<Class<?>, GraphicalObjectProducer> displayStrategy = new HashMap<>();

        TextureRegion tankTextureRegion = TextureProvider
                .getTextureRegionFromImgPath(TankGraphicalObject.tankTextureImgPath);
        closer.addClosable(new ClosableTexture(tankTextureRegion.getTexture()));

        GraphicalObjectProducer tankGraphicsProducer = new TankGraphicalObjectProducer(
                tankTextureRegion,
                tileMovement);

        displayStrategy.put(Tank.class, tankGraphicsProducer);

        TextureRegion treeTextureRegion = TextureProvider
                .getTextureRegionFromImgPath(TreeGraphicalObject.treeTextureImgPath);
        closer.addClosable(new ClosableTexture(treeTextureRegion.getTexture()));

        GraphicalObjectProducer treeGraphicsProducer = new TreeGraphicalObjectProducer(
                treeTextureRegion,
                groundLayer);

        displayStrategy.put(Tree.class, treeGraphicsProducer);

        TextureRegion bulletTextureRegion = TextureProvider
                .getTextureRegionFromImgPath(BulletGraphicalObject.bulletTextureImgPath);
        closer.addClosable(new ClosableTexture(bulletTextureRegion.getTexture()));

        GraphicalObjectProducer bulletGraphicsProducer = new BulletGraphicalObjectProducer(
                bulletTextureRegion,
                tileMovement);

        displayStrategy.put(Bullet.class, bulletGraphicsProducer);

        return displayStrategy;
    }

    private void initPollRegistry(LevelFiller lvlFiller) {
        pollRegistry = new DefaultPollRegistry();

        // привязываем keyboard event listener к танку игрока
        pollRegistry.registerEventListener(lvlFiller.getPlayerObject(), new KeyboardEventListener());

        // привязываем random event listener к остальным объектам
        EventListener listenerForObject = new RandomEventListener();
        for (Object gameObj : lvlFiller.fetchedObjects()) {
            if (!(gameObj instanceof Updatable)) { // полагаем, что ко всем объектам, у которых есть состояние, должен
                                                   // быть привязан eventListener(может быть общий для разных объектов)
                continue;
            }

            pollRegistry.registerEventListener((Updatable) gameObj, listenerForObject);
        }

        // привязываем keyboard event listener к граф. представлению уровня
        EventListener listenerForLevel = new KeyboardEventListener();

        pollRegistry.registerEventListener(levelGraphic, listenerForLevel);
    }

    private void initEventInterpreter(LevelFiller lvlFiller) {
        eventInterpreter = new EventInterpreter();

        KeyboardEventToCmdMapping keyboardCmdMapping = new KeyboardEventToCmdMapping(gameLevel);

        // задаем способ интерпретации событий(мапинг события на команду) для
        // объекта игрока, графики и др объектов
        eventInterpreter.addMappingForObject(levelGraphic, keyboardCmdMapping.getEventToCmdMappingForLevel());

        HashMap<Integer, CommandProducer> randomCmdMapping = new RandomEventToCmdMapping(gameLevel)
                .getEventToCmdMapping();

        for (Object gameObj : lvlFiller.fetchedObjects()) {
            eventInterpreter.addMappingForObject(gameObj, randomCmdMapping);
        }
        eventInterpreter.addMappingForObject(lvlFiller.getPlayerObject(),
                keyboardCmdMapping.getEventToCmdMappingForPlayer());
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        closer.addClosable(new TilledMapClosable(level));

        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // мапинг логических объектов на их графическое представление
        HashMap<Class<?>, GraphicalObjectProducer> displayStrategy = getDisplayStrategy(groundLayer);

        // реализация интерфейса Drawer, рисующего Displayable объекты
        BatchDrawer batchDrawer = new BatchDrawer(batch);
        closer.addClosable(batchDrawer);

        levelGraphic = new LevelGraphicalObject(levelRenderer, batchDrawer, displayStrategy);

        gameLevel = new Level(groundLayer.getWidth(), groundLayer.getHeight());
        gameLevel.subscribeToLevelEvents(levelGraphic);

        // инициализация уровня согласно выбранной стратегии наполения
        LevelFillerExecutor lvlFillerExec = new LevelFillerExecutor(LevelFillerExecutor.FROM_FILE);
        lvlFillerExec.fillLevel(gameLevel);

        // стратегия применения декораторов к графическим объектам графического
        // представления уровня
        // декоратор будет применен к граф. объекту(то есть граф. объект будет обернут в
        // декоратор), если сработает условие Predicate на логический объект
        HashMap<DecoratorProducer, Predicate<Object>> applyDecoStrategy = new ApplyDecoratorStrategy().getStrategy();
        levelGraphic.applyGraphicDecorators(applyDecoStrategy);

        LevelFiller lvlFiller = lvlFillerExec.getFiller();

        initPollRegistry(lvlFiller);

        initEventInterpreter(lvlFiller);
    }

    @Override
    public void render() {
        clearScreen();

        ArrayList<ArrayList<Event>> happenedEvents = pollRegistry.pollEventListeners();

        for (ArrayList<Event> listenerEvents : happenedEvents) {
            for (Event event : listenerEvents) {
                // определяем получателя события по event listener'у(содержится в событии как
                // адресант события)
                java.lang.Object eventReceiver = pollRegistry.getEventReceiver(event.whoGot());

                Command cmdCausedByEvent = eventInterpreter.eventToCommandForObject(eventReceiver, event);

                // если событие удалось интерпретировать для данного получателя, исполняем
                // нужную команду
                if (cmdCausedByEvent != null) {
                    cmdCausedByEvent.exec();
                }
            }
        }

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        gameLevel.update(deltaTime);

        levelGraphic.render();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    public void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        closer.close();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
