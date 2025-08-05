package com.example.client.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage; // Assuming Stage is available from PopUpMenu
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
import java.util.ArrayList;

public class FriendsMenu extends PopUpMenu {
    // Constants
    private static final float MIN_WIDTH = 400f;
    private static final float MIN_HEIGHT = 300f;
    private static final float MAX_WIDTH_RATIO = 0.7f;
    private static final float MAX_HEIGHT_RATIO = 0.8f;
    private static final float ITEM_HEIGHT = 40f;
    private static final float PADDING = 20f;
    private static final int ITEMS_PER_ROW = 4;

    // Menu states
    private enum MenuState {
        FRIENDS_LIST,
        GIFT_SELECTION,
        GIFT_HISTORY
    }

    // State variables
    private final Player currentPlayer;
    private MenuState currentState = MenuState.FRIENDS_LIST;
    private Player selectedFriend;
    private PlayerFriendship selectedFriendship;

    // UI Components
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
        clearCurrentComponents();

        switch (currentState) {
            case FRIENDS_LIST:
                populateFriendsContent(w);
                break;
            case GIFT_SELECTION:
                populateGiftContent(w);
                break;
            case GIFT_HISTORY:
                populateGiftHistoryContent(w);
                break;
        }
    }

    private void clearCurrentComponents() {
        if (currentScrollPane != null) {
            currentScrollPane.remove();
            currentScrollPane = null;
        }
        if (currentBackButton != null) {
            currentBackButton.remove();
            currentBackButton = null;
        }
    }

    // FRIENDS LIST VIEW
    private void populateFriendsContent(Window w) {
        Table contentTable = createContentTable();
        addFriendsHeader(contentTable);
        addFriendsTableHeader(contentTable);
        addFriendsList(contentTable);

        currentScrollPane = createScrollPane(contentTable);
        w.add(currentScrollPane).expand().fill().row();
    }

    private void addFriendsHeader(Table contentTable) {
        Label headerLabel = new Label("Friendships", skin);
        headerLabel.setFontScale(1.2f);
        contentTable.add(headerLabel).colspan(4).padBottom(15).row();
    }

    private void addFriendsTableHeader(Table contentTable) {
        Table headerRow = new Table();
        headerRow.add(new Label("Name", skin)).width(120f).left().padRight(10);
        headerRow.add(new Label("Level", skin)).width(100f).center().padRight(10);
        headerRow.add(new Label("Gift", skin)).width(80f).center().padRight(10);
        headerRow.add(new Label("History", skin)).width(80f).center();

        contentTable.add(headerRow).fillX().padBottom(10).row();
        contentTable.add(new Label("", skin)).colspan(4).height(2).row();
    }

    private void addFriendsList(Table contentTable) {
        int friendCount = 0;
        for (PlayerFriendship friendship : ClientApp.currentGame.getFriendships()) {
            Player otherPlayer = getOtherPlayer(friendship);
            if (otherPlayer == null) continue;

            Table friendRow = createFriendRow(friendship, otherPlayer);
            contentTable.add(friendRow).fillX().height(ITEM_HEIGHT).padBottom(5).row();
            friendCount++;
        }

        if (friendCount == 0) {
            contentTable.add(new Label("No friends yet!", skin)).colspan(4).center().row();
        }
    }

    private Table createFriendRow(PlayerFriendship friendship, Player otherPlayer) {
        Table friendRow = new Table();
        friendRow.left();

        Label nameLabel = new Label(otherPlayer.getNickname(), skin);
        nameLabel.setFontScale(1.1f);
        friendRow.add(nameLabel).width(120f).left().padRight(10);

        String levelText = createLevelText(friendship);
        Label levelLabel = new Label(levelText, skin);
        friendRow.add(levelLabel).width(100f).center().padRight(10);

        TextButton giftButton = createGiftButton(friendship, otherPlayer);
        friendRow.add(giftButton).width(80f).height(35f).center().padRight(10);

        TextButton historyButton = createHistoryButton(friendship, otherPlayer);
        friendRow.add(historyButton).width(80f).height(35f).center();

        return friendRow;
    }

    private String createLevelText(PlayerFriendship friendship) {
        String levelText = "Level: " + friendship.getLevel();
        if (friendship.isMarry()) {
            levelText += " ♥".repeat(Math.max(1, friendship.getLevel()));
        } else {
            levelText += " ★".repeat(Math.max(1, friendship.getLevel()));
        }
        return levelText;
    }

    private TextButton createGiftButton(PlayerFriendship friendship, Player otherPlayer) {
        TextButton giftButton = new TextButton("Gift", skin);

        if (friendship.giftToday()) {
            giftButton.setDisabled(true);
            giftButton.setText("Gifted");
        }

        giftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!friendship.giftToday()) {
                    enterGiftMode(otherPlayer, friendship);
                }
            }
        });

        return giftButton;
    }

    private TextButton createHistoryButton(PlayerFriendship friendship, Player otherPlayer) {
        TextButton historyButton = new TextButton("History", skin);
        historyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                enterHistoryMode(otherPlayer, friendship);
            }
        });
        return historyButton;
    }

    // GIFT SELECTION VIEW
    private void populateGiftContent(Window w) {
        Table contentTable = createContentTable();
        addGiftHeader(contentTable);
        addInventoryItems(contentTable);

        currentScrollPane = createScrollPane(contentTable);
        w.add(currentScrollPane).expand().fill().row();
        addBackButton(w, this::exitGiftMode);
    }

    private void addGiftHeader(Table contentTable) {
        if (selectedFriend != null) {
            Label instructionLabel = new Label("Select an item to gift to " + selectedFriend.getNickname() + ":", skin);
            instructionLabel.setFontScale(1.1f);
            contentTable.add(instructionLabel).colspan(ITEMS_PER_ROW).padBottom(10).row();
        }
    }

    private void addInventoryItems(Table contentTable) {
        HashMap<BackPackable, Integer> inventory = currentPlayer.getInventory().getItems();

        if (inventory.isEmpty()) {
            contentTable.add(new Label("Your inventory is empty!", skin))
                .colspan(ITEMS_PER_ROW).center().padTop(20).row();
            return;
        }

        int itemIndex = 0;
        for (Map.Entry<BackPackable, Integer> entry : inventory.entrySet()) {
            BackPackable item = entry.getKey();
            int quantity = entry.getValue();

            if (item instanceof Tool) continue;

            if (itemIndex % ITEMS_PER_ROW == 0 && itemIndex > 0) {
                contentTable.row();
            }

            contentTable.add(createInventoryItem(item, quantity))
                .size(130f, 100f).pad(5);
            itemIndex++;
        }

        if (itemIndex == 0) {
            contentTable.add(new Label("No giftable items in inventory!", skin))
                .colspan(ITEMS_PER_ROW).center().padTop(20).row();
        }
    }

    // GIFT HISTORY VIEW
    private void populateGiftHistoryContent(Window w) {
        Table contentTable = createContentTable();
        addHistoryHeader(contentTable);
        addGiftHistoryList(contentTable);

        currentScrollPane = createScrollPane(contentTable);
        w.add(currentScrollPane).expand().fill().row();
        addBackButton(w, this::exitHistoryMode);
    }

    private void addHistoryHeader(Table contentTable) {
        if (selectedFriend != null) {
            Label headerLabel = new Label("Gift History with " + selectedFriend.getNickname(), skin);
            headerLabel.setFontScale(1.2f);
            contentTable.add(headerLabel).colspan(3).padBottom(15).row();

            Table headerRow = new Table();
            headerRow.add(new Label("Gift", skin)).width(150f).left().padRight(10);
            headerRow.add(new Label("Rating", skin)).width(100f).center().padRight(10);
            headerRow.add(new Label("Actions", skin)).width(100f).center();

            contentTable.add(headerRow).fillX().padBottom(10).row();
            contentTable.add(new Label("", skin)).colspan(3).height(2).row();
        }
    }

    private void addGiftHistoryList(Table contentTable) {
        if (selectedFriendship == null) {
            contentTable.add(new Label("No friendship data available", skin)).colspan(3).center().row();
            return;
        }

        ArrayList<PlayerFriendship.Gift> receivedGifts = selectedFriendship.getGifts(currentPlayer);

        if (receivedGifts == null || receivedGifts.isEmpty()) {
            contentTable.add(new Label("No gifts sent yet!", skin)).colspan(3).center().row();
            return;
        }

        for (PlayerFriendship.Gift gift : receivedGifts) {
            Table giftRow = createGiftHistoryRow(gift);
            contentTable.add(giftRow).fillX().height(ITEM_HEIGHT).padBottom(5).row();
        }
    }

    private Table createGiftHistoryRow(PlayerFriendship.Gift gift) {
        Table giftRow = new Table();
        giftRow.left();

        Label giftLabel = new Label(gift.getItem().getName(), skin);
        giftLabel.setFontScale(1.0f);
        giftRow.add(giftLabel).width(150f).left().padRight(10);

        String ratingText = gift.getRate() > 0 ? "★".repeat(gift.getRate()) + " (" + gift.getRate() + "/5)" : "Not rated";
        Label ratingLabel = new Label(ratingText, skin);
        giftRow.add(ratingLabel).width(100f).center().padRight(10);

        TextButton rateButton = new TextButton(gift.getRate() > 0 ? "Re-rate" : "Rate", skin);
        rateButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showRatingDialog(gift);
            }
        });
        giftRow.add(rateButton).width(100f).height(35f).center();

        return giftRow;
    }

    // UTILITY METHODS
    private Table createContentTable() {
        Table contentTable = new Table();
        contentTable.pad(PADDING);
        contentTable.top();
        return contentTable;
    }

    private ScrollPane createScrollPane(Table contentTable) {
        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        return scrollPane;
    }

    private void addBackButton(Window w, Runnable onClickAction) {
        currentBackButton = new TextButton("Back", skin);
        currentBackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                onClickAction.run();
            }
        });
        w.row();
        w.add(currentBackButton).bottom().padTop(10);
    }

    private Player getOtherPlayer(PlayerFriendship friendship) {
        if (friendship.getPlayer1().equals(currentPlayer)) {
            return friendship.getPlayer2();
        } else if (friendship.getPlayer2().equals(currentPlayer)) {
            return friendship.getPlayer1();
        }
        return null;
    }

    // STATE MANAGEMENT
    private void enterGiftMode(Player recipient, PlayerFriendship friendship) {
        this.currentState = MenuState.GIFT_SELECTION;
        this.selectedFriend = recipient;
        this.selectedFriendship = friendship;
        window.getTitleLabel().setText("Select Gift");
        populate(window);
    }

    private void enterHistoryMode(Player friend, PlayerFriendship friendship) {
        this.currentState = MenuState.GIFT_HISTORY;
        this.selectedFriend = friend;
        this.selectedFriendship = friendship;
        window.getTitleLabel().setText("Gift History");
        populate(window);
    }

    private void exitGiftMode() {
        returnToFriendsList();
    }

    private void exitHistoryMode() {
        returnToFriendsList();
    }

    private void returnToFriendsList() {
        this.currentState = MenuState.FRIENDS_LIST;
        this.selectedFriend = null;
        this.selectedFriendship = null;
        window.getTitleLabel().setText("Friendships");
        populate(window);
    }

    // INVENTORY ITEM CREATION
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

    // DIALOG METHODS
    private void showQuantityDialog(BackPackable item, Integer availableQuantity) {
        final TextField quantityField = new TextField("1", skin);
        quantityField.setName("quantityField");
        quantityField.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return Character.isDigit(c);
            }
        });

        Dialog quantityDialog = new Dialog("Select Quantity", skin, getDialogStyleName(Color.BLUE)) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    handleQuantitySelection(quantityField, item, availableQuantity);
                }
            }
        };

        quantityDialog.getContentTable().add(quantityField).width(100f).row();
        quantityDialog.button("OK", true);
        quantityDialog.button("Cancel", false);

        quantityDialog.show(stage);
    }

    private void handleQuantitySelection(TextField textField, BackPackable item, Integer availableQuantity) {
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

    private void showRatingDialog(PlayerFriendship.Gift gift) {
        String yellowStyleName = getDialogStyleName(new Color(0.8f, 0.8f, 0.2f, 0.9f));

        ButtonGroup<TextButton> ratingGroup = new ButtonGroup<>();

        Dialog ratingDialog = getRatingDialog(gift, yellowStyleName, ratingGroup);

        Table ratingTable = new Table();
        ratingGroup.setMaxCheckCount(1);
        ratingGroup.setMinCheckCount(1);

        for (int i = 1; i <= 5; i++) {
            final int rating = i;
            TextButton ratingButton = new TextButton("★" + i, skin);
            ratingButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    gift.setRate(rating);
                }
            });
            ratingGroup.add(ratingButton);
            ratingTable.add(ratingButton).size(60f, 40f).pad(2);
        }

        if (gift.getRate() > 0 && gift.getRate() <= 5) {
            ratingGroup.getButtons().get(gift.getRate() - 1).setChecked(true);
        }

        ratingDialog.getContentTable().add(ratingTable).row();
        ratingDialog.button("Confirm", true);
        ratingDialog.button("Cancel", false);

        ratingDialog.show(stage);
    }

    private Dialog getRatingDialog(PlayerFriendship.Gift gift, String yellowStyleName, ButtonGroup<TextButton> ratingGroup) {
        Dialog ratingDialog = new Dialog("Rate Gift", skin, yellowStyleName) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    TextButton checkedButton = ratingGroup.getChecked();
                    if (checkedButton != null) {
                        int selectedRating = ratingGroup.getButtons().indexOf(checkedButton, true) + 1;
                        updateGiftRating(gift, selectedRating);
                    }
                }
            }
        };

        ratingDialog.text("Rate the gift: " + gift.getItem().getName());
        ratingDialog.text("Current rating: " + (gift.getRate() > 0 ? gift.getRate() + "/5" : "Not rated"));
        return ratingDialog;
    }

    private void updateGiftRating(PlayerFriendship.Gift gift, int rating) {
        int oldRating = gift.getRate();
        gift.setRate(rating);

        if (oldRating > 0) {
            selectedFriendship.rateGift(3);
        }
        selectedFriendship.rateGift(rating);

        showRatingConfirmDialog(gift.getItem().getName(), rating);

        populate(window);
    }

    private void showGiftSentDialog(BackPackable item, int quantity) {
        Dialog successDialog = new Dialog("Gift Sent!", skin, getDialogStyleName(Color.GREEN)) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    exitGiftMode();
                }
            }
        };
        successDialog.text("Successfully sent " + quantity + " "
            + item.getName() + " to " + selectedFriend.getNickname() + "!");
        successDialog.button("OK", true);
        successDialog.show(stage);
    }

    private void showRatingConfirmDialog(String itemName, int rating) {
        Dialog confirmDialog = new Dialog("Rating Updated!", skin, getDialogStyleName(Color.GREEN));
        confirmDialog.text("You rated " + itemName + " with " + rating + " stars!");
        confirmDialog.button("OK", true);
        confirmDialog.show(stage);
    }

    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog("Error", skin, getDialogStyleName(Color.RED));
        errorDialog.text(message);
        errorDialog.button("OK", true);
        errorDialog.show(stage);
    }

    private String getDialogStyleName(Color color) {
        String styleName = "dialog-style-" + color.toString();
        if (!skin.has(styleName, Window.WindowStyle.class)) {
            Window.WindowStyle windowStyle = new Window.WindowStyle(skin.get(Window.WindowStyle.class));
            windowStyle.background = createColoredBackground(color);
            skin.add(styleName, windowStyle);
        }
        return styleName;
    }

    private TextureRegionDrawable createColoredBackground(Color color) {
        Color dialogColor = new Color(color.r, color.g, color.b, 0.9f);
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(dialogColor);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    private boolean sendGift(BackPackable item, int quantity) {
        try {
            if (!currentPlayer.getInventory().removeCountFromBackPack(item, quantity)) {
                return false;
            }

            selectedFriend.addToBackPack(item, quantity);
            selectedFriendship.gift(currentPlayer, item);
            selectedFriendship.giftToday = true;

            return true;
        } catch (Exception e) {
            System.err.println("Error sending gift: " + e.getMessage());
            return false;
        }
    }
}
