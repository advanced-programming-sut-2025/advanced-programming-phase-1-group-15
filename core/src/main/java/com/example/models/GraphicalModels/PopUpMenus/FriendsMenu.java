package com.example.models.GraphicalModels.PopUpMenus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.models.App;
import com.example.models.Player;
import com.example.models.relation.PlayerFriendship;
import com.example.models.tools.BackPackable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendsMenu extends PopUpMenu {
    private static final float MIN_WIDTH = 400f;
    private static final float MIN_HEIGHT = 300f;
    private static final float MAX_WIDTH_RATIO = 0.7f; // 70% of screen width
    private static final float MAX_HEIGHT_RATIO = 0.8f; // 80% of screen height
    private static final float ITEM_HEIGHT = 40f;
    private static final float PADDING = 20f;

    Player currentPlayer;
    //private InventoryMenu inventoryMenu;

    public FriendsMenu(Skin skin, String title, Runnable onHideCallback) {
        super(skin, title, calculateWidth(), calculateHeight(), onHideCallback);
        currentPlayer = App.currentGame.getCurrentPlayer();
    }

    private static float calculateWidth() {
        float screenWidth = Gdx.graphics.getWidth();
        float calculatedWidth = Math.max(MIN_WIDTH, screenWidth * 0.4f);
        return Math.min(calculatedWidth, screenWidth * MAX_WIDTH_RATIO);
    }

    private static float calculateHeight() {
        float screenHeight = Gdx.graphics.getHeight();

        int friendshipCount = App.currentGame.getFriendships().size();
        float contentHeight = friendshipCount * ITEM_HEIGHT + PADDING * 2;

        contentHeight += 100f;

        float calculatedHeight = Math.max(MIN_HEIGHT, contentHeight);
        return Math.min(calculatedHeight, screenHeight * MAX_HEIGHT_RATIO);
    }

    @Override
    protected void populate(Window w) {
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

        contentTable.add(new Label("", skin)).colspan(2).height(2).row();;

        int friendCount = 0;
        for (PlayerFriendship friendship : App.currentGame.getFriendships()) {
            Player otherPlayer = getOtherPlayer(friendship);
            if (otherPlayer == null) continue;

            Table friendRow = createFriendRow(friendship, otherPlayer);
            contentTable.add(friendRow).fillX().height(ITEM_HEIGHT).padBottom(5).row();
            friendCount++;
        }

        if (friendCount == 0) {
            contentTable.add(new Label("No friends yet!", skin)).colspan(2).center().row();
        }

        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        w.add(scrollPane).expand().fill().row();

//        TextButton closeButton = new TextButton("Close", skin);
//        closeButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
//                hide();
//            }
//        });
//
//        w.add(closeButton).bottom().padTop(10).row();
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
            levelText += " ♥".repeat(friendship.getLevel()); // Married
        }else {
            levelText += " ★".repeat(friendship.getLevel()); // Stars for friendship levels
        }

        Label levelLabel = new Label(levelText, skin);
        friendRow.add(levelLabel).width(100f).center().padRight(10);

        // Gift button
        TextButton giftButton = new TextButton("Gift", skin);
        giftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //openInventoryForGift(otherPlayer, friendship);
                // TODO : should be implemented
            }
        });

        friendRow.add(giftButton).width(100f).height(35f).center();

        return friendRow;
    }

    //private void openInventoryForGift(Player ) {}


    private class GiftFriendMenu extends PopUpMenu {
        private boolean giftMode = false;
        private Player reciever;
        private PlayerFriendship targetFriendship;
        private Runnable onGiftSent;

        public GiftFriendMenu(Skin skin, String title, Runnable onHideCallback) {
            super(skin, title, 600f, 500f, onHideCallback);
        }

        public void setGiftMode(boolean giftMode, Player recipient, PlayerFriendship friendship, Runnable onGiftSent) {
            this.giftMode = giftMode;
            this.reciever = recipient;
            this.targetFriendship = friendship;
            this.onGiftSent = onGiftSent;
        }

        @Override
        protected void populate(Window w) {
            Table contentTable = new Table();
            contentTable.pad(PADDING);
            contentTable.top();

            if (giftMode) {
                Label instructionLabel = new Label("Select an item to gift:", skin);
                instructionLabel.setFontScale(1.1f);
                contentTable.add(instructionLabel).colspan(3).padBottom(10).row();
            }

            HashMap<BackPackable, Integer> inventory = currentPlayer.getInventory().getItems();

            if (inventory.isEmpty()) {
                Label emptyLabel = new Label("Your inventory is empty!", skin);
                contentTable.add(emptyLabel).colspan(3).center().padTop(20).row();
            } else {
                int itemsPerRow = 4;
                int currentRow = 0;
                int itemIndex = 0;

                for (Map.Entry<BackPackable, Integer> entry : inventory.entrySet()) {
                    BackPackable item = entry.getKey();
                    Integer quantity = entry.getValue();

                    if (itemIndex % itemsPerRow == 0 && itemIndex > 0) {
                        contentTable.row();
                        currentRow++;
                    }

                    Table itemCell = createInventoryItem(item, quantity);
                    contentTable.add(itemCell).size(120f, 80f).pad(5);
                    itemIndex++;
                }
            }

            ScrollPane scrollPane = new ScrollPane(contentTable, skin);
            scrollPane.setFadeScrollBars(false);

            w.add(scrollPane).expand().fill().row();

            // Close button
            TextButton closeButton = new TextButton("Cancel", skin);
            closeButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    hide();
                }
            });

            w.add(closeButton).bottom().padTop(10).row();
        }

        private Table createInventoryItem(BackPackable item, Integer quantity) {
            Table itemTable = new Table();
            itemTable.setBackground(skin.getDrawable("default-round"));

            Label itemLabel = new Label(item.getName(), skin);
            itemLabel.setWrap(true);
            itemTable.add(itemLabel).width(100f).padBottom(5).row();

            Label quantityLabel = new Label("Qty: " + quantity, skin);
            itemTable.add(quantityLabel).padBottom(5).row();

            if (giftMode) {
                TextButton selectButton = new TextButton("Gift", skin);
                selectButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        showQuantityDialog(item, quantity);
                    }
                });
                itemTable.add(selectButton).size(80f, 25f);
            }

            return itemTable;
        }

        private void showQuantityDialog(BackPackable item, Integer availableQuantity) {
            // Create a simple dialog for quantity selection
            Dialog quantityDialog = new Dialog("Select Quantity", skin) {
                @Override
                protected void result(Object object) {
                    if ((Boolean) object) {
                        TextField textField = (TextField) getContentTable().findActor("quantityField");
                        try {
                            int quantity = Integer.parseInt(textField.getText());
                            if (quantity > 0 && quantity <= availableQuantity) {
                                sendGift(item, quantity);
                            }
                        } catch (NumberFormatException e) {
                            // Invalid quantity, do nothing
                        }
                    }
                }
            };

            quantityDialog.text("How many " + item.getName() + " to gift?");
            quantityDialog.text("(Available: " + availableQuantity + ")");

            TextField quantityField = new TextField("1", skin);
            quantityField.setName("quantityField");
            quantityDialog.getContentTable().add(quantityField).width(100f).row();

            quantityDialog.button("OK", true);
            quantityDialog.button("Cancel", false);

            quantityDialog.show(stage);
        }

        private boolean sendGift(BackPackable item, int quantity) {
            // Remove item from player's inventory
            if(!currentPlayer.getInventory().removeCountFromBackPack(item, quantity)){
                // TODO: handel error
                return false;
            }
            targetFriendship.gift(currentPlayer, item);
            reciever.addToBackPack(item,quantity);

            // Add gift to friendship
            // Create a copy of the item for the gift
            BackPackable giftItem = createGiftItem(item, quantity);
            targetFriendship.gift(currentPlayer, giftItem);

            // You might want to add a rating system here
            // For now, let's assume a neutral rating
            targetFriendship.rateGift(3); // Neutral rating

            // Callback to refresh friends menu and close gift menu
            if (onGiftSent != null) {
                onGiftSent.run();
            }
            return true;
        }

        private BackPackable createGiftItem(BackPackable original, int quantity) {
            // This is a simplified version - you'll need to implement proper item copying
            // based on your BackPackable implementation
            try {
                BackPackable copy = original.getClass().newInstance();
                // Since the original item doesn't have quantity, we'll handle it in the Gift class
                return copy;
            } catch (Exception e) {
                // Fallback - return original item
                return original;
            }
        }
    }

}
