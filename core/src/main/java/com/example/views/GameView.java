package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
    private HUDCamera hudCamera;

    private final ExecutorService commandExecutor;
    private volatile boolean running = true;

    private final PauseMenuOverlay pauseMenuOverlay;

    private final Stage hudStage;
    private final Skin skin;
    private FriendsMenu friendsMenu;

    public GameView(Game game, Main main) {
        this.game = game;
        this.main = main;

        game.build();

        mapCamera = new MapCamera(game.getCurrentPlayer());
        hudCamera = new HUDCamera();

        hudStage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));

        commandExecutor = Executors.newSingleThreadExecutor();
        commandExecutor.submit(this::readTerminalInput);

        this.pauseMenuOverlay = new PauseMenuOverlay(main, game);
    }

    @Override
    public void show() {
        setupUI();

        if (skin != null) {
            friendsMenu = new FriendsMenu(skin, "Friends");
        }

        // Set input processor to handle both game input and UI input
        Gdx.input.setInputProcessor(hudStage);
    }

    private void setupUI() {
        if (skin == null) return;

        TextButton friendsButton = new TextButton("Friends", skin);
        friendsButton.setSize(150, 50);
        friendsButton.setPosition(0, Gdx.graphics.getHeight() - 100);

        friendsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (friendsMenu != null) {
                    friendsMenu.show(hudStage);
                }
            }
        });

        hudStage.addActor(friendsButton);

        // Add more UI buttons here as needed
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getCurrentPlayer().updateAnimation(delta);

        SpriteBatch batch = main.getBatch();

        printMapRelatedStuff(batch);

        printHUDRelatedStuff(batch,delta);

        boolean received = HUDRelatedInput(batch);

        if(!received) {
            mapRelatedInput(batch);
        }


        // These lines should be probably moved to another class ****
        hudStage.act(delta);
        hudStage.draw();

        if (friendsMenu != null) {
            friendsMenu.render(delta);
        }
        // **********************************************************
    }

    public void printMapRelatedStuff(SpriteBatch batch){
        mapCamera.setPlayer(game.getCurrentPlayer());
        mapCamera.update();
        batch.setProjectionMatrix(mapCamera.getCamera().combined);
        batch.begin();

        showMap(batch);
        drawPlayer(batch);

        batch.end();
    }

    public void printHUDRelatedStuff(SpriteBatch batch,float delta){
        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.getCamera().combined);
        batch.begin();

        game.getDateAndTime().updateDateAndTime(delta);
        drawClock(batch);

        batch.end();
        pauseMenuOverlay.draw(delta);
    }

    public boolean HUDRelatedInput(SpriteBatch batch){
        return hudStage.touchDown(Gdx.input.getX(), Gdx.input.getY(), 0, 0);
    }

    public void mapRelatedInput(SpriteBatch batch){
        mapCamera.update();
        batch.setProjectionMatrix(mapCamera.getCamera().combined);
        batch.begin();

        Player player = game.getCurrentPlayer();
        player.setWalking(false);
        Position currentPosition = player.getPosition();
        int x = currentPosition.getX();
        int y = currentPosition.getY();

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player.setDirection(Direction.UP);
            player.walk(x, y + 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            player.setDirection(Direction.DOWN);
            player.walk(x, y - 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            player.setDirection(Direction.LEFT);
            player.walk(x - 1, y);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player.setDirection(Direction.RIGHT);
            player.walk(x + 1, y);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pauseMenuOverlay.setVisible(!pauseMenuOverlay.isVisible());
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            int posX = Gdx.input.getX();
            int posY = Gdx.input.getY();
            // TODO : change posX and posY to suitable numbers for tiles 2D array
            Tile clickedTile = App.currentGame.getTile(posX/tileSideLength,posY/tileSideLength);
            // TODO: check if it is hud and handle it
            // TODO: else if , check the tile and handle it
            if(clickedTile.getArea() instanceof Store){
                Store store = (Store) clickedTile.getArea();
                // TODO : show Store menu
            }
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        mapCamera.resize(width, height);
        hudCamera.resize(width, height);
        hudStage.getViewport().update(width, height, true);

        if (friendsMenu != null) {
            friendsMenu.resize(width, height);
        }

        setupUI();
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

        hudStage.dispose();
        if (friendsMenu != null) {
            friendsMenu.dispose();
        }
        if (skin != null) {
            skin.dispose();
        }
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
        String energyStr = "Energy: " + (int) game.getCurrentPlayer().getEnergy();

        float textX = x + clockWidth / 2;

        font.setColor(Color.BLACK);
        font.draw(batch, dateStr, textX - 15, screenHeight - 30);
        font.draw(batch, timeStr, textX + 10, screenHeight - 125);
        font.setColor(Color.FIREBRICK);
        font.draw(batch, goldStr, x + 70, y + 40);
        font.getData().setScale(1f);
        font.draw(batch, energyStr, 0, screenHeight);

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
        Map currentMap = game.getMap();

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
        // draws the background of the tile (area is never null)
        if(tile.getAreaSprite() != null) {
            batch.draw(tile.getAreaSprite(), x, y, tileSideLength, tileSideLength);
        }
        else {
            printArea(tile, batch);
        }

        // draws the object in tile if present
        if(tile.getObjectSprite() != null) {
            batch.draw(tile.getObjectSprite(), x, y, tileSideLength, tileSideLength);
        }
    }

    public void printArea(Tile tile, Batch batch) {
        Area area = tile.getArea();
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

}
