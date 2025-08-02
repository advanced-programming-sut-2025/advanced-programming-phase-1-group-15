package com.example.client.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.example.common.tools.Tool;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.relation.PlayerFriendship;
import com.example.common.tools.BackPackable;

import java.util.HashMap;
import java.util.Map;

public class FriendsMenu extends PopUpMenu {
    private static final float MIN_WIDTH = 400f;
    private static final float MIN_HEIGHT = 300f;
    private static final float MAX_WIDTH_RATIO = 0.7f;
    private static final float MAX_HEIGHT_RATIO = 0.8f;
    private static final float ITEM_HEIGHT = 40f;
    private static final float PADDING = 20f;

    Player currentPlayer;

    private boolean inGiftMode = false;
    private Player giftReceiver;
    private PlayerFriendship giftFriendship;

    private ScrollPane currentScrollPane;
    private TextButton currentBackButton;

    public FriendsMenu(Skin skin, String title, Runnable onHideCallback) {
        super(skin, title, calculateWidth(), calculateHeight(), onHideCallback);
        currentPlayer = ClientApp.currentGame.getCurrentPlayer();
    }

    private static float calculateWidth() {
        float screenWidth = Gdx.graphics.getWidth();
        float calculatedWidth = Math.max(MIN_WIDTH, screenWidth * 0.4f);
        return Math.min(calculatedWidth, screenWidth * MAX_WIDTH_RATIO);
    }

    private static float calculateHeight() {
        float screenHeight = Gdx.graphics.getHeight();
        int friendshipCount = ClientApp.currentGame.getFriendships().size();
        float contentHeight = friendshipCount * ITEM_HEIGHT + PADDING * 2 + 100f;
        float calculatedHeight = Math.max(MIN_HEIGHT, contentHeight);
        return Math.min(calculatedHeight, screenHeight * MAX_HEIGHT_RATIO);
    }

    @Override
    protected void populate(Window w) {
        if (currentScrollPane != null) {
            currentScrollPane.remove();
            currentScrollPane = null;
        }
        if (currentBackButton != null) {
            currentBackButton.remove();
            currentBackButton = null;
        }

        if (inGiftMode) {
            populateGiftContent(w);
        } else {
            populateFriendsContent(w);
        }
    }

    private void populateFriendsContent(Window w) {
        Table contentTable = new Table();
        contentTable.pad(PADDING);
        contentTable.top();

        Label headerLabel = new Label("Friendships", skin);
        headerLabel.setFontScale(1.2f);
        contentTable.add(headerLabel).colspan(3).padBottom(15).row();

        Table headerRow = new Table();
        headerRow.add(new Label("Name", skin)).width(150f).left().padRight(10);
        headerRow.add(new Label("Level", skin)).width(100f).center().padRight(10);
        headerRow.add(new Label("Actions", skin)).width(100f).center();

        contentTable.add(headerRow).fillX().padBottom(10).row();
        contentTable.add(new Label("", skin)).colspan(3).height(2).row();

        int friendCount = 0;
        for (PlayerFriendship friendship : ClientApp.currentGame.getFriendships()) {
            Player otherPlayer = getOtherPlayer(friendship);
            if (otherPlayer == null) continue;
            Table friendRow = createFriendRow(friendship, otherPlayer);
            contentTable.add(friendRow).fillX().height(ITEM_HEIGHT).padBottom(5).row();
            friendCount++;
        }

        if (friendCount == 0) {
            contentTable.add(new Label("No friends yet!", skin)).colspan(3).center().row();
        }

        currentScrollPane = new ScrollPane(contentTable, skin);
        currentScrollPane.setFadeScrollBars(false);
        currentScrollPane.setScrollingDisabled(true, false);

        w.add(currentScrollPane).expand().fill().row();
    }

    private void populateGiftContent(Window w) {
        Table contentTable = new Table();
        contentTable.pad(PADDING);
        contentTable.top();

        if (giftReceiver != null) {
            Label instructionLabel = new Label("Select an item to gift to " + giftReceiver.getNickname() + ":", skin);
            instructionLabel.setFontScale(1.1f);
            contentTable.add(instructionLabel).colspan(4).padBottom(10).row();
        }

        HashMap<BackPackable, Integer> inventory = currentPlayer.getInventory().getItems();

        if (inventory.isEmpty()) {
            contentTable.add(new Label("Your inventory is empty!", skin))
                .colspan(4).center().padTop(20).row();
        } else {
            int itemsPerRow = 4;
            int itemIndex = 0;
            for (Map.Entry<BackPackable, Integer> entry : inventory.entrySet()) {
                BackPackable item = entry.getKey();
                int quantity = entry.getValue();
                if (item instanceof Tool) continue;
                if (itemIndex % itemsPerRow == 0 && itemIndex > 0) contentTable.row();
                contentTable.add(createInventoryItem(item, quantity))
                    .size(130f, 100f).pad(5);
                itemIndex++;
            }
            if (itemIndex == 0) {
                contentTable.add(new Label("No giftable items in inventory!", skin))
                    .colspan(4).center().padTop(20).row();
            }
        }

        currentScrollPane = new ScrollPane(contentTable, skin);
        currentScrollPane.setFadeScrollBars(false);

        w.add(currentScrollPane).expand().fill().row();

        currentBackButton = new TextButton("Back", skin);
        currentBackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                exitGiftMode();
            }
        });
        w.row();
        w.add(currentBackButton).bottom().padTop(10);
    }

    private void addCloseButton(Window w) {
        TextButton closeButton = new TextButton("X", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });

        w.getTitleTable().add(closeButton).size(30f, 30f).padLeft(10f);
    }

    private Player getOtherPlayer(PlayerFriendship friendship) {
        if(friendship.getPlayer1().equals(currentPlayer)) {
            return friendship.getPlayer2();
        } else if(friendship.getPlayer2().equals(currentPlayer)) {
            return friendship.getPlayer1();
        }
        return null;
    }

    private Table createFriendRow(PlayerFriendship friendship, Player otherPlayer) {
        Table friendRow = new Table();
        friendRow.left();

        Label nameLabel = new Label(otherPlayer.getNickname(), skin);
        nameLabel.setFontScale(1.1f);
        friendRow.add(nameLabel).width(150f).left().padRight(10);

        String levelText = "Level: " + friendship.getLevel();
        if (friendship.isMarry()) {
            levelText += " ♥".repeat(Math.max(1, friendship.getLevel()));
        } else {
            levelText += " ★".repeat(Math.max(1, friendship.getLevel()));
        }

        Label levelLabel = new Label(levelText, skin);
        friendRow.add(levelLabel).width(100f).center().padRight(10);

        TextButton giftButton = new TextButton("Gift", skin);

        if (friendship.giftToday) {
            giftButton.setDisabled(true);
            giftButton.setText("Gifted");
        }

        giftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!friendship.giftToday) {
                    enterGiftMode(otherPlayer, friendship);
                }
            }
        });

        friendRow.add(giftButton).width(100f).height(35f).center();
        return friendRow;
    }

    private void enterGiftMode(Player recipient, PlayerFriendship friendship) {
        this.inGiftMode = true;
        this.giftReceiver = recipient;
        this.giftFriendship = friendship;

        window.getTitleLabel().setText("Select Gift");

        populate(window);
    }

    private void exitGiftMode() {
        this.inGiftMode = false;
        this.giftReceiver = null;
        this.giftFriendship = null;

        window.getTitleLabel().setText("Friendships");

        populate(window);
    }

    private Table createInventoryItem(BackPackable item, Integer quantity) {
        Table itemTable = new Table();
        itemTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("UI/overlay.png"))));

        Label itemLabel = new Label(item.getName(), skin);
        itemLabel.setWrap(true);
        itemTable.add(itemLabel).width(110f).padBottom(5).row();

        Label quantityLabel = new Label("Qty: " + quantity, skin);
        quantityLabel.setFontScale(0.9f);
        itemTable.add(quantityLabel).padBottom(5).row();

        TextButton selectButton = new TextButton("Gift", skin);
        selectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showQuantityDialog(item, quantity);
            }
        });
        itemTable.add(selectButton).size(80f, 25f);

        return itemTable;
    }

    private TextureRegionDrawable createColoredBackground(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    private void showQuantityDialog(BackPackable item, Integer availableQuantity) {
        Window.WindowStyle blueWindowStyle = new Window.WindowStyle(skin.get(Window.WindowStyle.class));
        blueWindowStyle.background = createColoredBackground(new Color(0.2f, 0.4f, 0.8f, 0.9f)); // Blue background
        skin.add("blue", blueWindowStyle);

        Dialog quantityDialog = new Dialog("Select Quantity", skin, "blue") {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    TextField textField = (TextField) getContentTable().findActor("quantityField");
                    try {
                        int quantity = Integer.parseInt(textField.getText());
                        if (quantity > 0 && quantity <= availableQuantity) {
                            if (sendGift(item, quantity)) {
                                showGiftSentDialog(item, quantity);
                            } else {
                                showErrorDialog("Failed to send gift. Please try again.");
                            }
                        } else {
                            showErrorDialog("Invalid quantity! Please enter a number between 1 and " + availableQuantity);
                        }
                    } catch (NumberFormatException e) {
                        showErrorDialog("Please enter a valid number!");
                    }
                }
            }
        };

        quantityDialog.text("How many " + item.getName() + " to gift?");
        quantityDialog.text("(Available: " + availableQuantity + ")");

        TextField quantityField = new TextField("1", skin);
        quantityField.setName("quantityField");
        quantityField.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return Character.isDigit(c);
            }
        });

        quantityDialog.getContentTable().add(quantityField).width(100f).row();

        quantityDialog.button("OK", true);
        quantityDialog.button("Cancel", false);

        quantityDialog.show(stage);
    }

    private void showGiftSentDialog(BackPackable item, int quantity) {
        Window.WindowStyle greenWindowStyle = new Window.WindowStyle(skin.get(Window.WindowStyle.class));
        greenWindowStyle.background = createColoredBackground(new Color(0.2f, 0.8f, 0.2f, 0.9f)); // Green background
        skin.add("green", greenWindowStyle);

        Dialog successDialog = new Dialog("Gift Sent!", skin, "green") {
            @Override
            protected void result(Object object) {
                exitGiftMode();
            }
        };
        successDialog.text("Successfully sent " + quantity + " "
            + item.getName() + " to " + giftReceiver.getNickname() + "!");
        successDialog.button("OK", true);
        successDialog.show(stage);
    }

    private void showErrorDialog(String message) {
        Window.WindowStyle redWindowStyle = new Window.WindowStyle(skin.get(Window.WindowStyle.class));
        redWindowStyle.background = createColoredBackground(new Color(0.8f, 0.2f, 0.2f, 0.9f));
        skin.add("red", redWindowStyle);

        Dialog errorDialog = new Dialog("Error", skin, "red");
        errorDialog.text(message);
        errorDialog.button("OK", true);
        errorDialog.show(stage);
    }

    private boolean sendGift(BackPackable item, int quantity) {
        try {
            if (!currentPlayer.getInventory().removeCountFromBackPack(item, quantity)) {
                return false;
            }

            giftReceiver.addToBackPack(item, quantity);

            giftFriendship.gift(currentPlayer, item);

            giftFriendship.giftToday = true;

            return true;
        } catch (Exception e) {
            System.err.println("Error sending gift: " + e.getMessage());
            return false;
        }
    }
}
