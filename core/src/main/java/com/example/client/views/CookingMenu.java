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
import com.example.common.animals.AnimalProduct;
import com.example.common.animals.AnimalProductType;
import com.example.common.cooking.Food;
import com.example.common.cooking.FoodType;
import com.example.common.farming.Crop;
import com.example.common.farming.Crops;
import com.example.common.foraging.ForagingCrop;
import com.example.common.foraging.ForagingCropsType;
import com.example.common.foraging.ForagingMineral;
import com.example.common.tools.BackPackable;
import com.example.common.tools.Fridge;

public class CookingMenu {
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("UI/StardewValley.json"));
    private boolean visible ;
    private final Table rootTable;
    private final Table recipe;
    private final Table fridge;
    private final Main main;
    private final Game game;
    private final Runnable onHideCallback;
    private final Label tooltipLabel = new Label("", skin);
    private final Container<Label> tooltipContainer = new Container<>(tooltipLabel);
    public CookingMenu(Main main, Game game, Runnable onHideCallback) {
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
        TextButton recipeTab = new TextButton("Recipe", skin);
        TextButton fridgeTab = new TextButton("Fridge", skin);
        tabBar.add(recipeTab).pad(5);
        tabBar.add(fridgeTab).pad(5);
        rootTable.add(tabBar).expandX().top().padTop(10).row();
        recipe = createCookingContent();
        fridge = createFridgeContent();
        Stack stack = new Stack();
        stack.add(recipe);
        stack.add(fridge);
        changeTab(recipe);
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
                changeTab(recipe);
            }
        });
        fridgeTab.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                refresh();
                changeTab(fridge);
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
    private Table createCookingContent() {
        Label titleLabel = new Label("Recipe: ", skin); titleLabel.setColor(Color.FIREBRICK);
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
                    descriptionLabel.setText("Desc: " + food.getRecipe());
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
    private Table createFridgeContent() {
        Fridge fridge = game.getCurrentPlayer().getFridge();
        if (fridge.getItems().isEmpty()) {
            Label label = new Label("No item available in fridge", skin);
            label.setColor(Color.RED);
            Table tab = new Table(skin);
            tab.add(label);
            return tab;
        }
        List<String> itemList = new List<>(skin);
        String[] items = fridge.getItems().entrySet().stream()
                .map(entry -> entry.getKey().getName() + "  x" + entry.getValue())
                .toArray(String[]::new);
        itemList.setItems(items);
        itemList.setItems(items);
        ScrollPane scrollPane = new ScrollPane(itemList, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        Label titleLabel = new Label("Fridge Items:", skin); titleLabel.setColor(Color.FIREBRICK);
        itemList.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                int index = itemList.getSelectedIndex();
                if (index >= 0) {
                    String item = itemList.getItems().get(index);
                    if (item != null && !item.isEmpty()) {
                        String itemName = item.split(" x")[0];
                        BackPackable hoveredItem = fridge.getItemByName(itemName);
                        if (hoveredItem != null) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        Table table = new Table(skin);
        Table bottomRow = new Table();
        table.add(titleLabel).padBottom(10).row();
        table.add(scrollPane).expand().fill().pad(10).row();
        table.add(bottomRow).bottom();
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
                    changeTab(recipe);
                    break;
                case 2:
                    changeTab(fridge);
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
        recipe.clear(); fridge.clear();
        recipe.add(createCookingContent()).expand().fill();
        fridge.add(createFridgeContent()).expand().fill();
    }
    private void changeTab(Table content) {
        recipe.setVisible(false);
        fridge.setVisible(false);
        content.setVisible(true);
    }
    public boolean isAvailable(Food food) {
        if (game.getCurrentPlayer().getInventory().checkFilled()) {
            return false;
        }
        for (BackPackable item : food.getFoodType().getIngredients().keySet()) {
            boolean found = false;
            int temp = 0;
            int num  = food.getFoodType().getIngredients().get(item);
            if (game.getCurrentPlayer().getInventory().getItemByName(item.getName()) != null) {
                int total = game.getCurrentPlayer().getInventory().getItemCount(item.getName());
                if(total >= num){
                  found = true;
                }
                temp = total;
            }
            if (!found) {
                if (game.getCurrentPlayer().getFridge().getItemByName(item.getName()) != null) {
                    found = true;
                    int total = game.getCurrentPlayer().getFridge().getItemCount(item.getName());
                    if ((total+temp) < num) {
                        return false;
                    }
                }
            }
            if (!found) {
                return false;
            }
        }
        for (BackPackable item : food.getFoodType().getIngredients().keySet()) {
            boolean found = false;
            int num  = food.getFoodType().getIngredients().get(item);
            int temp = 0;
            if (game.getCurrentPlayer().getInventory().getItemByName(item.getName()) != null) {
                found = true;
                temp = game.getCurrentPlayer().getInventory().getItemCount(item.getName());
                if (temp< num) {
                    found = false;
                    game.getCurrentPlayer().getInventory().removeCountFromBackPack(game.getCurrentPlayer().getInventory().getItemByName(item.getName()) , temp);
                }
            }
            if (!found) {
                game.getCurrentPlayer().getFridge().removeCountFromFridge(game.getCurrentPlayer().getFridge().getItemByName(item.getName()) , num-temp);
            }
        }
        game.getCurrentPlayer().getInventory().addToBackPack(food,1);
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
}
