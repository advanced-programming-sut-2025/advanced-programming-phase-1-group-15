package com.example.client.models.GraphicalModels;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.example.client.controllers.ClientGameController;
import com.example.client.models.ClientApp;
import com.example.common.Player;
import com.example.common.relation.PlayerFriendship;
import com.example.common.stores.GeneralItem;
import com.example.common.stores.GeneralItemsType;

public class MarriageProposalNotification extends Dialog {

    private final Player proposer;
    private boolean isShowing = false;

    public MarriageProposalNotification(Skin skin) {
        super("Marriage Proposal", skin);
        this.proposer = null;
        setupDialog();
    }

    private void setupDialog() {
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);
        setVisible(false);
    }

    public boolean showProposal(Player proposer) {
        if (isShowing) return false;
        isShowing = true;

        buildContent(proposer);
        setVisible(true);

        if (getStage() != null) {
            show(getStage());
        }

        return true;
    }

    private void buildContent(Player proposer) {
        getContentTable().clear();
        getButtonTable().clear();

        Label messageLabel = new Label(
            proposer.getNickname() + " has proposed to you!\nWill you marry them?",
            getSkin()
        );
        messageLabel.setWrap(true);
        messageLabel.setAlignment(1);

        getContentTable().add(messageLabel).width(400).pad(20).center().row();

        TextButton acceptButton = new TextButton("Accept", getSkin());
        TextButton rejectButton = new TextButton("Reject", getSkin());

        getButtonTable().add(acceptButton).pad(5).width(150);
        getButtonTable().add(rejectButton).pad(5).width(150);

        acceptButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                handleAccept(proposer);
            }
        });

        rejectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                handleReject(proposer);
            }
        });
    }

    private void handleAccept(Player proposer) {
        Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();

        showResultAndClose(ClientGameController.respondMarriage(proposer.getUsername(),"accept").message());
    }

    private void handleReject(Player proposer) {
        showResultAndClose(ClientGameController.respondMarriage(proposer.getUsername(),"reject").message());
    }

    private void showResultAndClose(String message) {
        Dialog resultDialog = new Dialog("Result", getSkin()) {
            @Override
            protected void result(Object object) {
                remove();
            }
        };

        Label messageLabel = new Label(message, getSkin());
        messageLabel.setWrap(true);
        messageLabel.setAlignment(1);

        resultDialog.getContentTable().add(messageLabel).width(300).pad(20);
        resultDialog.button("OK", true);
        resultDialog.show(getStage());

        cancel();
    }

    public void cancel() {
        setVisible(false);
        remove();
        isShowing = false;
    }

    public boolean isShowing() {
        return isShowing;
    }
}
