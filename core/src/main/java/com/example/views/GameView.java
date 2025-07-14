package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.example.Main;
import com.example.controllers.CheatCodeController;
import com.example.models.App;
import com.example.models.Game;
import com.example.models.GraphicalModels.HUDCamera;
import com.example.models.GraphicalModels.MapCamera;
import com.example.models.GraphicalModels.PopUpMenus.FriendsMenu;
import com.example.models.Player;
import com.example.models.Result;
import com.example.models.enums.Direction;
import com.example.models.map.Area;
import com.example.models.map.Map;
import com.example.models.map.Position;
import com.example.models.map.Tile;
import com.example.models.stores.Store;
import com.example.models.time.DateAndTime;
import com.example.models.time.Season;
import com.example.models.weather.WeatherOption;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameView implements Screen {
    private final Game game;
    private final Main main;

    private final Integer tileSideLength = 16;
    private final Integer screenWidth = 1920;
    private final Integer screenHeight = 1080;

    private MapCamera mapCamera;
    private Stage uiStage;
    private Skin skin;

    // UI
    private Table hudTable;
    private Label energyLabel;

    // Pop-up menus
    private FriendsMenu friendsMenu;

    // Input handling
    private GameInputProcessor gameInputProcessor;
    private final ExecutorService commandExecutor;
    private volatile boolean running = true;

    private final PauseMenuOverlay pauseMenuOverlay;

    public GameView(Game game, Main main) {
        this.game = game;
        this.main = main;

        game.build();

        mapCamera = new MapCamera(game.getCurrentPlayer());

        uiStage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));

        gameInputProcessor = new GameInputProcessor();

        commandExecutor = Executors.newSingleThreadExecutor();
        commandExecutor.submit(this::readTerminalInput);

        this.pauseMenuOverlay = new PauseMenuOverlay(main, game);
    }

    @Override
    public void show() {
        setupUI();
        setupInputHandling();

        if (skin != null) {
            friendsMenu = new FriendsMenu(skin, "Friends");
        }
    }

    private void setupUI() {
        if (skin == null) return;

        hudTable = new Table();
        hudTable.setFillParent(true);
        hudTable.top().right();

        createHUDComponents();

        createControlButtons();

        uiStage.addActor(hudTable);
    }

    private void createHUDComponents() {
        energyLabel = new Label("", skin);
        energyLabel.setColor(Color.BLACK);

        // Energy label in top-left
        Table energyTable = new Table();
        energyTable.setFillParent(true);
        energyTable.top().left();
        energyTable.add(energyLabel).padTop(10).padLeft(10);

        uiStage.addActor(energyTable);
    }

    private void createControlButtons() {
        // Friends button
        TextButton friendsButton = new TextButton("Friends", skin);
        friendsButton.setSize(150, 50);

        friendsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (friendsMenu != null) {
                    friendsMenu.show(uiStage);
                }
            }
        });

        // Button layout table
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.top().left();
        buttonTable.add(friendsButton).padTop(50).padLeft(10);

        uiStage.addActor(buttonTable);
    }

    private void setupInputHandling() {
        com.badlogic.gdx.InputMultiplexer multiplexer = new com.badlogic.gdx.InputMultiplexer();
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(gameInputProcessor);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getCurrentPlayer().updateAnimation(delta);
        game.getDateAndTime().updateDateAndTime(delta);

        SpriteBatch batch = main.getBatch();

        renderMap(batch);

        renderHUD(batch, delta);

        updateUIComponents();
        uiStage.act(delta);
        uiStage.draw();

        if (friendsMenu != null) {
            friendsMenu.render(delta);
        }

        pauseMenuOverlay.draw(delta);
    }

    private void renderMap(SpriteBatch batch) {
        mapCamera.setPlayer(game.getCurrentPlayer());
        mapCamera.update();
        batch.setProjectionMatrix(mapCamera.getCamera().combined);
        batch.begin();

        showMap(batch);
        drawPlayer(batch);

        batch.end();
    }

    private void renderHUD(SpriteBatch batch, float delta) {
        batch.setProjectionMatrix(uiStage.getCamera().combined);
        batch.begin();

        drawClock(batch);

        batch.end();
    }

    private void updateUIComponents() {
        int energy = (int) game.getCurrentPlayer().getEnergy();
        energyLabel.setText("Energy: " + energy);
    }

    private void drawClock(SpriteBatch batch) {
        DateAndTime dateAndTime = game.getDateAndTime();
        WeatherOption weatherOption = game.getWeather().getCurrentWeather();
        Season season = dateAndTime.getSeason();
        int gold = game.getCurrentPlayer().getGold();

        TextureRegion clock = GameAssetManager.clock;
        float clockWidth = 4 * clock.getRegionWidth();
        float clockHeight = 4 * clock.getRegionHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float x = screenWidth - clockWidth - 10;
        float y = screenHeight - clockHeight - 10;

        batch.draw(clock, x, y, clockWidth, clockHeight);

        BitmapFont font = GameAssetManager.font;
        font.getData().setScale(1.5f);

        String dateStr = dateAndTime.displayDate();
        String timeStr = dateAndTime.displayTime();
        String goldStr = String.valueOf(gold);

        float textX = x + clockWidth / 2;

        font.setColor(Color.BLACK);
        font.draw(batch, dateStr, textX - 15, screenHeight - 30);
        font.draw(batch, timeStr, textX + 10, screenHeight - 125);
        font.setColor(Color.FIREBRICK);
        font.draw(batch, goldStr, x + 70, y + 40);
        font.getData().setScale(1f);

        TextureRegion weatherIcon;
        TextureRegion seasonIcon;
        switch (weatherOption) {
            case RAINY -> weatherIcon = GameAssetManager.rainy;
            case STORM -> weatherIcon = GameAssetManager.stormy;
            case SNOW -> weatherIcon = GameAssetManager.snowy;
            default -> weatherIcon = GameAssetManager.sunny;
        }
        switch (season) {
            case SUMMER -> seasonIcon = GameAssetManager.summer;
            case WINTER -> seasonIcon = GameAssetManager.winter;
            case AUTUMN -> seasonIcon = GameAssetManager.autumn;
            default -> seasonIcon = GameAssetManager.spring;
        }

        batch.draw(weatherIcon, x + 115, screenHeight - 105 , 4 * weatherIcon.getRegionWidth(), 4 * weatherIcon.getRegionHeight());
        batch.draw(seasonIcon, x + 210, screenHeight - 105, 4 * seasonIcon.getRegionWidth(), 4 * seasonIcon.getRegionHeight());
    }

    @Override
    public void resize(int width, int height) {
        mapCamera.resize(width, height);
        uiStage.getViewport().update(width, height, true);

        if (friendsMenu != null) {
            friendsMenu.resize(width, height);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        if (friendsMenu != null) {
            friendsMenu.hide();
        }
    }

    @Override
    public void dispose() {
        running = false;
        commandExecutor.shutdownNow();

        uiStage.dispose();
        if (friendsMenu != null) {
            friendsMenu.dispose();
        }
        if (skin != null) {
            skin.dispose();
        }
    }

    private void readTerminalInput() {
        Scanner scanner = new Scanner(System.in);
        while (running) {
            if (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                Result result = CheatCodeController.processCommand(command);
                System.out.println(result);
            }
        }
        scanner.close();
    }

    public void showMap(Batch batch) {
        com.example.models.map.Map currentMap = game.getMap();

        for(int row = 0; row < currentMap.ROWS; row++){
            for(int col = 0; col < currentMap.COLS; col++){
                Tile toBePrinted = App.currentGame.getMap().getTile(row, col);
                printTile(toBePrinted,
                    (col) * tileSideLength,
                    (row) * tileSideLength,
                    batch);
            }
        }
    }

    public void printTile(Tile tile, int x, int y, Batch batch){
        if(tile.getAreaSprite() != null) {
            batch.draw(tile.getAreaSprite(), x, y, tileSideLength, tileSideLength);
        }
        else {
            printArea(tile, batch);
        }

        if(tile.getObjectSprite() != null) {
            batch.draw(tile.getObjectSprite(), x, y, tileSideLength, tileSideLength);
        }
    }

    public void printArea(Tile tile, Batch batch) {
        com.example.models.map.Area area = tile.getArea();
        Position bottomLeft = area.getBottomLeftCorner();
        int drawX = bottomLeft.x * tileSideLength;
        int drawY = bottomLeft.y * tileSideLength;
        int realWidth = tileSideLength * area.getWidth();
        int realHeight = tileSideLength * area.getHeight();
        if(area.getTexture() != null)  {
            batch.draw(area.getTexture(), drawX, drawY, realWidth, realHeight);
        }
    }

    private void drawPlayer(SpriteBatch batch) {
        Position pos = game.getCurrentPlayer().getPosition();
        int drawX = pos.x * tileSideLength;
        int drawY = pos.y * tileSideLength;
        batch.draw(game.getCurrentPlayer().getCurrentFrame(), drawX, drawY);
    }

    private class GameInputProcessor implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            Player player = game.getCurrentPlayer();
            Position currentPosition = player.getPosition();
            int x = currentPosition.getX();
            int y = currentPosition.getY();

            player.setWalking(false);

            switch (keycode) {
                case Input.Keys.W:
                    player.setDirection(Direction.UP);
                    player.walk(x, y + 1);
                    return true;
                case Input.Keys.S:
                    player.setDirection(Direction.DOWN);
                    player.walk(x, y - 1);
                    return true;
                case Input.Keys.A:
                    player.setDirection(Direction.LEFT);
                    player.walk(x - 1, y);
                    return true;
                case Input.Keys.D:
                    player.setDirection(Direction.RIGHT);
                    player.walk(x + 1, y);
                    return true;
                case Input.Keys.ESCAPE:
                    pauseMenuOverlay.setVisible(!pauseMenuOverlay.isVisible());
                    return true;
            }
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            Player player = game.getCurrentPlayer();

            switch (keycode) {
                case Input.Keys.W:
                case Input.Keys.S:
                case Input.Keys.A:
                case Input.Keys.D:
                    player.setWalking(false);
                    return true;
            }
            return false;
        }

        @Override
        public boolean keyTyped(char c) {
            return false;
        }

        @Override
        public boolean touchDown(int x, int y, int pointer, int button) {
            if (button == Input.Buttons.LEFT) {
                Vector3 worldCoords = mapCamera.getCamera().unproject(new com.badlogic.gdx.math.Vector3(x, y, 0));

                int tileX = (int) (worldCoords.x / tileSideLength);
                int tileY = (int) (worldCoords.y / tileSideLength);

                if (tileX >= 0 && tileX < game.getMap().COLS &&
                    tileY >= 0 && tileY < game.getMap().ROWS) {

                    Tile clickedTile = App.currentGame.getTile(tileX, tileY);

                    if (clickedTile.getArea() instanceof Store) {
                        Store store = (Store) clickedTile.getArea();
                        // TODO: show Store menu
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean touchUp(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchCancelled(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchDragged(int i, int i1, int i2) {
            return false;
        }

        @Override
        public boolean mouseMoved(int i, int i1) {
            return false;
        }

        @Override
        public boolean scrolled(float v, float v1) {
            return false;
        }
    }

}
