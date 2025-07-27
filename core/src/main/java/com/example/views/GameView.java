package com.example.views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.example.Main;
import com.example.controllers.CheatCodeController;
import com.example.controllers.GameController;
import com.example.models.App;
import com.example.models.Game;
import com.example.models.GraphicalModels.MapCamera;
import com.example.models.GraphicalModels.NPCUIHelper;
import com.example.models.GraphicalModels.NotificationLabel;
import com.example.models.GraphicalModels.PopUpMenus.*;
import com.example.models.GraphicalModels.RightClickMenus.NPCRightClickMenu;
import com.example.models.GraphicalModels.RightClickMenus.PlayerRightClickMenu;
import com.example.models.GraphicalModels.RightClickMenus.RightClickMenu;
import com.example.models.Player;
import com.example.models.Result;
import com.example.models.crafting.CraftItem;
import com.example.models.enums.Direction;
import com.example.models.map.GreenHouse;
import com.example.models.map.Map;
import com.example.models.map.Position;
import com.example.models.map.Tile;
import com.example.models.npcs.DefaultNPCs;
import com.example.models.npcs.NPC;
import com.example.models.stores.CarpenterShop;
import com.example.models.stores.MarnieRanch;
import com.example.models.stores.Store;
import com.example.models.time.DateAndTime;
import com.example.models.time.Season;
import com.example.models.weather.WeatherOption;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameView implements Screen {
    private final Game game;
    private final Main main;

    private final Integer tileSideLength = 16;
    private static final Integer screenWidth = 1920;
    private static final Integer screenHeight = 1080;

    private final MapCamera mapCamera;
    private final Stage uiStage;
    private final Skin skin;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final ShapeRenderer overlayRenderer = new ShapeRenderer();
    private final Color eveningTint = new Color(0.2f, 0.2f, 0.4f, 0.3f);

    // UI
    private Table hudTable;
    private Label energyLabel;
    private Label currentItemLabel;
    private NotificationLabel notificationLabel;

    // Pop-up menus
    private PopUpMenu popUpMenu;

    // right-click menus
    private RightClickMenu rightClickMenu;

    // Input handling
    private final GameInputProcessor gameInputProcessor;
    private final ExecutorService commandExecutor;
    private volatile boolean running = true;

    private InputMultiplexer gameInputMultiplexer;

    private final PauseMenuOverlay pauseMenuOverlay;
    private final CraftingMenu craftingMenu;
    private final CookingMenu cookingMenu;
    private ArrayList<ArtisanMenu> artisans = new ArrayList<>();

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

        this.pauseMenuOverlay = new PauseMenuOverlay(main, game, this::restoreGameInput);
        this.craftingMenu = new CraftingMenu(main, game, this::restoreGameInput);
        this.cookingMenu = new CookingMenu(main, game, this::restoreGameInput);
    }

    @Override
    public void show() {
        setupUI();
        setupInputHandling();
    }

    private void setupUI() {
        if (skin == null) return;

        hudTable = new Table();
        hudTable.setFillParent(true);
        hudTable.top().left();

        createHUDComponents();

        uiStage.addActor(hudTable);
    }

    private void createHUDComponents() {
        energyLabel = new Label("", skin);
        energyLabel.setColor(Color.FIREBRICK); energyLabel.setAlignment(Align.left);
        currentItemLabel = new Label("", skin);
        currentItemLabel.setColor(Color.FIREBRICK); currentItemLabel.setAlignment(Align.left);
        notificationLabel = new NotificationLabel(skin);

        hudTable.add(energyLabel).padTop(5).padLeft(10).left().row();
        hudTable.add(currentItemLabel).padTop(5).padLeft(10).left().row();

        TextButton friendsButton = new TextButton("Friends", skin);
        TextButton toolsButton = new TextButton("Tools", skin);

        float buttonWidth = 125f;
        float buttonHeight = 60f;

        friendsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                popUpMenu = new FriendsMenu(skin, "Friends:", this::restoreGameInput);
                popUpMenu.show();
                Gdx.input.setInputProcessor(popUpMenu.getStage());
            }

            private void restoreGameInput() {
                Gdx.input.setInputProcessor(gameInputMultiplexer);
            }
        });

        toolsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                popUpMenu = new ToolsMenu(skin, "Tools:", this::restoreGameInput);
                popUpMenu.show();
                Gdx.input.setInputProcessor(popUpMenu.getStage());
            }

            private void restoreGameInput() {
                Gdx.input.setInputProcessor(gameInputMultiplexer);
            }
        });

        hudTable.add(friendsButton).padTop(5).padLeft(5).size(buttonWidth, buttonHeight).left().row();
        hudTable.add(toolsButton).padLeft(5).size(buttonWidth, buttonHeight).left().row();
        hudTable.add(notificationLabel).expandX().bottom().center().padTop(700).row();
    }

    private void setupInputHandling() {
        gameInputMultiplexer = new InputMultiplexer();
        gameInputMultiplexer.addProcessor(uiStage);
        gameInputMultiplexer.addProcessor(gameInputProcessor);
        Gdx.input.setInputProcessor(gameInputMultiplexer); // Set the primary game input multiplexer
    }

    public void restoreGameInput() {
        if (gameInputMultiplexer == null) {
            setupInputHandling();
        }
        gameInputMultiplexer.clear();
        gameInputMultiplexer.addProcessor(uiStage);
        gameInputMultiplexer.addProcessor(gameInputProcessor);

        Gdx.input.setInputProcessor(gameInputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getCurrentPlayer().updateAnimation(delta);
        game.getDateAndTime().updateDateAndTime(delta);

        SpriteBatch batch = main.getBatch();

        renderMap(batch);
        int currentHour = game.getDateAndTime().getHour();
        if (currentHour >= 18) {
            drawEveningTintOverlay();
        }
        renderHeldItemCursor(batch);

        renderHUD(batch, delta);

        updateUIComponents();
        uiStage.act(delta);
        uiStage.draw();

        if (rightClickMenu != null && rightClickMenu.isVisible()) {
            rightClickMenu.render(delta);
        }

        if (popUpMenu != null) {
            popUpMenu.render(delta);
        }

        pauseMenuOverlay.draw(delta);
        craftingMenu.draw(delta);
        cookingMenu.draw(delta);
        for (ArtisanMenu artisan : artisans) {
            artisan.draw(delta);
        }
    }

    private void renderMap(SpriteBatch batch) {
        mapCamera.setPlayer(game.getCurrentPlayer());
        mapCamera.update();
        batch.setProjectionMatrix(mapCamera.getCamera().combined);
        batch.begin();

        showMap(batch);
        drawNPCs(batch);
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
        if(game.getCurrentPlayer().getCurrentItem() != null) {
            String currentItem = game.getCurrentPlayer().getCurrentItem().getName();
            currentItemLabel.setText("You're holding: " + currentItem);
        }
        else {
            currentItemLabel.setText("You're holding: -");
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

        if (rightClickMenu != null) {
            rightClickMenu.resize(width, height);
        }

        if (popUpMenu != null) {
            popUpMenu.resize(width, height);
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
    }

    @Override
    public void dispose() {
        running = false;
        commandExecutor.shutdownNow();
        shapeRenderer.dispose();
        uiStage.dispose();

        if (rightClickMenu != null) {
            rightClickMenu.dispose();
        }
        if (popUpMenu != null) {
            popUpMenu.dispose();
        }
        if (skin != null) {
            skin.dispose();
        }
        overlayRenderer.dispose();
        pauseMenuOverlay.dispose();
        craftingMenu.dispose();
        cookingMenu.dispose();
        for (ArtisanMenu artisan : artisans) {
            artisan.dispose();
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

        // prints the background
        for(int row = 0; row < Map.ROWS; row++){
            for(int col = 0; col < Map.COLS; col++){
                Tile toBePrinted = App.currentGame.getMap().getTile(row, col);
                printTileBackground(toBePrinted,
                    (col) * tileSideLength,
                    (row) * tileSideLength,
                    batch);
            }
        }

        // prints the area
        for(int row = 0; row < Map.ROWS; row++){
            for(int col = 0; col < Map.COLS; col++){
                Tile toBePrinted = App.currentGame.getMap().getTile(row, col);
                printTileArea(toBePrinted,
                    (col) * tileSideLength,
                    (row) * tileSideLength,
                    batch);
            }
        }

        // prints other objects
        for(int row = 0; row < Map.ROWS; row++){
            for(int col = 0; col < Map.COLS; col++){
                Tile toBePrinted = App.currentGame.getMap().getTile(row, col);
                printTileObject(toBePrinted,
                    (col) * tileSideLength,
                    (row) * tileSideLength,
                    batch);
            }
        }
    }

    public void printTileBackground(Tile tile, int x, int y, Batch batch){
        if(tile.getAreaSprite() != null) {
            batch.draw(tile.getAreaSprite(), x, y, tileSideLength, tileSideLength);
        }
    }
    public void printTileArea(Tile tile, int x, int y, Batch batch){
        com.example.models.map.Area area = tile.getArea();
        Position bottomLeft = area.getBottomLeftCorner();
        int drawX = bottomLeft.x * tileSideLength;
        int drawY = bottomLeft.y * tileSideLength;
        int realWidth = tileSideLength * area.getWidth();
        int realHeight = tileSideLength * area.getHeight();
        if(drawX == x && drawY == y) {
            if(area.getTexture() != null)  {
                batch.draw(area.getTexture(), drawX, drawY, realWidth, realHeight);
            }
        }
    }
    public void printTileObject(Tile tile, int x, int y, Batch batch) {
        if(tile.getObjectSprite() != null) {
            batch.draw(tile.getObjectSprite(), x, y);
        }
    }

    private void drawPlayer(SpriteBatch batch) {
        Position pos = game.getCurrentPlayer().getPosition();
        int drawX = pos.x * tileSideLength;
        int drawY = pos.y * tileSideLength;
        batch.draw(game.getCurrentPlayer().getCurrentFrame(), drawX, drawY);
    }

    private Tile surroundTile(int x, int y){
        Position pos = new Position(x/tileSideLength, y/tileSideLength);
        Tile tile = App.currentGame.getMap().getTile(pos);
        return tile;
    }

    private class GameInputProcessor implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            if (pauseMenuOverlay.isVisible() || craftingMenu.isVisible() || cookingMenu.isVisible() || (popUpMenu != null && popUpMenu.isVisible())) {
                return false;
            }

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
                    if (pauseMenuOverlay.isVisible()) {
                        pauseMenuOverlay.setVisible(false, 0);
                        restoreGameInput();
                    } else {
                        pauseMenuOverlay.setVisible(true, 1);
                        Gdx.input.setInputProcessor(pauseMenuOverlay.getStage());
                    }
                    return true;
                case Input.Keys.M:
                    if (pauseMenuOverlay.isVisible()) {
                        pauseMenuOverlay.setVisible(false, 0);
                        restoreGameInput();
                    }
                    else {
                        pauseMenuOverlay.setVisible(true, 4);
                        Gdx.input.setInputProcessor(pauseMenuOverlay.getStage());
                    }
                    return true;
                case Input.Keys.B:
                    if (craftingMenu.isVisible()) {
                        craftingMenu.setVisible(false);
                        restoreGameInput();
                    }
                    else {
                        craftingMenu.setVisible(true);
                        Gdx.input.setInputProcessor(craftingMenu.getStage());
                    }
                    return true;
                case Input.Keys.C:
                    if (cookingMenu.isVisible()){
                        cookingMenu.setVisible(false , 0);
                        restoreGameInput();
                    }
                    else {
                        cookingMenu.setVisible(true, 1);
                        Gdx.input.setInputProcessor(cookingMenu.getStage());
                    }
            }
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            if (pauseMenuOverlay.isVisible() || (popUpMenu != null && popUpMenu.isVisible())) {
                return false;
            }

            Player player = game.getCurrentPlayer();

            return switch (keycode) {
                case Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D -> {
                    player.setWalking(false);
                    yield true;
                }
                default -> false;
            };
        }

        @Override
        public boolean keyTyped(char c) {
            return false;
        }

        @Override
        public boolean touchDown(int x, int y, int pointer, int button) {
            if (pauseMenuOverlay.isVisible() || (popUpMenu != null && popUpMenu.isVisible())) {
                return false;
            }

            Vector3 worldCoords = mapCamera.getCamera().unproject(new Vector3(x, y, 0));
            int tileX = (int) (worldCoords.x / tileSideLength);
            int tileY = (int) (worldCoords.y / tileSideLength);

            if (tileX >= 0 && tileX < Map.COLS &&
                tileY >= 0 && tileY < Map.ROWS) {

                Tile clickedTile = App.currentGame.getTile(tileX, tileY);

                if (button == Input.Buttons.RIGHT) {
                    NPC clickedNPC = getNPCAtPosition(tileX, tileY);
                    if (clickedNPC != null) {
                        if(rightClickMenu != null){
                            rightClickMenu.dispose();
                        }
                        rightClickMenu = new NPCRightClickMenu(skin, clickedNPC, GameView.this::restoreGameInput);
                        rightClickMenu.show(x, y);
                        Gdx.input.setInputProcessor(rightClickMenu.getStage());
                        return true;
                    }

                    // Check for Player right-click
                    for(Player player: App.currentGame.getPlayers()) {
                        if(player.equals(App.currentGame.getCurrentPlayer())){
                            continue;
                        }
                        if(player.getPosition().x == tileX && player.getPosition().y == tileY){
                            if(rightClickMenu!=null){
                                rightClickMenu.dispose();
                            }
                            rightClickMenu = new PlayerRightClickMenu(skin, player, GameView.this::restoreGameInput);
                            rightClickMenu.show(x,y);
                            Gdx.input.setInputProcessor(rightClickMenu.getStage());
                            return true;
                        }
                    }
                }

                if (button == Input.Buttons.LEFT) {
                    CraftItem current = checkCraftItem();
                    if (current!=null){
                        boolean find = false;
                        for (ArtisanMenu artisan : artisans) {
                            if(artisan.getCraftItem() == current){
                                find = true;
                                if (artisan.isVisible()){
                                    artisan.setVisible(false);
                                    restoreGameInput();
                                }
                                else {
                                    artisan.setVisible(true);
                                    Gdx.input.setInputProcessor(artisan.getStage());
                                }
                            }
                        }
                        if (!find){
                            ArtisanMenu artisan = new ArtisanMenu(main, game, this::restoreGameInput , current);
                            artisans.add(artisan);
                            if (artisan.isVisible()){
                                artisan.setVisible(false);
                                restoreGameInput();
                            }
                            else {
                                artisan.setVisible(true);
                                Gdx.input.setInputProcessor(artisan.getStage());
                            }
                        }
                    }
                    NPC clickedNPC = getNPCAtPosition(tileX, tileY);
                    if (clickedNPC != null && clickedNPC.hasMessageForToday(App.currentGame.getCurrentPlayer())) {
                        // Show the NPC's message
                        String message = clickedNPC.meet(App.currentGame.getCurrentPlayer());

                        // Create and show message dialog
                        Dialog messageDialog = new Dialog("Message from " + clickedNPC.getName(), skin) {
                            @Override
                            protected void result(Object object) {
                                // Restore game input when dialog is closed
                                restoreGameInput();
                            }
                        };
                        messageDialog.text(message);
                        messageDialog.button("OK");
                        messageDialog.show(uiStage);

                        // Set input processor to the dialog's stage
                        Gdx.input.setInputProcessor(messageDialog.getStage());
                        return true;
                    }

                    // Regular left-click handling for adjacent tiles
                    if(checkCursorInAdjacent()) {
                        if(clickedTile.getArea() instanceof GreenHouse greenHouse && !greenHouse.isBuilt()){
                            popUpMenu = new GreenHouseMenu(skin, "Repair GREENHOUSE", this::restoreGameInput, greenHouse);
                            popUpMenu.show();
                            Gdx.input.setInputProcessor(popUpMenu.getStage());
                        }

                        else if (clickedTile.getArea() instanceof Store store) {
                            if(store.isOpen(game.getDateAndTime().getHour())) {
                                if(store instanceof MarnieRanch marnieRanch) {
                                    popUpMenu = new MarnieRanchMenu(skin, "Marnie's Ranch", this::restoreGameInput, marnieRanch);
                                    popUpMenu.show();
                                    Gdx.input.setInputProcessor(popUpMenu.getStage());
                                }
                                else if(store instanceof CarpenterShop carpenterShop) {
                                    popUpMenu = new CarpenterShopMenu(skin, "Carpenter Shop:", this::restoreGameInput, carpenterShop);
                                    popUpMenu.show();
                                    Gdx.input.setInputProcessor(popUpMenu.getStage());
                                }
                            }
                            else {
                                notificationLabel.showMessage("store is closed now!", Color.RED);
                            }
                        }

                        else {
                            Result result = GameController.useToolOrPlaceItem(game.getCurrentPlayer(), clickedTile);
                            notificationLabel.showMessage(result.message(), result.success() ? Color.BLACK : Color.RED);
                        }
                    }
                    return true;
                }
                NPC clickedNPC = getNPCAtPosition(tileX, tileY);
                if (clickedNPC != null && clickedNPC.hasMessageForToday(App.currentGame.getCurrentPlayer())) {
                    // Show the NPC's message
                    String message = clickedNPC.meet(App.currentGame.getCurrentPlayer());

                    // You can show this in a dialog or notification
                    Dialog messageDialog = new Dialog("Message from " + clickedNPC.getName(), skin);
                    messageDialog.text(message);
                    messageDialog.button("OK");
                    messageDialog.show(uiStage); // You'll need access to the stage here

                    return true;
                }
            }
            return false;
        }

        private void restoreGameInput() {
            Gdx.input.setInputProcessor(gameInputMultiplexer);
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

    private void renderHeldItemCursor(SpriteBatch batch) {
        Player player = game.getCurrentPlayer();

        Vector3 mouseWorld = mapCamera.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        int mouseTileX = (int) (mouseWorld.x / tileSideLength);
        int mouseTileY = (int) (mouseWorld.y / tileSideLength);

        Position playerPos = player.getPosition();

        if (Math.abs(mouseTileX - playerPos.getX()) <= 1 && Math.abs(mouseTileY - playerPos.getY()) <= 1) {
            shapeRenderer.setProjectionMatrix(mapCamera.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(mouseTileX * tileSideLength, mouseTileY * tileSideLength, tileSideLength, tileSideLength);
            shapeRenderer.end();

            if (player.getCurrentItem() != null) {
                TextureRegion heldSprite = player.getCurrentItem().getSprite();
                if (heldSprite != null) {
                    batch.begin();
                    batch.draw(
                        heldSprite,
                        mouseWorld.x - tileSideLength / 2f,
                        mouseWorld.y - tileSideLength / 2f,
                        tileSideLength,
                        tileSideLength
                    );
                    batch.end();
                }
            }
        }
    }
    private CraftItem checkCraftItem() {
        Vector3 mouseWorld = mapCamera.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        for (CraftItem item : game.getCurrentPlayer().getBuildedCrafts()) {
            Sprite sprite = item.getSprite();
            sprite.setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

            if (sprite.getBoundingRectangle().contains(mouseWorld.x, mouseWorld.y)) {
                return item;
            }
        }
        return null;
    }
    private boolean checkCursorInAdjacent() {
        Vector3 mouseWorld = mapCamera.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        int mouseTileX = (int) (mouseWorld.x / tileSideLength);
        int mouseTileY = (int) (mouseWorld.y / tileSideLength);

        Position playerPos = game.getCurrentPlayer().getPosition();

        return Math.abs(mouseTileX - playerPos.getX()) <= 1 && Math.abs(mouseTileY - playerPos.getY()) <= 1;
    }

    private void drawEveningTintOverlay() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        overlayRenderer.setProjectionMatrix(mapCamera.getCamera().combined);
        overlayRenderer.begin(ShapeRenderer.ShapeType.Filled);
        overlayRenderer.setColor(eveningTint);
        overlayRenderer.rect(0, 0, Map.COLS * tileSideLength, Map.ROWS * tileSideLength);
        overlayRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }


    private void drawNPCs(SpriteBatch batch) {
        Player currentPlayer = App.currentGame.getCurrentPlayer();
        NPCUIHelper uiHelper = NPCUIHelper.getInstance();

        for(NPC npc : DefaultNPCs.getInstance().defaultOnes.values()){
            Position pos = npc.getHomeLocation().getPosition();
            int drawX = pos.x * tileSideLength;
            int drawY = pos.y * tileSideLength;

            // Draw the NPC sprite
            batch.draw(npc.getSprite(), drawX, drawY, tileSideLength, tileSideLength);

            // Draw message indicator if NPC has a message for today
            if (npc.hasMessageForToday(currentPlayer)) {
                int indicatorX = drawX + tileSideLength - 18;
                int indicatorY = drawY + tileSideLength - 18;
                uiHelper.drawMessageIndicator(batch, indicatorX, indicatorY, 16);
            }
        }
    }

    private void drawMessageIndicator(SpriteBatch batch, int npcX, int npcY) {
        // You'll need to create or load a message indicator texture
        // For now, this assumes you have a messageIndicatorTexture
        // TextureRegion messageIcon = messageIndicatorTexture;

        int indicatorX = npcX + tileSideLength - 16; // Top-right corner
        int indicatorY = npcY + tileSideLength - 16;

        // If you don't have a texture yet, you can draw a simple colored rectangle
        // batch.draw(messageIcon, indicatorX, indicatorY, 16, 16);

        // Alternative: Draw a simple colored square (you'd need to create a 1x1 white pixel texture)
        // batch.setColor(Color.YELLOW);
        // batch.draw(whitePixelTexture, indicatorX, indicatorY, 16, 16);
        // batch.setColor(Color.WHITE); // Reset color
    }

    private NPC getNPCAtPosition(int tileX, int tileY) {
        for(NPC npc : DefaultNPCs.getInstance().defaultOnes.values()) {
            Position npcPos = npc.getHomeLocation().getPosition();
            if(npcPos.x == tileX && npcPos.y == tileY) {
                return npc;
            }
        }
        return null;
    }


}
