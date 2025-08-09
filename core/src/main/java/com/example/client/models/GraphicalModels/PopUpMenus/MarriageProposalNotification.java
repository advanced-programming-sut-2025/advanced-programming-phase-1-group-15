package com.example.client.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.client.controllers.ClientGameController;
import com.example.client.models.ClientApp;

public class MarriageProposalNotification extends PopUpMenu {
    private String proposerName;

    public MarriageProposalNotification(Skin skin, Runnable onHideCallback) {
        super(skin, "Marriage Proposal", 400, 200, onHideCallback);
    }

    public void setProposal(String proposerName) {
        this.proposerName = proposerName;
    }

    @Override
    protected void populate(Window w) {
        w.clear();

        Label messageLabel = new Label(proposerName + " has proposed marriage to you!", skin);
        messageLabel.setWrap(true);

        Label questionLabel = new Label("Do you accept?", skin);
        questionLabel.setWrap(true);

        TextButton acceptButton = new TextButton("Accept", skin);
        TextButton declineButton = new TextButton("Decline", skin);

        acceptButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ClientGameController.respondMarriage(proposerName, "accept");
                hide();
            }
        });

        declineButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ClientGameController.respondMarriage(proposerName, "decline");
                hide(); // This will trigger the onHideCallback
            }
        });

        Table contentTable = new Table();
        contentTable.pad(20);

        contentTable.add(messageLabel).width(350).padBottom(10).row();
        contentTable.add(questionLabel).width(350).padBottom(20).row();

        Table buttonTable = new Table();
        buttonTable.add(acceptButton).width(100).height(40).padRight(10);
        buttonTable.add(declineButton).width(100).height(40);

        contentTable.add(buttonTable).row();

        w.add(contentTable).expand().fill();
    }

    public void showProposal(String proposerName) {
        setProposal(proposerName);
        show();
    }

    @Override
    public void show() {
        super.show();
    }
}

