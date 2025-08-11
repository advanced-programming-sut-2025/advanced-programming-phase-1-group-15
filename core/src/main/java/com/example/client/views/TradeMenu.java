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
import com.example.common.farming.Crop;
import com.example.common.farming.Fruit;
import com.example.common.foraging.ForagingCrop;
import com.example.common.tools.BackPackable;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class TradeMenu {
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));
    private boolean visible ;
    private final Table rootTable;
    private final Table request;
    private final Table offer;
    private final Table history;
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
        TextButton responseTab = new TextButton("Offer", skin);
        TextButton requestTab = new TextButton("Request", skin);
        TextButton historyTab = new TextButton("History", skin);
        tabBar.add(responseTab).pad(5);
        tabBar.add(requestTab).pad(5);
        tabBar.add(historyTab).pad(5);
        rootTable.add(tabBar).expandX().top().padTop(10).row();
        offer = createOfferContent();
        request = createRequestContent();
        history = createHistoryContent();
        Stack stack = new Stack();
        stack.add(request);
        stack.add(offer);
        stack.add(history);
        changeTab(offer);
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
                changeTab(offer);
            }
        });
        historyTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(history);
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
        Label titleLabel = new Label("Crate Request: ", skin); titleLabel.setColor(Color.FIREBRICK);
        Table table = new Table(skin);
        return table;
    }
    private Table createOfferContent() {
        Table table = new Table(skin);
        Label errorLabel = new Label("" , skin);
        Label titleLabel = new Label("Offer", skin); titleLabel.setColor(Color.FIREBRICK);
        TextButton send = new TextButton("send", skin);
        TextField userField = new TextField("Enter Player UserName for trade", skin);
        TextField wantedField = new TextField("Name Item you want", skin);
        TextField wantedNumber = new TextField("Number" , skin);
        TextButton addWantItem = new TextButton("add" , skin);
        TextField itemField = new TextField("Name Item to sell", skin);
        TextField itemNumber = new TextField("Number" , skin);
        TextButton addItem = new TextButton("add" , skin);
        addWantItem.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (game.getPlayerByUsername(userField.getText()) == null) {
                    showError("user not find" , errorLabel);
                    errorLabel.setColor(Color.RED);
                    return;
                }
                try{
                    int num = Integer.parseInt(wantedNumber.getText());
                    if (game.getCurrentPlayer().getWantedItems().containsKey(wantedField.getText())) {
                        game.getCurrentPlayer().getWantedItems().put(wantedField.getText(), game.getCurrentPlayer().getWantedItems().get(wantedField.getText()) + num);
                    }
                    else {
                        game.getCurrentPlayer().getWantedItems().put(wantedField.getText(), num);
                    }
                    showError("add successfully" , errorLabel);
                }catch (NumberFormatException e){
                    showError("invalid number" , errorLabel);
                    errorLabel.setColor(Color.RED);
                }
            }
        });
        addItem.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (game.getPlayerByUsername(userField.getText()) == null) {
                    showError("user not find" , errorLabel);
                    errorLabel.setColor(Color.RED);
                    return;
                }
                if (game.getCurrentPlayer().getInventory().getItemByName(itemField.getText()) == null) {
                    showError("You don't have this item" , errorLabel);
                    errorLabel.setColor(Color.RED);
                    return;
                }
                try{
                    BackPackable temp = game.getCurrentPlayer().getInventory().getItemByName(itemField.getText());
                    int num = Integer.parseInt(itemNumber.getText());
                    if (game.getCurrentPlayer().getInventory().getItemCount(itemField.getText()) < num) {
                        showError("you don't have enough item" , errorLabel);
                        return;
                    }
                    if (game.getCurrentPlayer().getItems().get(temp) != 0) {
                        game.getCurrentPlayer().getItems().put(temp, game.getCurrentPlayer().getItems().get(temp) + num);
                    }
                    else {
                        game.getCurrentPlayer().getItems().put(temp, num);
                    }
                    showError("add successfully" , errorLabel);
                }catch (NumberFormatException e){
                    showError("invalid number" , errorLabel);
                    errorLabel.setColor(Color.RED);
                }
            }
        });
        userField.getStyle().fontColor = Color.BLACK;
        wantedField.getStyle().fontColor = Color.BLACK;
        itemField.getStyle().fontColor = Color.BLACK;
        itemNumber.getStyle().fontColor = Color.BLACK;
        wantedNumber.getStyle().fontColor = Color.BLACK;
        table.add(titleLabel).row();
        table.add(userField).width(700).row();
        table.add(wantedField).width(400);
        table.add(wantedNumber).width(200).right().row();
        table.add(addWantItem).right().pad(10).row();
        table.add(itemField).width(400);
        table.add(itemNumber).width(200).right().row();
        table.add(addItem).right().pad(10).row();
        TextButton showWantedItem = new TextButton("show wanted item", skin);
        TextButton showItem = new TextButton("show item to sell", skin);
        Table contentArea = new Table(skin);
        table.add(contentArea).expand().fill().row();
        showWantedItem.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                table.add(contentArea).expand().fill();
                contentArea.clear();
                contentArea.add(showWantedTable()).expand().fill();
            }
        });
        showItem.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                table.add(contentArea).expand().fill();
                contentArea.clear();
                contentArea.add(showItemTable()).expand().fill();
            }
        });
        send.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        table.add(errorLabel).width(500).row();
        table.add(showWantedItem).left();
        table.add(showItem).right().row();
        table.add(send).right().pad(10).row();
        return table;
    }
    private Table showItemTable(){
        Table table = new Table(skin);
        TextButton removeItem = new TextButton("remove", skin);
        TextButton Back = new TextButton("back", skin);
        Label errorLabel = new Label("" , skin);
        List<String> itemList = new List<>(skin);
        String[] items = game.getCurrentPlayer().getItems().entrySet().stream()
            .map(entry -> entry.getKey() + " x" + entry.getValue())
            .toArray(String[]::new);
        itemList.setItems(items);
        ScrollPane scrollPane = new ScrollPane(itemList, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        BackPackable[] current = new BackPackable[1];
        itemList.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                int index = itemList.getSelectedIndex();
                if (index >= 0) {
                    String item = itemList.getItems().get(index);
                    if (item != null && !item.isEmpty()) {
                        String itemName = item.split(" x")[0];
                        current[0] = game.getCurrentPlayer().getInventory().getItemByName(itemName);
                        return true;
                    }
                }
                return false;
            }
        });
        removeItem.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (current[0] == null) {
                    showError("choose item to remove" , errorLabel);
                    errorLabel.setColor(Color.RED);
                    return;
                }
                game.getCurrentPlayer().getItems().remove(current[0]);
                table.clear();
                table.add(showItemTable()).expand().fill();
                showError("remove successfully" , errorLabel);
                errorLabel.setColor(Color.GREEN);
            }
        });
        Back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                refresh();
                changeTab(offer);
            }
        });
        table.add(scrollPane).expand().fill().pad(10).row();
        table.add(removeItem).right().row();
        table.add(Back).left().pad(10);
        return table;
    }
    private Table showWantedTable(){
        Table table = new Table(skin);
        TextButton removeItem = new TextButton("remove", skin);
        TextButton Back = new TextButton("back", skin);
        Label errorLabel = new Label("" , skin);
        List<String> itemList = new List<>(skin);
        String[] items = game.getCurrentPlayer().getWantedItems().entrySet().stream()
            .map(entry -> entry.getKey() + " x" + entry.getValue())
            .toArray(String[]::new);
        itemList.setItems(items);
        ScrollPane scrollPane = new ScrollPane(itemList, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        String[] current = new String[1];
        itemList.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                int index = itemList.getSelectedIndex();
                if (index >= 0) {
                    String item = itemList.getItems().get(index);
                    if (item != null && !item.isEmpty()) {
                        String itemName = item.split(" x")[0];
                        current[0] = itemName;
                        return true;
                    }
                }
                return false;
            }
        });
        removeItem.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (current[0] == null || current[0].isEmpty()) {
                    showError("choose item to remove" , errorLabel);
                    errorLabel.setColor(Color.RED);
                    return;
                }
                game.getCurrentPlayer().getWantedItems().remove(current[0]);
                table.clear();
                table.add(showWantedTable()).expand().fill();
                showError("remove successfully" , errorLabel);
                errorLabel.setColor(Color.GREEN);
            }
        });
        Back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                refresh();
                changeTab(offer);
            }
        });
        table.add(scrollPane).expand().fill().pad(10).row();
        table.add(removeItem).right().row();
        table.add(Back).left().pad(10);
        return table;
    }
    private Table createHistoryContent() {
        Label titleLabel = new Label("History: ", skin); titleLabel.setColor(Color.FIREBRICK);

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
                    changeTab(offer);
                    break;
                case 3:
                    changeTab(history);
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
        request.clear(); offer.clear(); history.clear();
        request.add(createRequestContent()).expand().fill();
        offer.add(createOfferContent()).expand().fill();
        history.add(createHistoryContent()).expand().fill();
    }
    private void changeTab(Table content) {
        request.setVisible(false);
        offer.setVisible(false);
        history.setVisible(false);
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
