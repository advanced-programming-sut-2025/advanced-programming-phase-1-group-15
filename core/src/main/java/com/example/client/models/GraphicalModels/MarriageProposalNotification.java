package com.example.client.models.GraphicalModels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.relation.PlayerFriendship;
import com.example.common.stores.GeneralItem;
import com.example.common.stores.GeneralItemsType;
import com.example.common.enums.Gender;

public class MarriageProposalNotification extends Dialog {

    private final Player proposer;

    public MarriageProposalNotification(Skin skin, Player proposer) {
        super("Marriage Proposal", skin);
        this.proposer = proposer;
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        Table contentTable = getContentTable();
        contentTable.clear();

        Label messageLabel = new Label(proposer.getNickname() + " has proposed to you!\nWill you marry him?", skin);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(1);

        contentTable.add(messageLabel).width(400).pad(20).center().row();

        TextButton acceptButton = new TextButton("Accept", skin);
        TextButton rejectButton = new TextButton("Reject", skin);

        getButtonTable().add(acceptButton).pad(5).width(150);
        getButtonTable().add(rejectButton).pad(5).width(150);

        acceptButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();
                PlayerFriendship friendship = ClientApp.currentGame.getFriendshipByPlayers(proposer, currentPlayer);
                if (friendship == null) {
                    showResultDialog("An error occurred with the proposal.");
                    remove();
                    return;
                }

                GeneralItem ring = (GeneralItem) proposer.getInventory().getItemByName(GeneralItemsType.WEDDING_RING.getName());
                if (ring == null) {
                    showResultDialog("The proposer no longer has the wedding ring. Proposal invalid.");
                    remove();
                    return;
                }

                friendship.marry();
                currentPlayer.addToBackPack(ring, 1);
                proposer.getInventory().removeCountFromBackPack(ring, 1);

                proposer.addMessage(new PlayerFriendship.Message(currentPlayer, "accepted your proposal! CONGRATS!"));
                showResultDialog("You accepted the proposal! CONGRATULATIONS!");
                remove();
            }
        });

        rejectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();
                PlayerFriendship friendship = ClientApp.currentGame.getFriendshipByPlayers(proposer, currentPlayer);
                if (friendship == null) {
                    showResultDialog("An error occurred with the proposal.");
                    remove();
                    return;
                }

                friendship.reject();
                proposer.reject(ClientApp.currentGame.getDateAndTime().getDay());

                proposer.addMessage(new PlayerFriendship.Message(currentPlayer, "rejected your proposal... :("));
                showResultDialog("You rejected the proposal.");
                remove();
            }
        });
    }

    private void showResultDialog(String message) {
        Dialog resultDialog = new Dialog(" Result", getSkin()) {
            @Override
            protected void result(Object object) {
                remove();
            }
        };

        Table contentTable = new Table();
        Label messageLabel = new Label(message, getSkin());
        messageLabel.setWrap(true);
        messageLabel.setAlignment(1); // CENTER

        contentTable.add(messageLabel).width(300).pad(20);

        resultDialog.getContentTable().add(contentTable);
        resultDialog.button("OK", true);
        resultDialog.show(getStage());
    }
}
