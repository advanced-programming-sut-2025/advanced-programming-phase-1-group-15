package com.example.client.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.example.client.Main;
import com.example.common.Game;
import com.example.common.cooking.Food;
import com.example.common.cooking.FoodType;

public class TradeMenu {
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));
    private boolean visible ;
    private final Table rootTable;
    private final Table request;
    private final Table response;
    private final Main main;
    private final Game game;
    private final Runnable onHideCallback;
    private final Label tooltipLabel = new Label("", skin);
    private final Container<Label> tooltipContainer = new Container<>(tooltipLabel);
    public TradeMenu(Main main, Game game, Runnable onHideCallback) {
        game.getCurrentPlayer().getInventory().addToBackPack(new Food(FoodType.SALMON_DINNER) , 3);
        this.main = main;
        this.game = game;
        this.onHideCallback = onHideCallback;
        this.stage = new Stage(new ScreenViewport(), main.getBatch());
        rootTable = new Table(skin);
        rootTable.setFillParent(false);
        rootTable.setSize(800, 700);
        rootTable.setPosition(Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f - 350);
        rootTable.setVisible(false);
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));
        Table tabBar = new Table(skin);
        TextButton requestTab = new TextButton("Request", skin);
        TextButton responseTab = new TextButton("Response", skin);
        TextButton historyTab = new TextButton("History", skin);
        tabBar.add(requestTab).pad(5);
        tabBar.add(responseTab).pad(5);
        tabBar.add(historyTab).pad(5);
        rootTable.add(tabBar).expandX().top().padTop(10).row();
        request = createRequestContent();
        response = createResponseContent();
        Stack stack = new Stack();
        stack.add(request);
        stack.add(response);
        changeTab(request);
        rootTable.add(stack).expand().fill().row();
        TextButton closeButton = new TextButton("X Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setVisible(false , 0);
                if (onHideCallback != null) {
                    onHideCallback.run();
                }
            }
        });
        rootTable.add(closeButton).right().pad(10);
        tooltipLabel.setColor(Color.BROWN);
        tooltipContainer.background(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));
        tooltipContainer.pad(20);
        tooltipContainer.setVisible(false);
        stage.addActor(rootTable);
        stage.addActor(tooltipContainer);
        requestTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(request);
            }
        });
        responseTab.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(response);
            }
        });
    }
    public void draw(float delta) {
        if (!visible) return;
        stage.act(delta);

        if (tooltipContainer.isVisible()) {
            tooltipContainer.pack();
            tooltipContainer.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        }
        stage.draw();
    }
    private Table createRequestContent() {
        Label titleLabel = new Label("request: ", skin); titleLabel.setColor(Color.FIREBRICK);
        Label descriptionLabel = new Label("Desc: ", skin);
        Image foodIcon = new Image();
        foodIcon.setSize(48, 48);
        foodIcon.setVisible(false);
        final Food[] current = new Food[1];
        TextButton cookButton = new TextButton("Cook", skin);
        Label errorLabel = new Label("", skin);
        cookButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (current[0] != null) {
                    if (isAvailable(current[0])) {
                        showError("You make it successfully" , errorLabel);
                        errorLabel.setColor(Color.GREEN);
                    } else {
                        if (game.getCurrentPlayer().getInventory().checkFilled()){
                            showError("You don't have enough capacity" , errorLabel);
                            errorLabel.setColor(Color.RED);
                        }
                        else{
                            showError("You don't have enough material" , errorLabel);
                            errorLabel.setColor(Color.RED);
                        }
                    }
                }
            }
        });
        Table itemTable = new Table(skin);
        for (Food food : game.getCurrentPlayer().getAvailableFoods()) {
            Label label = new Label(food.getName(), skin);
            if (!food.isAvailable()) {
                label.setColor(Color.LIGHT_GRAY);
            }
            else {
                label.setColor(Color.BROWN);
            }
            label.addListener(new InputListener() {
                @Override
                public boolean mouseMoved(InputEvent event, float x, float y) {
                    current[0] = food;
                    if (!food.isAvailable()) {
                        cookButton.setVisible(false);
                    } else {
                        cookButton.setVisible(true);
                    }
                    Sprite sprite = food.getSprite();
                    sprite.setSize(48, 48);
                    foodIcon.setDrawable(new TextureRegionDrawable(sprite));
                    foodIcon.setVisible(true);
                    return true;
                }
            });

            itemTable.add(label).left().pad(5).row();
        }
        ScrollPane scrollPane = new ScrollPane(itemTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        errorLabel.setVisible(false);
        descriptionLabel.setColor(Color.FIREBRICK); descriptionLabel.setWrap(true); descriptionLabel.setWidth(700);
        Table table = new Table(skin);
        Table bottomRow = new Table();
        bottomRow.top().right();
        bottomRow.add(descriptionLabel).padLeft(10).width(700);
        bottomRow.add(foodIcon).size(80, 80).right();
        table.add(titleLabel).padBottom(10).row();
        table.add(scrollPane).expand().fill().pad(10).row();
        table.add(bottomRow).bottom().row();
        table.add(errorLabel).width(700).row();
        table.add(cookButton).right().row();
        return table;
    }
    private Table createResponseContent() {
        Table table = new Table(skin);
        return table;
    }
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible, int tabNumber) {
        this.visible = visible;
        rootTable.setVisible(visible);
        if (visible) {
            refresh();
            switch (tabNumber) {
                case 1:
                    changeTab(request);
                    break;
                case 2:
                    changeTab(response);
                    break;
                default:
                    break;
            }
        }
    }

    public Stage getStage() {
        return stage;
    }
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    private void refresh() {
        request.clear(); response.clear();
        request.add(createRequestContent()).expand().fill();
        response.add(createResponseContent()).expand().fill();
    }
    private void changeTab(Table content) {
        request.setVisible(false);
        response.setVisible(false);
        content.setVisible(true);
    }
    public boolean isAvailable(Food food) {
        return true;
    }
    private void showError(String message, Label errorLabel) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                errorLabel.setVisible(false);
            }
        }, 2);
    }
//    public void run(Scanner scanner) {
//        String command = scanner.nextLine().trim();
//        if(Commands.checkShowCurrentMenuRegex(command)) {
//            System.out.println("trade menu");
//        }
//        else if(TradeMenuCommands.TRADE_MONEY.matches(command)) {
//            Matcher matcher = TradeMenuCommands.TRADE_MONEY.matcher(command);
//            matcher.matches();
//            System.out.println(TradeMenuController.tradeWithMoney(matcher.group("username"),
//                    matcher.group("type"),matcher.group("item"),matcher.group("amount"),matcher.group("price")));
//        }
//        else if(TradeMenuCommands.TRADE_ITEM.matches(command)) {
//            Matcher matcher = TradeMenuCommands.TRADE_ITEM.matcher(command);
//            matcher.matches();
//            System.out.println(TradeMenuController.tradeWithItem(matcher.group("username"),matcher.group("type"),
//                    matcher.group("item"),matcher.group("amount"),matcher.group("targetItem"),matcher.group("targetAmount")));
//        }
//        else if(TradeMenuCommands.TRADE_LIST.matches(command)) {
//            Matcher matcher = TradeMenuCommands.TRADE_LIST.matcher(command);
//            matcher.matches();
//            TradeMenuController.tradeList();
//        }
//        else if(TradeMenuCommands.TRADE_RESPONSE.matches(command)) {
//            Matcher matcher = TradeMenuCommands.TRADE_RESPONSE.matcher(command);
//            matcher.matches();
//            System.out.println(TradeMenuController.tradeResponse(matcher.group("response"),matcher.group("id")));
//        }
//        else if(TradeMenuCommands.SHOW_TRADE_HISTORY.matches(command)) {
//            Matcher matcher = TradeMenuCommands.SHOW_TRADE_HISTORY.matcher(command);
//            matcher.matches();
//            TradeMenuController.showTradeHistory();
//        }
//        else if (TradeMenuCommands.BACK.matches(command)) {
//            Matcher matcher = TradeMenuCommands.BACK.matcher(command);
//        }
//        else {
//            System.out.println("invalid command");
//        }
//    }
}
