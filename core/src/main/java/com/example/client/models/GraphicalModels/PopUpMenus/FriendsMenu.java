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
import com.example.client.controllers.ClientGameController;
import com.example.common.tools.Tool;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.relation.PlayerFriendship;
import com.example.common.tools.BackPackable;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class FriendsMenu extends PopUpMenu {
    private static final float MIN_WIDTH = 400f;
    private static final float MIN_HEIGHT = 300f;
    private static final float MAX_WIDTH_RATIO = 0.7f;
    private static final float MAX_HEIGHT_RATIO = 0.8f;
    private static final float ITEM_HEIGHT = 40f;
    private static final float PADDING = 20f;
    private static final int ITEMS_PER_ROW = 4;

    private enum MenuState {
        FRIENDS_LIST,
        GIFT_SELECTION,
        GIFT_HISTORY
    }

    private final Player currentPlayer;
    private MenuState currentState = MenuState.FRIENDS_LIST;
    private Player selectedFriend;
    private PlayerFriendship selectedFriendship;

    private ScrollPane currentScrollPane;
    private TextButton currentBackButton;

    public FriendsMenu(Skin skin, String title, Runnable onHideCallback) {
        super(skin, title, calculateWidth(), calculateHeight(), onHideCallback);
        currentPlayer = ClientApp.currentGame.getCurrentPlayer();
        populate(window);
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
                populateFriendsList(w);
                break;
            case GIFT_SELECTION:
                populateGiftSelection(w);
                break;
            case GIFT_HISTORY:
                populateGiftHistory(w);
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

    private void populateFriendsList(Window w) {
        w.getTitleLabel().setText("Friendships");
        Table contentTable = createContentTable();
        addFriendsHeader(contentTable);
        addFriendsTableHeader(contentTable);
        addFriendsListContent(contentTable);
        w.add(createScrollPane(contentTable)).expand().fill().row();
    }

    private void addFriendsHeader(Table contentTable) {
        Label headerLabel = new Label("Friendships", skin);
        headerLabel.setFontScale(1.2f);
        contentTable.add(headerLabel).colspan(4).padBottom(15).row();
    }

    private void addFriendsTableHeader(Table contentTable) {
        Table headerRow = new Table();
        headerRow.add(createTableHeaderLabel("Name")).width(120f).left().padRight(10);
        headerRow.add(createTableHeaderLabel("Level")).width(100f).center().padRight(10);
        headerRow.add(createTableHeaderLabel("Gift")).width(80f).center().padRight(10);
        headerRow.add(createTableHeaderLabel("History")).width(80f).center();
        contentTable.add(headerRow).fillX().padBottom(10).row();
        contentTable.add(createSeparatorLabel()).colspan(4).height(2).row();
    }

    private Label createTableHeaderLabel(String text) {
        return new Label(text, skin);
    }

    private Label createSeparatorLabel() {
        return new Label("", skin);
    }

    private void addFriendsListContent(Table contentTable) {
        ArrayList<PlayerFriendship> friendships = ClientApp.currentGame.getFriendships();
        if (friendships.isEmpty()) {
            contentTable.add(new Label("No friends yet!", skin)).colspan(4).center().row();
            return;
        }

        for (PlayerFriendship friendship : friendships) {
            Player otherPlayer = getOtherPlayer(friendship);
            if (otherPlayer != null) {
                Table friendRow = createFriendRow(friendship, otherPlayer);
                contentTable.add(friendRow).fillX().height(ITEM_HEIGHT).padBottom(5).row();
            }
        }
    }

    private Table createFriendRow(PlayerFriendship friendship, Player otherPlayer) {
        Table friendRow = new Table();
        friendRow.left();
        friendRow.add(new Label(otherPlayer.getNickname(), skin)).width(120f).left().padRight(10);
        friendRow.add(new Label(createLevelText(friendship), skin)).width(100f).center().padRight(10);
        friendRow.add(createGiftButton(friendship, otherPlayer)).width(80f).height(35f).center().padRight(10);
        friendRow.add(createHistoryButton(friendship, otherPlayer)).width(80f).height(35f).center();
        return friendRow;
    }

    private String createLevelText(PlayerFriendship friendship) {
        String symbol = friendship.isMarry() ? "♥" : "★";
        return String.format("Level: %d %s", friendship.getLevel(), symbol);
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

    private void populateGiftSelection(Window w) {
        w.getTitleLabel().setText("Select Gift");
        Table contentTable = createContentTable();
        addGiftHeader(contentTable);
        addInventoryItems(contentTable);
        w.add(createScrollPane(contentTable)).expand().fill().row();
        addBackButton(w, this::returnToFriendsList);
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
        ArrayList<Map.Entry<BackPackable, Integer>> giftableItems = new ArrayList<>();
        for (Map.Entry<BackPackable, Integer> entry : inventory.entrySet()) {
            if (!(entry.getKey() instanceof Tool)) {
                giftableItems.add(entry);
            }
        }

        if (giftableItems.isEmpty()) {
            contentTable.add(new Label("No giftable items in inventory!", skin))
                .colspan(ITEMS_PER_ROW).center().padTop(20).row();
            return;
        }

        int itemIndex = 0;
        for (Map.Entry<BackPackable, Integer> entry : giftableItems) {
            if (itemIndex % ITEMS_PER_ROW == 0) {
                contentTable.row();
            }
            contentTable.add(createInventoryItem(entry.getKey(), entry.getValue())).size(130f, 100f).pad(5);
            itemIndex++;
        }
    }

    private Table createInventoryItem(BackPackable item, Integer quantity) {
        Table itemTable = new Table();
        itemTable.setBackground(createColoredBackground(Color.DARK_GRAY));

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

    private void populateGiftHistory(Window w) {
        w.getTitleLabel().setText("Gift History");
        Table contentTable = createContentTable();
        addHistoryHeader(contentTable);
        addGiftHistoryLists(contentTable);
        w.add(createScrollPane(contentTable)).expand().fill().row();
        addBackButton(w, this::returnToFriendsList);
    }

    private void addHistoryHeader(Table contentTable) {
        if (selectedFriend != null) {
            Label headerLabel = new Label("Gift History with " + selectedFriend.getNickname(), skin);
            headerLabel.setFontScale(1.2f);
            contentTable.add(headerLabel).colspan(3).padBottom(15).row();

            Table headerRow = new Table();
            headerRow.add(createTableHeaderLabel("Gift")).width(150f).left().padRight(10);
            headerRow.add(createTableHeaderLabel("Rating")).width(100f).center().padRight(10);
            headerRow.add(createTableHeaderLabel("Actions")).width(100f).center();
            contentTable.add(headerRow).fillX().padBottom(10).row();
            contentTable.add(createSeparatorLabel()).colspan(3).height(2).row();
        }
    }

    private void addGiftHistoryLists(Table contentTable) {
        if (selectedFriendship == null) {
            contentTable.add(new Label("No friendship data available", skin)).colspan(3).center().row();
            return;
        }

        addReceivedGiftsList(contentTable);
        addSentGiftsList(contentTable);
    }

    private void addReceivedGiftsList(Table contentTable) {
        Label receivedHeader = new Label("Gifts Received:", skin);
        receivedHeader.setFontScale(1.1f);
        contentTable.add(receivedHeader).colspan(3).left().padTop(10).padBottom(5).row();

        ArrayList<PlayerFriendship.Gift> receivedGifts = selectedFriendship.getGifts(selectedFriend);
        if (receivedGifts == null || receivedGifts.isEmpty()) {
            contentTable.add(new Label("No gifts received yet!", skin)).colspan(3).center().padLeft(20).row();
        } else {
            for (PlayerFriendship.Gift gift : receivedGifts) {
                Table giftRow = createReceivedGiftRow(gift);
                contentTable.add(giftRow).fillX().height(ITEM_HEIGHT).padBottom(5).row();
            }
        }
    }

    private Table createReceivedGiftRow(PlayerFriendship.Gift gift) {
        Table giftRow = new Table();
        giftRow.left();
        giftRow.add(new Label(gift.getItem().getName(), skin)).width(150f).left().padRight(10);
        String ratingText = gift.getRate() > 0 ?  " (" + gift.getRate() + "/5)" : "Not rated";
        giftRow.add(new Label(ratingText, skin)).width(100f).center().padRight(10);
        giftRow.setColor(Color.BLUE);
        TextButton rateButton = createRateButton(gift);
        giftRow.add(rateButton).width(100f).height(35f).center();
        return giftRow;
    }

    private TextButton createRateButton(PlayerFriendship.Gift gift) {
        TextButton rateButton = new TextButton(gift.getRate() > 0 ? "Rated" : "Rate", skin);
        if (gift.getRate() > 0) {
            rateButton.setDisabled(true);
        } else {
            rateButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    showRatingDialog(gift);
                }
            });
        }
        return rateButton;
    }

    private void addSentGiftsList(Table contentTable) {
        Label sentHeader = new Label("Gifts Sent:", skin);
        sentHeader.setFontScale(1.1f);
        contentTable.add(sentHeader).colspan(3).left().padTop(15).padBottom(5).row();

        ArrayList<PlayerFriendship.Gift> sentGifts = selectedFriendship.getGifts(currentPlayer);
        if (sentGifts == null || sentGifts.isEmpty()) {
            contentTable.add(new Label("No gifts sent yet!", skin)).colspan(3).center().padLeft(20).row();
        } else {
            for (PlayerFriendship.Gift gift : sentGifts) {
                Table giftRow = createSentGiftRow(gift);
                contentTable.add(giftRow).fillX().height(ITEM_HEIGHT).padBottom(5).row();
            }
        }
    }

    private Table createSentGiftRow(PlayerFriendship.Gift gift) {
        Table giftRow = new Table();
        giftRow.left();
        giftRow.add(new Label(gift.getItem().getName(), skin)).width(150f).left().padRight(10);
        String ratingText = gift.getRate() > 0 ? "★".repeat(gift.getRate()) + " (" + gift.getRate() + "/5)" : "Not rated by them";
        giftRow.add(new Label(ratingText, skin)).width(100f).center().padRight(10);
        Label statusLabel = new Label("Sent", skin);
        statusLabel.setColor(Color.GRAY);
        giftRow.add(statusLabel).width(100f).center();
        return giftRow;
    }

    private Table createContentTable() {
        Table contentTable = new Table();
        contentTable.pad(PADDING);
        contentTable.top();
        return contentTable;
    }

    private ScrollPane createScrollPane(Table contentTable) {
        currentScrollPane = new ScrollPane(contentTable, skin);
        currentScrollPane.setFadeScrollBars(false);
        currentScrollPane.setScrollingDisabled(true, false);
        return currentScrollPane;
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

    private void enterGiftMode(Player recipient, PlayerFriendship friendship) {
        this.currentState = MenuState.GIFT_SELECTION;
        this.selectedFriend = recipient;
        this.selectedFriendship = friendship;
        populate(window);
    }

    private void enterHistoryMode(Player friend, PlayerFriendship friendship) {
        this.currentState = MenuState.GIFT_HISTORY;
        this.selectedFriend = friend;
        this.selectedFriendship = friendship;
        populate(window);
    }

    private void returnToFriendsList() {
        this.currentState = MenuState.FRIENDS_LIST;
        this.selectedFriend = null;
        this.selectedFriendship = null;
        populate(window);
    }

    private void showQuantityDialog(BackPackable item, Integer availableQuantity) {
        final TextField quantityField = new TextField("1", skin);
        quantityField.setName("quantityField");
        quantityField.setTextFieldFilter((textField, c) -> Character.isDigit(c));

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
                ClientGameController.giveGift(selectedFriend.getUsername(),item.getName(), quantity);
                ClientGameController.sendGiftMessage(item, quantity, selectedFriend.getUsername());
                showGiftSentDialog(item, quantity);
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
        Dialog ratingDialog = createRatingDialog(gift, yellowStyleName, ratingGroup);
        ratingDialog.show(stage);
    }

    private Dialog createRatingDialog(PlayerFriendship.Gift gift, String styleName, ButtonGroup<TextButton> ratingGroup) {
        Dialog ratingDialog = new Dialog("Rate Gift", skin, styleName) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    TextButton checkedButton = ratingGroup.getChecked();
                    if (checkedButton != null) {
                        int selectedRating = ratingGroup.getButtons().indexOf(checkedButton, true) + 1;
                        ClientGameController.sendRateGiftMessage(selectedFriend.getUsername(), getGiftIndex(gift), selectedRating);
                        showRatingConfirmDialog(gift.getItem().getName(), selectedRating);
                    }
                }
            }
        };

        ratingDialog.text("Rate the gift: " + gift.getItem().getName());
        Table ratingTable = new Table();
        ratingGroup.setMaxCheckCount(1);
        ratingGroup.setMinCheckCount(0);
        for (int i = 1; i <= 5; i++) {
            TextButton ratingButton = new TextButton("★" + i, skin);
            ratingGroup.add(ratingButton);
            ratingTable.add(ratingButton).size(60f, 40f).pad(2);
        }
        ratingDialog.getContentTable().add(ratingTable).row();
        ratingDialog.button("Confirm", true).button("Cancel", false);
        return ratingDialog;
    }

    private int getGiftIndex(PlayerFriendship.Gift gift) {
        ArrayList<PlayerFriendship.Gift> gifts = selectedFriendship.getGifts(selectedFriend);
        return (gifts != null) ? gifts.indexOf(gift) : -1;
    }

    private void showGiftSentDialog(BackPackable item, int quantity) {
        new Dialog("Gift Sent!", skin, getDialogStyleName(Color.GREEN)) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    returnToFriendsList();
                }
            }
        }.text("Successfully sent " + quantity + " " + item.getName() + " to " + selectedFriend.getNickname() + "!").button("OK", true).show(stage);
    }

    private void showRatingConfirmDialog(String itemName, int rating) {
        new Dialog("Rating Updated!", skin, getDialogStyleName(Color.GREEN))
            .text("You rated " + itemName + " with " + rating + " stars!").button("OK", true).show(stage);
    }

    private void showErrorDialog(String message) {
        new Dialog("Error", skin, getDialogStyleName(Color.RED)).text(message).button("OK", true).show(stage);
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
}
