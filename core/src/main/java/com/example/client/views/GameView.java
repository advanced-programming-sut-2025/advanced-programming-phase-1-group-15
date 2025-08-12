package com.example.client.views;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.example.client.Main;
import com.example.client.models.GraphicalModels.*;
import com.example.client.models.GraphicalModels.PopUpMenus.*;
import com.example.common.map.Area;
import com.example.common.relation.PlayerFriendship;
import com.example.common.stores.*;
import com.example.client.controllers.CheatCodeController;
import com.example.client.controllers.ClientGameController;
import com.example.client.models.ClientApp;
import com.example.common.Game;
import com.example.client.models.GraphicalModels.RightClickMenus.NPCRightClickMenu;
import com.example.client.models.GraphicalModels.RightClickMenus.PlayerRightClickMenu;
import com.example.client.models.GraphicalModels.RightClickMenus.RightClickMenu;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.animals.Animal;
import com.example.common.crafting.CraftItem;
import com.example.common.map.GreenHouse;
import com.example.common.map.Map;
import com.example.common.map.Position;
import com.example.common.map.Tile;
import com.example.common.npcs.DefaultNPCs;
import com.example.common.npcs.NPC;
import com.example.common.time.DateAndTime;
import com.example.common.time.Season;
import com.example.common.weather.WeatherOption;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final GameOverlayRenderer overlayRenderer;

    // UI
    private Table hudTable;
    private Label energyLabel;
    private Label currentItemLabel;
    private Label Message;
    private Image image;
    private TextButton TradeMessage;
    private TextButton ChatButton;
    private NotificationLabel notificationLabel;
    private boolean isNotificationShowing = false;
    private Runnable cancelShow = () -> {
        isNotificationShowing = false;
    };
    private ScoreboardWidget scoreboardWidget;
    private boolean marriageNotificationShown = false;

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
    private final BlackSmithMenu blackSmithMenu;
    private final FishShopMenu fishShopMenu;
    private final JojaMartMenu jojaMartMenu;
    private final PierreGeneralStoreMenu pierreGeneralStoreMenu;
    private final StarDropSaloonMenu starDropSaloonMenu;
    private final TradeMenu tradeMenu;
    private final ReActMenu reActMenu;
    private final ChatMenu chatMenu;
    private ArrayList<ArtisanMenu> artisans = new ArrayList<>();

    float sinceLastInfoUpdate = 0;


    public GameView(Game game, Main main) {
        this.game = game;
        this.main = main;

        game.build();

        mapCamera = new MapCamera(game.getCurrentPlayer());
        overlayRenderer = new GameOverlayRenderer(main.getBatch(), 16);
        uiStage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));
        this.scoreboardWidget = new ScoreboardWidget(game, skin);

        gameInputProcessor = new GameInputProcessor();

        commandExecutor = Executors.newSingleThreadExecutor();
        commandExecutor.submit(this::readTerminalInput);
        this.pauseMenuOverlay = new PauseMenuOverlay(main, game, this::restoreGameInput);
        this.craftingMenu = new CraftingMenu(main, game, this::restoreGameInput);
        this.cookingMenu = new CookingMenu(main, game, this::restoreGameInput);
        this.blackSmithMenu = new BlackSmithMenu(main, game, this::restoreGameInput);
        this.jojaMartMenu = new JojaMartMenu(main,game,this::restoreGameInput);
        this.fishShopMenu = new FishShopMenu(main,game,this::restoreGameInput);
        this.pierreGeneralStoreMenu = new PierreGeneralStoreMenu(main , game , this::restoreGameInput);
        this.starDropSaloonMenu = new StarDropSaloonMenu(main, game, this::restoreGameInput);
        this.reActMenu = new ReActMenu(main , game , this::restoreGameInput);
        this.tradeMenu = new TradeMenu(main, game, this::restoreGameInput);
        this.chatMenu = new ChatMenu(main, game, this::restoreGameInput);
        ClientApp.setCurrentGameView(this);
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
        uiStage.addActor(scoreboardWidget);
    }

    private void createHUDComponents() {
        energyLabel = new Label("", skin);
        energyLabel.setColor(Color.FIREBRICK); energyLabel.setAlignment(Align.left);
        currentItemLabel = new Label("", skin);
        Message = new Label("", skin);
        image = new Image();
        TradeMessage = new TextButton("You have new Trade Request, Click to see" , skin);
        ChatButton = new TextButton("Chat", skin);
        TradeMessage.setVisible(false);
        currentItemLabel.setColor(Color.FIREBRICK); currentItemLabel.setAlignment(Align.left);
        this.notificationLabel = new NotificationLabel(skin);

        hudTable.add(energyLabel).padTop(5).padLeft(10).left().row();
        hudTable.add(currentItemLabel).padTop(5).padLeft(10).left().row();
        TextButton friendsButton = new TextButton("Friends", skin);
        TextButton toolsButton = new TextButton("Tools", skin);
        TextButton scoreboardButton = new TextButton("Scoreboard", skin);
        TextButton sendMessage = new TextButton("Send Message", skin);
        TextButton groupQuestButton = new TextButton("Group Quests", skin);
        TextButton TradeButton = new TextButton("Trade", skin);
        float buttonWidth = 200f;
        float buttonHeight = 60f;
        ChatButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                if (chatMenu.isVisible()){
                    chatMenu.setVisible(false , 0);
                    restoreGameInput();
                }
                else {
                    chatMenu.setVisible(true, 1);
                    Gdx.input.setInputProcessor(chatMenu.getStage());
                }
            }
        });
        TradeMessage.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if (tradeMenu.isVisible()){
                    tradeMenu.setVisible(false , 0);
                    restoreGameInput();
                }
                else {
                    tradeMenu.setVisible(true, 2);
                    Gdx.input.setInputProcessor(tradeMenu.getStage());
                }
            }
        });
        TradeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (tradeMenu.isVisible()){
                    tradeMenu.setVisible(false , 0);
                    restoreGameInput();
                }
                else {
                    tradeMenu.setVisible(true, 1);
                    Gdx.input.setInputProcessor(tradeMenu.getStage());
                }
            }
        });
        sendMessage.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                if (reActMenu.isVisible()){
                    reActMenu.setVisible(false , 0);
                    restoreGameInput();
                }
                else {
                    reActMenu.setVisible(true, 1);
                    Gdx.input.setInputProcessor(reActMenu.getStage());
                }
            }
        });
        friendsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                popUpMenu = new FriendsMenu(skin, "Friends:", this::restoreGameInput);
                popUpMenu.show();
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
        groupQuestButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                popUpMenu = new GroupQuestMenu(skin,game.getGroupQuestManager(),this::restoreGameInput);
                popUpMenu.show();
                Gdx.input.setInputProcessor(popUpMenu.getStage());
            }
            private void restoreGameInput() {
                Gdx.input.setInputProcessor(gameInputMultiplexer);
            }
        });

        scoreboardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                scoreboardWidget.toggleVisibility();
            }
        });

        hudTable.add(scoreboardButton).padTop(5).padLeft(5).size(buttonWidth, buttonHeight).left().row();
        hudTable.add(friendsButton).padTop(5).padLeft(5).size(buttonWidth, buttonHeight).left().row();
        hudTable.add(toolsButton).padLeft(5).size(buttonWidth, buttonHeight).left().row();
        hudTable.add(sendMessage).padTop(5).padLeft(5).size(buttonWidth, buttonHeight).left().row();
        hudTable.add(groupQuestButton).padTop(5).padLeft(5).size(buttonWidth, buttonHeight).left().row();
        hudTable.add(TradeButton).padLeft(5).size(buttonWidth, buttonHeight).left().row();
        hudTable.add(ChatButton).padLeft(5).size(buttonWidth, buttonHeight).left().row();
        hudTable.add(Message).expandX().bottom().center().padTop(200).row();
        hudTable.add(image).expandX().bottom().center().row();
        hudTable.add(notificationLabel).expandX().bottom().center().row();
        hudTable.add(TradeMessage).expandX().bottom().center().width(700).row();
    }
    private void setupInputHandling() {
        gameInputMultiplexer = new InputMultiplexer();
        gameInputMultiplexer.addProcessor(uiStage);
        gameInputMultiplexer.addProcessor(gameInputProcessor);
        Gdx.input.setInputProcessor(gameInputMultiplexer);
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
        for(Player player : game.getPlayers()) {
            player.updateAnimation(delta);
        }
        game.getDateAndTime().updateDateAndTime(delta);
        updateMessageEmoji(delta);
        updateTradeMessage(delta);
        updateJoja();
        processNotifications(delta);
        processMarriageProposal(delta);
        SpriteBatch batch = main.getBatch();
        renderMap(batch);
        overlayRenderer.draw(delta, game, mapCamera);
        renderHeldItemCursor(batch);
        renderHUD(batch, delta);
        scoreboardWidget.update(delta);

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
        blackSmithMenu.draw(delta);
        jojaMartMenu.draw(delta);
        fishShopMenu.draw(delta);
        pierreGeneralStoreMenu.draw(delta);
        starDropSaloonMenu.draw(delta);
        reActMenu.draw(delta);
        tradeMenu.draw(delta);
        for (ArtisanMenu artisan : artisans) {
            artisan.draw(delta);
        }

        sinceLastInfoUpdate += delta;
        if(sinceLastInfoUpdate >= 1){
            if(scoreboardWidget.isVisible()){
                game.notifyOthersScoreInfo();
            }
            sinceLastInfoUpdate = 0;
        }
    }
    private void renderMap(SpriteBatch batch) {
        mapCamera.setPlayer(game.getCurrentPlayer());
        mapCamera.update();
        batch.setProjectionMatrix(mapCamera.getCamera().combined);
        batch.begin();
        showMap(batch);
        drawNPCs(batch);
        drawPlayers(batch);

        batch.end();
    }

    private void renderHUD(SpriteBatch batch, float delta) {
        batch.setProjectionMatrix(uiStage.getCamera().combined);
        batch.begin();

        drawClock(batch);

        batch.end();
    }
    private void updateJoja() {
        if (game.getCurrentPlayer().isUpdateJoja()){
            ArrayList<JojaMartItems> foragingSeed = new ArrayList<>(Arrays.asList(JojaMartItems.values()));
            JojaMartItems current = null;
            for (JojaMartItems jojaMartItems : foragingSeed) {
                if(game.getCurrentPlayer().getJojaItem().equalsIgnoreCase(jojaMartItems.getName())) {
                    current = jojaMartItems;
                    break;
                }
            }
            current.setSold(game.getCurrentPlayer().getJojaNumber()+current.getSold());
            if (current.getSold() >= current.dailyLimit){
                current.setAvailable(false);
            }
            game.getCurrentPlayer().setUpdateJoja(false);
        }
    }
    private void updateTradeMessage(float delta) {
        if (game.getCurrentPlayer().isRefresh()){
            tradeMenu.refresh();
            if (tradeMenu.isVisible()){
                tradeMenu.setVisible(true , 2);
                game.getCurrentPlayer().setRefresh(false);
            }
        }
        if (game.getCurrentPlayer().isNewTrade()){
            TradeMessage.setVisible(true);
        }
        game.getCurrentPlayer().updateTrade(delta);
        if (!game.getCurrentPlayer().isNewTrade()){
            TradeMessage.setVisible(false);
        }
    }
    private void updateMessageEmoji(float delta) {
        if (game.getCurrentPlayer().getMessage()== null){
            Message.setText("");
            Message.setVisible(false);
        }
        if(game.getCurrentPlayer().getMessage()!=null && !game.getCurrentPlayer().getMessage().isEmpty()){
            Message.setText(game.getCurrentPlayer().getMessage());
            Message.setColor(Color.BLACK);
            Message.setVisible(true);
            game.getCurrentPlayer().updateLabel(delta);
            if (game.getCurrentPlayer().getMessage()==null){
                Message.setText("");
                Message.setVisible(false);
            }
        }
        if (game.getCurrentPlayer().getActiveEmoji() == null){
            image.setVisible(false);
        }
        else{
            image.setDrawable(new TextureRegionDrawable(game.getCurrentPlayer().getActiveEmoji()));
            image.setVisible(true);
            image.setSize(64,64);
            System.out.println(game.getCurrentPlayer().getActiveEmoji());
            game.getCurrentPlayer().updateLabel(delta);
            if (game.getCurrentPlayer().getActiveEmoji()==null){
                image.setVisible(false);
            }
        }
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
        dispose();
    }

    @Override
    public void dispose() {
        running = false;
        commandExecutor.shutdownNow();
        overlayRenderer.dispose();
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
        if (scoreboardWidget != null) {
            scoreboardWidget.remove();
        }
        overlayRenderer.dispose();
        pauseMenuOverlay.dispose();
        craftingMenu.dispose();
        cookingMenu.dispose();
        blackSmithMenu.dispose();
        fishShopMenu.dispose();
        jojaMartMenu.dispose();
        pierreGeneralStoreMenu.dispose();
        starDropSaloonMenu.dispose();
        reActMenu.dispose();
        tradeMenu.dispose();
        for (ArtisanMenu artisan : artisans) {
            artisan.dispose();
        }
    }

    private void readTerminalInput() {
        Scanner scanner = new Scanner(System.in);
        while (running) {
            if (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                //if(game.isAdmin()) {
                    Result result = CheatCodeController.processCommand(command);
                    System.out.println(result);
                //}
                //else {
                //    System.out.println("you need to have admin privileges to run cheat codes.");
                //}
            }
        }
        scanner.close();
    }

    public void showMap(Batch batch) {
        Map currentMap = game.getMap();

        // prints the background
        for(int row = 0; row < Map.ROWS; row++){
            for(int col = 0; col < Map.COLS; col++){
                Tile toBePrinted = ClientApp.currentGame.getMap().getTile(row, col);
                printTileBackground(toBePrinted,
                    (col) * tileSideLength,
                    (row) * tileSideLength,
                    batch);
            }
        }

        // prints the area
        for(int row = 0; row < Map.ROWS; row++){
            for(int col = 0; col < Map.COLS; col++){
                Tile toBePrinted = ClientApp.currentGame.getMap().getTile(row, col);

                printTileArea(toBePrinted,
                    (col) * tileSideLength,
                    (row) * tileSideLength,
                    batch);
            }
        }

        // prints other objects
        for(int row = 0; row < Map.ROWS; row++){
            for(int col = 0; col < Map.COLS; col++){
                Tile toBePrinted = ClientApp.currentGame.getMap().getTile(row, col);
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
        Area area = tile.getArea();
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

    private void drawPlayers(SpriteBatch batch) {
        for(Player player : game.getPlayers()) {
            Position pos = player.getPosition();
            int drawX = pos.x * tileSideLength;
            int drawY = pos.y * tileSideLength;
            batch.draw(player.getCurrentFrame(), drawX, drawY);
        }
    }

    private Tile surroundTile(int x, int y){
        Position pos = new Position(x/tileSideLength, y/tileSideLength);
        Tile tile = ClientApp.currentGame.getMap().getTile(pos);
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
                    player.walk(0, +1);
                    return true;
                case Input.Keys.S:
                    player.walk(0, -1);
                    return true;
                case Input.Keys.A:
                    player.walk(-1, 0);
                    return true;
                case Input.Keys.D:
                    player.walk(+1, 0);
                    return true;
                case Input.Keys.E:
                    overlayRenderer.setLightning();
                    return true;
                case Input.Keys.TAB:
                    scoreboardWidget.toggleVisibility();
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
                        pauseMenuOverlay.refresh();
                        pauseMenuOverlay.setVisible(false, 0);
                        restoreGameInput();
                    }
                    else {
                        pauseMenuOverlay.refresh();
                        pauseMenuOverlay.setVisible(true, 4);
                        Gdx.input.setInputProcessor(pauseMenuOverlay.getStage());
                    }
                    return true;
                case Input.Keys.B:
                    if (craftingMenu.isVisible()) {
                        craftingMenu.refresh();
                        craftingMenu.setVisible(false);
                        restoreGameInput();
                    }
                    else {
                        craftingMenu.refresh();
                        craftingMenu.setVisible(true);
                        Gdx.input.setInputProcessor(craftingMenu.getStage());
                    }
                    return true;
                case Input.Keys.C:
                    if (cookingMenu.isVisible()){
                        craftingMenu.refresh();
                        cookingMenu.setVisible(false , 0);
                        restoreGameInput();
                    }
                    else {
                        craftingMenu.refresh();
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
                    ClientGameController.sendPlayerStopMessage();
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

                Tile clickedTile = ClientApp.currentGame.getTile(tileX, tileY);

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
                    for(Player player: ClientApp.currentGame.getPlayers()) {
                        if(player.equals(ClientApp.currentGame.getCurrentPlayer())){
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

                    if(clickedTile.getObjectInTile() instanceof Animal animal) {
                        popUpMenu = new AnimalMenu(skin, animal.getName(), this::restoreGameInput, animal);
                        popUpMenu.show();
                        Gdx.input.setInputProcessor(popUpMenu.getStage());
                        return true;
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
                        return true;
                    }
                    NPC clickedNPC = getNPCAtPosition(tileX, tileY);
                    if (clickedNPC != null && clickedNPC.hasMessageForToday(ClientApp.currentGame.getCurrentPlayer())) {

                        Dialog loadingDialog = new Dialog("", skin);
                        loadingDialog.text("Talking to " + clickedNPC.getName() + "...");
                        loadingDialog.show(uiStage);

                        Gdx.input.setInputProcessor(loadingDialog.getStage());

                        clickedNPC.meetAsync(ClientApp.currentGame.getCurrentPlayer(),
                            (message ) -> {
                                Gdx.app.postRunnable(() -> {
                                    loadingDialog.hide();
                                    showMessageDialog(clickedNPC.getName(), message);
                                });
                            },
                            (error) -> {
                                Gdx.app.postRunnable(() -> {
                                    loadingDialog.hide();
                                    showMessageDialog(clickedNPC.getName(), "Sorry, I can't talk right now. Try again later!");
                                });
                            }
                        );

                        return true;
                    }
                    if(checkCursorInAdjacent()) {
                        if(clickedTile.getArea() instanceof GreenHouse greenHouse && !greenHouse.isBuilt()){
                            popUpMenu = new GreenHouseMenu(skin, "Repair GREENHOUSE", this::restoreGameInput);
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
                                else if (store instanceof Blacksmith){
                                    if (blackSmithMenu.isVisible()){
                                        blackSmithMenu.setVisible(false , 0);
                                        restoreGameInput();
                                    }
                                    else {
                                        blackSmithMenu.setVisible(true, 1);
                                        Gdx.input.setInputProcessor(blackSmithMenu.getStage());
                                    }
                                }
                                else if (store instanceof FishShop){
                                    if (fishShopMenu.isVisible()){
                                        fishShopMenu.setVisible(false , 0);
                                        restoreGameInput();
                                    }
                                    else {
                                        fishShopMenu.setVisible(true, 1);
                                        Gdx.input.setInputProcessor(fishShopMenu.getStage());
                                    }
                                }
                                else if (store instanceof JojaMart) {
                                    if (jojaMartMenu.isVisible()){
                                        jojaMartMenu.setVisible(false , 0);
                                        restoreGameInput();
                                    }
                                    else {
                                        jojaMartMenu.setVisible(true, 1);
                                        Gdx.input.setInputProcessor(jojaMartMenu.getStage());
                                    }
                                }
                                else if (store instanceof PierreGeneralStore) {
                                    if (pierreGeneralStoreMenu.isVisible()){
                                        pierreGeneralStoreMenu.setVisible(false , 0);
                                        restoreGameInput();
                                    }
                                    else {
                                        pierreGeneralStoreMenu.setVisible(true, 1);
                                        Gdx.input.setInputProcessor(pierreGeneralStoreMenu.getStage());
                                    }
                                }
                                else if (store instanceof StarDropSaloon) {
                                    if (starDropSaloonMenu.isVisible()){
                                        starDropSaloonMenu.setVisible(false , 0);
                                        restoreGameInput();
                                    }
                                    else {
                                        starDropSaloonMenu.setVisible(true, 1);
                                        Gdx.input.setInputProcessor(starDropSaloonMenu.getStage());
                                    }
                                }
                            }
                            else {
                                notificationLabel.showMessage("store is closed now!", Color.RED, cancelShow);
                            }
                        }

                        else {
                            Result result = ClientGameController.useToolOrPlaceItem(game.getCurrentPlayer(), clickedTile);
                            notificationLabel.showMessage(result.message(), result.success() ? Color.BLACK : Color.RED, cancelShow);
                        }
                    }
                    return true;
                }
                NPC clickedNPC = getNPCAtPosition(tileX, tileY);
                if (clickedNPC != null && clickedNPC.hasMessageForToday(ClientApp.currentGame.getCurrentPlayer())) {

                    Dialog loadingDialog = new Dialog("", skin);
                    loadingDialog.text("Talking to " + clickedNPC.getName() + "...");
                    loadingDialog.show(uiStage);

                    Gdx.input.setInputProcessor(loadingDialog.getStage());

                    clickedNPC.meetAsync(ClientApp.currentGame.getCurrentPlayer(),
                        (message ) -> {
                            Gdx.app.postRunnable(() -> {
                                loadingDialog.hide();
                                showMessageDialog(clickedNPC.getName(), message);
                            });
                        },
                        (error) -> {
                            Gdx.app.postRunnable(() -> {
                                loadingDialog.hide();
                                showMessageDialog(clickedNPC.getName(), "Sorry, I can't talk right now. Try again later!");
                            });
                        }
                    );

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

    private void drawNPCs(SpriteBatch batch) {
        Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();
        NPCUIHelper uiHelper = NPCUIHelper.getInstance();

        for(NPC npc : DefaultNPCs.getInstance().defaultOnes.values()){
            Position pos = npc.getHomeLocation().getPosition();
            int drawX = pos.x * tileSideLength;
            int drawY = pos.y * tileSideLength;

            batch.draw(npc.getSprite(), drawX, drawY, tileSideLength, tileSideLength);

            if (npc.hasMessageForToday(currentPlayer)) {
                int indicatorX = drawX + tileSideLength - 18;
                int indicatorY = drawY + tileSideLength - 18;
                uiHelper.drawMessageIndicator(batch, indicatorX, indicatorY, 6);
            }
        }
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

    private void processNotifications(float delta) {
        if (!notificationLabel.isShowing() && !game.getCurrentPlayer().getNotifications().isEmpty()) {
            PlayerFriendship.Message message = game.getCurrentPlayer().readNotification();
            boolean started = notificationLabel.showMessage(message.message(), Color.BLUE, () -> {
            });
            if (!started) {
                game.getCurrentPlayer().addNotification(message);
            } else {
                System.out.println("notification is called");
            }
        }
    }

    private void processMarriageProposal(float delta) {
        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer.isNotifiedForMarriage() && !marriageNotificationShown) {
            String proposerName = currentPlayer.getMarriageAsker().getUsername();
            MarriageProposalNotification menu =
                new MarriageProposalNotification(skin, this::onMarriageNotificationHide);
            menu.setProposal(proposerName);
            popUpMenu = menu;

            menu.show();

            if (gameInputMultiplexer != null) {
                gameInputMultiplexer.addProcessor(0, menu.getStage());
            } else {
                Gdx.input.setInputProcessor(menu.getStage());
            }

            marriageNotificationShown = true;
        }
    }

    private void onMarriageNotificationHide() {
        marriageNotificationShown = false;
        restoreGameInput();

        Player currentPlayer = game.getCurrentPlayer();
        currentPlayer.setNotifiedForMarriage(false);
    }

    private void showMessageDialog(String npcName, String message) {
        Dialog messageDialog = new Dialog("Message from " + npcName, skin) {
            @Override
            protected void result(Object object) {
                restoreGameInput();
            }
        };
        messageDialog.text(message);
        messageDialog.button("OK");
        messageDialog.show(uiStage);

        Gdx.input.setInputProcessor(messageDialog.getStage());
    }

    public void setPopUpMenu(PopUpMenu popUpMenu) {
        this.popUpMenu = popUpMenu;
    }
}
