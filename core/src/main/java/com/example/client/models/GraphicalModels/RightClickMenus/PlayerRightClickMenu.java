package com.example.client.models.GraphicalModels.RightClickMenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.client.controllers.ClientGameController;
import com.example.client.controllers.ClientGameListener;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.enums.Gender;
import com.example.common.relation.PlayerFriendship;
import com.example.common.stores.GeneralItem;
import com.example.common.stores.GeneralItemsType;
import com.example.common.User;
import com.example.common.map.Tile;
import com.example.common.Result;

public class PlayerRightClickMenu extends RightClickMenu {
    private final Player targetPlayer;

    public PlayerRightClickMenu(Skin skin, Player targetPlayer, Runnable onHideCallback) {
        super(skin, onHideCallback);
        this.targetPlayer = targetPlayer;
    }

    @Override
    protected void setupMenuContent() {
        TextButton hugButton = new TextButton("Hug", skin);
        TextButton buyFlowerButton = new TextButton("Buy Flower", skin);
        TextButton marryButton = new TextButton("Marry", skin);

        menuTable.add(hugButton).pad(3).fillX().row();
        menuTable.add(buyFlowerButton).pad(3).fillX().row();
        menuTable.add(marryButton).pad(3).fillX().row();


        hugButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();
                showResultDialog(ClientGameController.hug(targetPlayer.getUsername()).message());
                //hide();
            }
        });

        buyFlowerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();

                showResultDialog(ClientGameController.flower(targetPlayer.getUsername()).message());
            }
        });

        marryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();

                Tile playerTile = ClientApp.currentGame.getTile(currentPlayer.getPosition());
                Tile targetTile = ClientApp.currentGame.getTile(targetPlayer.getPosition());
                if (playerTile == null || targetTile == null || !playerTile.isAdjacent(targetTile)) {
                    showResultDialog("You must be next to a player to ask for marriage!");
                    hide();
                    return;
                }

                if (currentPlayer.getGender().equals(Gender.GIRL)) {
                    showResultDialog("Only a boy can propose for marriage!");
                    hide();
                    return;
                } else if (targetPlayer.getGender().equals(Gender.BOY)) {
                    showResultDialog("Cannot marry another boy.");
                    hide();
                    return;
                }

                PlayerFriendship friendship = findFriendship(currentPlayer, targetPlayer);
                if (friendship == null) {
                    showResultDialog("Friendship not found.");
                    hide();
                    return;
                }

                GeneralItem ring = (GeneralItem) currentPlayer.getInventory().getItemByName(GeneralItemsType.WEDDING_RING.getName());
                if (ring == null) {
                    showResultDialog("You must first purchase a wedding ring!");
                    hide();
                    return;
                }

                if (friendship.getLevel() < 3) {
                    showResultDialog("Friendship level is too low for marriage! At least 3 levels required.");
                } else {
                    targetPlayer.addMessage(new PlayerFriendship.Message(currentPlayer, "Will you Marry me?"));
                    showResultDialog("Your proposal has been sent to " + targetPlayer.getNickname() + ".");
                }
                hide();
            }
        });
    }

    private PlayerFriendship findFriendship(Player player1, Player player2) {
        return ClientApp.currentGame.getFriendshipByPlayers(player1, player2);
    }

    private void showResultDialog(String message) {
        Dialog resultDialog = new Dialog(" Result", skin) {
            @Override
            protected void result(Object object) {
                remove();
            }
        };

        Table contentTable = new Table();
        Label messageLabel = new Label(message, skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(1);

        contentTable.add(messageLabel).width(300).pad(20);

        resultDialog.getContentTable().add(contentTable);
        resultDialog.button("OK", true);
        resultDialog.show(stage);
    }
}
