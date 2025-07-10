package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.example.Main;
import com.example.controllers.CheatCodeController;
import com.example.models.App;
import com.example.models.Game;
import com.example.models.GraphicalModels.HUDCamera;
import com.example.models.GraphicalModels.MapCamera;
import com.example.models.Player;
import com.example.models.Result;
import com.example.models.enums.Direction;
import com.example.models.map.Map;
import com.example.models.map.Position;
import com.example.models.map.Tile;
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

    // private MapCamera mapCamera;
    private HUDCamera hudCamera;

    private ExecutorService commandExecutor;
    private volatile boolean running = true;

    public GameView(Game game, Main main) {
        this.game = game;
        this.main = main;

        game.build();

        // mapCamera = new MapCamera(game.getCurrentPlayer());
        hudCamera = new HUDCamera();

        commandExecutor = Executors.newSingleThreadExecutor();
        commandExecutor.submit(this::readTerminalInput);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
        game.getCurrentPlayer().updateAnimation(delta);


//        mapCamera.setPlayer(game.getCurrentPlayer());
//        mapCamera.update();

        SpriteBatch batch = main.getBatch();
        // batch.setProjectionMatrix(mapCamera.getCamera().combined);
        batch.begin();

        showMap(batch);
        drawPlayer(batch);

        batch.end();

        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.getCamera().combined);
        batch.begin();

        game.getDateAndTime().updateDateAndTime(delta);
        drawClock(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // mapCamera.resize(width, height);
        hudCamera.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        running = false;
        commandExecutor.shutdownNow();
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
//        Player currentPlayer = App.currentGame.getCurrentPlayer();
//        int beginX = Math.max(0,currentPlayer.getPosition().getX() - screenWidth/tileSideLength);
//        int beginY = Math.max(0,currentPlayer.getPosition().getY() - screenHeight/tileSideLength);
//        int finishX = Math.min(currentMap.COLS,
//            currentPlayer.getPosition().getX() + screenWidth/tileSideLength);
//        int finishY = Math.min(App.currentGame.getMap().ROWS,
//            currentPlayer.getPosition().getY() + screenHeight/tileSideLength);
        for(int row = 0; row < currentMap.ROWS; row++){
            for(int col = 0; col < currentMap.COLS; col++){
                Tile toBePrinted = App.currentGame.getMap().getTile(row, col);
                printTile(toBePrinted,
                    (row)*tileSideLength,
                    (col)*tileSideLength,
                    batch);
            }
        }
    }

    public void printTile(Tile tile, int x, int y, Batch batch){
        // draws the background of the tile (area is never null)
        if(tile.getAreaSprite() != null) {
            batch.draw(tile.getAreaSprite(), x, y, tileSideLength, tileSideLength);
        }

        // draws the object in tile if present
        if(tile.getObjectSprite() != null) {
            batch.draw(tile.getObjectSprite(), x, y, tileSideLength, tileSideLength);
        }
    }

    public void printArea(Tile tile, Batch batch){
        int[] surrounding = tile.getArea().surrounding();
        int realWidth = tileSideLength * (surrounding[2]-surrounding[0]);
        int realHeight = tileSideLength * (surrounding[3]-surrounding[1]);
        int cornerX = surrounding[0];
        int cornerY = surrounding[1];
        batch.draw(tile.getAreaSprite(), cornerX, cornerY,realWidth,realHeight);
        // print the rectangle in the centre of the real rectangle
    }

    public void updateMapCamera(){
//        mapCamera = new MapCamera(game.getCurrentPlayer());
//        mapCamera.update();
    }

    private void drawPlayer(SpriteBatch batch) {
        Position pos = game.getCurrentPlayer().getPosition();
        int drawX = pos.getX() * tileSideLength;
        int drawY = pos.getY() * tileSideLength;
        batch.draw(game.getCurrentPlayer().getCurrentFrame(), drawX, drawY);
    }

    private void handleInput() {
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
    }
}
