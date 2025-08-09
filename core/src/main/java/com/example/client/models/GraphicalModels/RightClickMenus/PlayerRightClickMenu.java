package com.example.client.models.GraphicalModels.RightClickMenus;

import com.badlogic.gdx.graphics.Color;
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
        TextField messageField = new TextField("your message", skin);
        messageField.setColor(Color.RED);
        TextButton talkButton = new TextButton("Talk", skin);
        TextButton hugButton = new TextButton("Hug", skin);
        TextButton buyFlowerButton = new TextButton("Buy Flower", skin);
        TextButton marryButton = new TextButton("Marry", skin);

        menuTable.add(messageField).pad(3).fillX().row();
        menuTable.add(talkButton).pad(3).fillX().row();
        menuTable.add(hugButton).pad(3).fillX().row();
        menuTable.add(buyFlowerButton).pad(3).fillX().row();
        menuTable.add(marryButton).pad(3).fillX().row();

        talkButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                //TalkMenu talkMenu = new TalkMenu(skin, targetPlayer, () -> hide());
                //talkMenu.show();
                //hide();
                String message = messageField.getText().trim();
                if (!message.isEmpty()) {
                    Result result = ClientGameController.talkFriendship(targetPlayer.getUsername(), message);
                    showResultDialog(result.message());
                }
            }
        });

        hugButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showResultDialog(ClientGameController.hug(targetPlayer.getUsername()).message());
            }
        });

        buyFlowerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showResultDialog(ClientGameController.flower(targetPlayer.getUsername()).message());
            }
        });

        marryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showResultDialog(ClientGameController.marry(targetPlayer.getUsername()).message());
            }
        });
    }


    private PlayerFriendship findFriendship(Player player1, Player player2) {
        return ClientApp.currentGame.getFriendshipByPlayers(player1, player2);
    }
}
