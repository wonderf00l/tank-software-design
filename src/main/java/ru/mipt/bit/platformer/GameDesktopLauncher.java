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
import ru.mipt.bit.platformer.event.ListenerProvider;
import ru.mipt.bit.platformer.event.keyboard.KeyboardEventToCmdMapping;
import ru.mipt.bit.platformer.event.random.RandomEventToCmdMapping;
import ru.mipt.bit.platformer.event.EventInterpreter;
import ru.mipt.bit.platformer.event.PollRegistry;
import ru.mipt.bit.platformer.event.DefaultPollRegistry;
import ru.mipt.bit.platformer.event.Event;
import ru.mipt.bit.platformer.level.entity.Level;
import ru.mipt.bit.platformer.level.entity.filler.LevelFiller;
import ru.mipt.bit.platformer.level.entity.filler.LevelFillerExecutor;
import ru.mipt.bit.platformer.level.ui.LevelGraphicalObject;
import ru.mipt.bit.platformer.movement.entity.DefaultMoveManager;
import ru.mipt.bit.platformer.UI.BatchDrawer;
import ru.mipt.bit.platformer.UI.GraphicalObjectProducer;
import ru.mipt.bit.platformer.UI.TextureProvider;
import ru.mipt.bit.platformer.tank.entity.DefaultTank;
import ru.mipt.bit.platformer.tank.ui.TankGraphicalObjectProducer;
import ru.mipt.bit.platformer.obstacle.entity.Tree;
import ru.mipt.bit.platformer.obstacle.ui.TreeGraphicalObjectProducer;
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

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 2.0f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    // private Texture blueTankTexture;
    // private TextureRegion playerGraphics;
    // private Rectangle playerRectangle;
    // // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    // private GridPoint2 playerCoordinates;
    // // which tile the player want to go next
    // private GridPoint2 playerDestinationCoordinates;
    // private float playerMovementProgress = 1f;
    // private float playerRotation;

    // private Texture greenTreeTexture;
    // private TextureRegion treeObstacleGraphics;
    // private GridPoint2 treeObstacleCoordinates = new GridPoint2();
    // private Rectangle treeObstacleRectangle = new Rectangle();

    private Level gameLevel;

    private LevelGraphicalObject levelGraphic;

    private PollRegistry pollRegistry;

    private EventInterpreter eventInterpreter;

    private Closer closer = new DefaultCloser();

    @Override
    public void create() {

        // graphic decorator interface - Displayable getWrapped()
        // graphic deco can be toggable(on/off), another objs can be too

        // graphicDecosCotanier == []Decorator
        // .addDeco()
        // apply to graphic obj(obj)

        // L: event - from keyboard, eventAcceptor - player, toggleCmd

        // toggleCmd: preserves graphic level

        // cfg opt -> use health or not

        // mapRenderer, batch, BatchDrawer; classes: graphProducer - displayStrategy ->
        // graph lvl
        // new logical lvl, partly init logical lvl, subscribe graph level to logical
        // lvl
        // initialize rest logical lvl arrts: widht, height(from groundLayer)
        // use levelInitializer interface(level Filler) to set objs on locations and
        // notify listeners(fill graph lvl)

        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        closer.addClosable(new TilledMapClosable(level));

        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        TextureRegion tankTextureRegion = TextureProvider
                .getTextureRegionFromImgPath(TextureProvider.tankTextureImgPath);
        closer.addClosable(new ClosableTexture(tankTextureRegion.getTexture()));

        GraphicalObjectProducer tankGraphicsProducer = new TankGraphicalObjectProducer(
                tankTextureRegion,
                tileMovement);

        TextureRegion treeTextureRegion = TextureProvider
                .getTextureRegionFromImgPath(TextureProvider.treeTextureImgPath);
        closer.addClosable(new ClosableTexture(treeTextureRegion.getTexture()));

        GraphicalObjectProducer treeGraphicsProducer = new TreeGraphicalObjectProducer(
                treeTextureRegion,
                groundLayer);

        HashMap<Class<?>, GraphicalObjectProducer> displayStrategy = new HashMap<>();
        displayStrategy.put(DefaultTank.class, tankGraphicsProducer);
        displayStrategy.put(Tree.class, treeGraphicsProducer);

        BatchDrawer batchDrawer = new BatchDrawer(batch);
        closer.addClosable(batchDrawer);

        levelGraphic = new LevelGraphicalObject(levelRenderer, batchDrawer, displayStrategy);

        // create decoStrat

        // applyDecos

        // create logical level, do sub

        gameLevel = new Level(groundLayer.getWidth(), groundLayer.getHeight());
        gameLevel.subscribeToLevelEvents(levelGraphic);

        // levelFillerProvider: use arg - what lvl init strategy(file or random)

        LevelFillerExecutor lvlFillerExec = new LevelFillerExecutor(new DefaultMoveManager(MOVEMENT_SPEED),
                LevelFillerExecutor.FROM_FILE);

        lvlFillerExec.fillLevel(gameLevel);

        // ---

        /*
         * 1. fetch objs + player obj from levelFiller
         * 2. for each obj:
         * fetch listner form listenerProvider - CREATE SENDER-RECEIVER BOND
         * 3. register listener for each obj in poll registry
         * 
         * 4. create eventCmdMapping -> create default event interpreter
         * 
         * 5. registry has been polled -> returned [][]Event
         * 6. get acceptor(obj) --> invoke cmd producer to producer cmd, exec cmd
         */

        pollRegistry = new DefaultPollRegistry();

        LevelFiller lvlFiller = lvlFillerExec.getFiller();

        EventListener listenerForObject = ListenerProvider.provideListener(false);
        for (Object gameObj : lvlFiller.fetchedObjects()) {
            if (!(gameObj instanceof Updatable)) { // полагаем, что ко всем объектам, у которых есть состояние, должен
                                                   // быть привязан eventListener
                continue;
            }

            pollRegistry.registerEventListener(gameObj, listenerForObject);
        }
        EventListener listenerForPlayer = ListenerProvider.provideListener(true);
        pollRegistry.registerEventListener(lvlFiller.getPlayerObject(), listenerForPlayer);

        // --

        eventInterpreter = new EventInterpreter();

        // задаем способ интерпретации событий для каждого из объектов

        HashMap<Integer, CommandProducer> mapping = new RandomEventToCmdMapping(gameLevel).getEventToCmdMapping();

        for (Object gameObj : lvlFiller.fetchedObjects()) {
            eventInterpreter.addMappingForObject(gameObj, mapping);
        }
        eventInterpreter.addMappingForObject(lvlFiller.getPlayerObject(),
                new KeyboardEventToCmdMapping(gameLevel).getEventToCmdMapping());

        // Texture decodes an image file and loads it into GPU memory, it represents a
        // native resource
        // blueTankTexture = new Texture("images/tank_blue.png");

        // // TextureRegion represents Texture portion, there may be many TextureRegion
        // // instances of the same Texture
        // playerGraphics = new TextureRegion(blueTankTexture);
        // playerRectangle = createBoundingRectangle(playerGraphics);
        // // set player initial position
        // playerDestinationCoordinates = new GridPoint2(1, 1);
        // playerCoordinates = new GridPoint2(playerDestinationCoordinates);
        // playerRotation = 0f;

        // greenTreeTexture = new Texture("images/greenTree.png");
        // treeObstacleGraphics = new TextureRegion(greenTreeTexture);
        // treeObstacleCoordinates = new GridPoint2(1, 3);
        // treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);
        // moveRectangleAtTileCenter(groundLayer, treeObstacleRectangle,
        // treeObstacleCoordinates);
    }

    @Override
    public void render() {
        clearScreen();

        // execCmds()
        ArrayList<ArrayList<Event>> happenedEvents = pollRegistry.pollEventListeners();

        for (ArrayList<Event> listenerEvents : happenedEvents) {
            for (Event event : listenerEvents) {
                Object eventReceiver = pollRegistry.getEventReceiver(event.whoGot());

                Command cmdCausedByEvent = eventInterpreter.eventToCommandForObject(eventReceiver, event);

                cmdCausedByEvent.exec();
            }
        }

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        gameLevel.update(deltaTime);

        levelGraphic.render();

        // if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
        // if (isEqual(playerMovementProgress, 1f)) {
        // // check potential player destination for collision with obstacles
        // if (!treeObstacleCoordinates.equals(incrementedY(playerCoordinates))) {
        // playerDestinationCoordinates.y++;
        // playerMovementProgress = 0f;
        // }
        // playerRotation = 90f;
        // }
        // }
        // if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
        // if (isEqual(playerMovementProgress, 1f)) {
        // if (!treeObstacleCoordinates.equals(decrementedX(playerCoordinates))) {
        // playerDestinationCoordinates.x--;
        // playerMovementProgress = 0f;
        // }
        // playerRotation = -180f;
        // }
        // }
        // if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
        // if (isEqual(playerMovementProgress, 1f)) {
        // if (!treeObstacleCoordinates.equals(decrementedY(playerCoordinates))) {
        // playerDestinationCoordinates.y--;
        // playerMovementProgress = 0f;
        // }
        // playerRotation = -90f;
        // }
        // }
        // if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
        // if (isEqual(playerMovementProgress, 1f)) {
        // if (!treeObstacleCoordinates.equals(incrementedX(playerCoordinates))) {
        // playerDestinationCoordinates.x++;
        // playerMovementProgress = 0f;
        // }
        // playerRotation = 0f;
        // }
        // }

        // // calculate interpolated player screen coordinates
        // tileMovement.moveRectangleBetweenTileCenters(playerRectangle,
        // playerCoordinates, playerDestinationCoordinates, playerMovementProgress);

        // // moveManager - прогресс движения с ходом времени
        // playerMovementProgress = continueProgress(playerMovementProgress, deltaTime,
        // MOVEMENT_SPEED);
        // if (isEqual(playerMovementProgress, 1f)) { //
        // moveManager.isMovementFinished() } { return progress ==
        // // MOVEMENT_DONE(1) }
        // // record that the player has reached his/her destination
        // playerCoordinates.set(playerDestinationCoordinates);
        // }

        // // render each tile of the level
        // levelRenderer.render();

        // // start recording all drawing commands
        // batch.begin();

        // // render player
        // drawTextureRegionUnscaled(batch, playerGraphics, playerRectangle,
        // playerRotation);

        // // render tree obstacle
        // drawTextureRegionUnscaled(batch, treeObstacleGraphics, treeObstacleRectangle,
        // 0f);

        // // submit all drawing requests
        // batch.end();
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
        // dispose of all the native resources (classes which implement
        // com.badlogic.gdx.utils.Closable)
        // greenTreeTexture.dispose();
        // blueTankTexture.dispose();
        // level.dispose();
        // batch.dispose();

        closer.close();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
