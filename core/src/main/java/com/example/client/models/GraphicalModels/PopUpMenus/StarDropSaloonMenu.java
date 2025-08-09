package com.example.client.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.example.common.animals.AnimalProduct;
import com.example.common.animals.AnimalProductType;
import com.example.common.cooking.Food;
import com.example.common.cooking.FoodType;
import com.example.common.crafting.CraftItem;
import com.example.common.crafting.CraftItemType;
import com.example.common.farming.Crop;
import com.example.common.farming.Crops;
import com.example.common.farming.Fruit;
import com.example.common.foraging.ForagingCrop;
import com.example.common.foraging.ForagingMineral;
import com.example.common.map.Area;
import com.example.client.controllers.ClientGameController;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.Result;
import com.example.common.map.AreaType;
import com.example.common.map.Farm;
import com.example.common.map.Tile;
import com.example.common.stores.BlackSmithItems;
import com.example.common.stores.Blacksmith;
import com.example.common.tools.BackPackable;
import com.example.common.tools.Fridge;
import com.example.common.tools.TrashCan;

import java.util.ArrayList;
import java.util.Arrays;

public class StarDropSaloonMenu{
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));
    private boolean visible ;
    private final Table rootTable;
    private final Table buy;
    private final Table sell;
    private final Main main;
    private final Game game;
    private final Runnable onHideCallback;
    private final Label tooltipLabel = new Label("", skin);
    private final TextButton add = new TextButton("+", skin);
    private final TextButton remove = new TextButton("-", skin);
    private final Container<Label> tooltipContainer = new Container<>(tooltipLabel);
    public StarDropSaloonMenu(Main main, Game game, Runnable onHideCallback) {
        game.getCurrentPlayer().addToAvailableFoods(new Food(FoodType.TRIPLE_SHOT_ESPRESSO));
        game.getCurrentPlayer().addToAvailableFoods(new Food(FoodType.BACKED_FISH));
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
        TextButton recipeTab = new TextButton("Buy", skin);
        TextButton fridgeTab = new TextButton("Sell", skin);
        tabBar.add(recipeTab).pad(5);
        tabBar.add(fridgeTab).pad(5);
        rootTable.add(tabBar).expandX().top().padTop(10).row();
        buy = createBuyContent();
        sell = createSellContent();
        Stack stack = new Stack();
        stack.add(buy);
        stack.add(sell);
        changeTab(buy);
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
        recipeTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(buy);
            }
        });
        fridgeTab.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(sell);
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
    private Table createBuyContent() {
        Label titleLabel = new Label("Star Drop Saloon: ", skin); titleLabel.setColor(Color.FIREBRICK);
        Label descriptionLabel = new Label("Recipe Price: ", skin);
        Label errorLabel = new Label("", skin);
        Image foodIcon = new Image();
        foodIcon.setSize(48, 48);
        foodIcon.setVisible(false);
        ArrayList<FoodType> foodTypes = new ArrayList<>(Arrays.asList(FoodType.values()));
        final FoodType[] current = new FoodType[1];
        TextButton buy = new TextButton("Buy", skin);
        buy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (current[0] != null) {
                    if (isAvailable(current[0])) {
                        showError("You buy it successfully" , errorLabel);
                        current[0] = null;
                        descriptionLabel.setVisible(false);
                        foodIcon.setVisible(false);
                        errorLabel.setColor(Color.GREEN);
                    } else {
                        for (Food item : game.getCurrentPlayer().getAvailableFoods()) {
                            if (item.getFoodType()==(current[0])) {
                                if (item.isAvailable()){
                                    showError("You have this Recipe" , errorLabel);
                                    current[0] = null;
                                    descriptionLabel.setVisible(false);
                                    foodIcon.setVisible(false);
                                    errorLabel.setColor(Color.RED);
                                    return;
                                }
                                showError("You don't have enough gold" , errorLabel);
                                current[0] = null;
                                descriptionLabel.setVisible(false);
                                foodIcon.setVisible(false);
                                errorLabel.setColor(Color.RED);
                                return;
                            }
                        }
                    }
                }
            }
        });
        Table itemTable = new Table(skin);
        for (FoodType item : foodTypes) {
            Label label = new Label(item.getName(), skin);
            label.setColor(Color.BROWN);
            label.addListener(new InputListener() {
                @Override
                public boolean mouseMoved(InputEvent event, float x, float y) {
                    current[0] = item;
                    descriptionLabel.setText(current[0].getName() +" Price :" + item.price);
                    descriptionLabel.setVisible(true);
                    Sprite sprite = new Sprite(current[0].getTexture());
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
        bottomRow.add(descriptionLabel).right().padLeft(10).width(700);
        table.add(titleLabel).padBottom(10).row();
        table.add(scrollPane).height(200).expandX().fillX().pad(10).row();;
        table.add(bottomRow).bottom().row();
        table.add(errorLabel).width(700).row();
        bottomRow.add(foodIcon).size(80, 80).left().row();
        table.add(buy).right().row();
        return table;
    }
    private Table createSellContent() {
        if (game.getCurrentPlayer().getTrashCan().getTrashes().isEmpty()){
            Label label = new Label("No item to sell", skin);
            label.setColor(Color.RED);
            Table tab = new Table(skin);
            tab.add(label);
            return tab;
        }
        final int[] num = {1};
        final BackPackable[] current = new BackPackable[1];
        TextButton sell = new TextButton("Sell", skin);
        Label errorLabel = new Label("", skin);
        Label Final = new Label("", skin);
        Final.setColor(Color.BROWN);
        Final.setVisible(false);
        Label descriptionLabel = new Label("", skin);
        TrashCan trashCan = game.getCurrentPlayer().getTrashCan();
        List<String> itemList = new List<>(skin);
        String[] items = trashCan.getTrashes().entrySet().stream()
            .map(entry -> entry.getKey().getName() + "  x" + entry.getValue())
            .toArray(String[]::new);
        itemList.setItems(items);
        ScrollPane scrollPane = new ScrollPane(itemList, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        Label titleLabel = new Label("Trash Can Items:", skin); titleLabel.setColor(Color.FIREBRICK);
        itemList.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                int index = itemList.getSelectedIndex();
                if (index >= 0) {
                    String item = itemList.getItems().get(index);
                    if (item != null && !item.isEmpty()) {
                        String itemName = item.split(" x")[0].trim();
                        System.out.println(itemName);
                        current[0] = trashCan.getItemByName(itemName);
                        if (current[0] != null) {
                            descriptionLabel.setText("Price :" + current[0].getPrice());
                            descriptionLabel.setVisible(true);
                            descriptionLabel.setColor(Color.BROWN);
                            Final.setText("Total number: "+num[0] +"    Total Price = " + num[0]*current[0].getPrice());
                            Final.setVisible(true);
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        sell.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (current[0] == null) {
                    return;
                }
                game.getCurrentPlayer().getTrashCan().removeCountFromTrashCan(current[0],num[0]);
                game.getCurrentPlayer().setGold(game.getCurrentPlayer().getGold()+ current[0].getPrice()*num[0]);
                refresh();
                Final.setVisible(false);
                current[0] = null;
                num[0] = 1;
                showError("You sell item successfully" , errorLabel);
                errorLabel.setVisible(true);
                errorLabel.setColor(Color.GREEN);
            }
        });
        add.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (current[0]!=null){
                    num[0] = Math.min(num[0]+1 , game.getCurrentPlayer().getTrashCan().getTrashes().get(current[0]));
                    Final.setText("Total number: "+num[0] + "    Total Price = " + num[0]*current[0].getPrice());
                    Final.setVisible(true);
                }
            }
        });
        remove.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (current[0]!=null){
                    num[0] = Math.max(1, num[0]-1);
                    Final.setText("Total number: "+num[0] + "    Total Price = " + num[0]*current[0].getPrice());
                    Final.setVisible(true);
                }
            }
        });
        Table table = new Table(skin);
        Table bottomRow = new Table();
        table.add(titleLabel).padBottom(10).row();
        table.add(scrollPane).expand().fill().pad(10).row();
        table.add(bottomRow).bottom();
        Table quantityRow = new Table();
        quantityRow.add(remove).padRight(5);
        quantityRow.add(add).padLeft(5);
        table.add(quantityRow).padBottom(5).row();
        table.add(errorLabel).width(700).row();
        table.add(Final);
        table.add(sell).right().row();
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
                    changeTab(buy);
                    break;
                case 2:
                    changeTab(sell);
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
        buy.clear(); sell.clear();
        buy.add(createBuyContent()).expand().fill();
        sell.add(createSellContent()).expand().fill();
    }
    private void changeTab(Table content) {
        buy.setVisible(false);
        sell.setVisible(false);
        content.setVisible(true);
    }
    public boolean isAvailable(FoodType foodType) {
        Food food = new Food(foodType);
        Player player = game.getCurrentPlayer();
        for (Food item : player.getAvailableFoods()) {
            if (item.getFoodType()==(foodType)) {
                if(item.isAvailable()) {
                    return false;
                }
                if (item.getFoodType().price<player.getGold()){
                    item.setAvailable(true);
                    player.setGold(player.getGold()- foodType.price);
                    return true;
                }
            }
        }
        return false;
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
}
