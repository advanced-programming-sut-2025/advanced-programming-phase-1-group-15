package com.example.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.example.Main;
import com.example.controllers.CheatCodeController;
import com.example.models.App;
import com.example.models.Game;
import com.example.models.Player;
import com.example.models.Result;
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

    private final Integer tileSideLength = 48;
    private final Integer screenWidth = 1920;
    private final Integer screenHeight = 1080;

    private ExecutorService commandExecutor;
    private volatile boolean running = true;

    public GameView(Game game, Main main) {
        this.game = game;
        this.main = main;
        GameAssetManager.load();

        game.build();

        commandExecutor = Executors.newSingleThreadExecutor();
        commandExecutor.submit(this::readTerminalInput);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        SpriteBatch batch = main.getBatch();

        batch.begin();

        //TODO : first find the suitable Textures of tiles and areas and then uncomment bellow
        //showMap(batch); // Should be completed


        game.getDateAndTime().updateDateAndTime(delta);
        drawClock(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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


    public void showMap(Batch batch){
        Player currentPlayer = App.currentGame.getCurrentPlayer();
        int beginX = Math.max(0,currentPlayer.getPosition().getX() - screenWidth/tileSideLength);
        int beginY = Math.max(0,currentPlayer.getPosition().getY() - screenHeight/tileSideLength);
        int finishX = Math.min(App.currentGame.getMap().COLS,
            currentPlayer.getPosition().getX() + screenWidth/tileSideLength);
        int finishY = Math.min(App.currentGame.getMap().ROWS,
            currentPlayer.getPosition().getY() + screenHeight/tileSideLength);
        for(int i=beginY; i<= finishY; i++){
            for(int j=beginX; j<= finishX; j++){
                Tile toBePrinted = App.currentGame.getMap().getTile(new Position(i,j));
                printTile(toBePrinted,
                    (i)*tileSideLength - screenWidth/2,
                    (j)*tileSideLength - screenHeight/2,
                    batch);
            }
        }
        Sprite s;
        Animation a;
        TextureRegion region;
        Texture texture;

    }

    public void printTile(Tile tile, int x, int y,Batch batch){
        // TODO: return the sprite of current Tile
        // or the are that conaints it
        if(tile.getArea() == null){
            batch.draw(tile.getSprite(),x,y,tileSideLength,tileSideLength);
        }
        else{
            printArea(tile,batch);
        }
    }

    public void printArea(Tile tile,Batch batch){
        // TODO : find minX, minY, max X , max Y
        int[] surrounding = tile.getArea().surrounding();
        // TODO: find the centre
        int realWidth = tileSideLength * (surrounding[2]-surrounding[0]);
        int realHeight = tileSideLength * (surrounding[3]-surrounding[1]);
        int cornerX = surrounding[0];
        int cornerY = surrounding[1];
        batch.draw(tile.getSprite(), cornerX, cornerY,realWidth,realHeight);
        // TODO : scale the sprite of Area to the real width and length
        // print the rectangle in the centre of the real rectangle
    }
}
