package com.example.client.models.GraphicalModels.RightClickMenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.common.Player;
import com.example.client.models.ClientApp;
import com.example.common.relation.PlayerFriendship;

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


        // Hug button
        hugButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();
                PlayerFriendship friendship = findFriendship(currentPlayer, targetPlayer);
                if (friendship != null) {
                    friendship.hug();
                    System.out.println("Hugged " + targetPlayer.getNickname());
                }
                hide();
            }
        });

        // Buy Flower button
        buyFlowerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();
                PlayerFriendship friendship = findFriendship(currentPlayer, targetPlayer);
                if (friendship != null) {
                    friendship.flower();
                    System.out.println("Bought flower for " + targetPlayer.getNickname());
                }
                hide();
            }
        });

        // Marry button
        marryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = ClientApp.currentGame.getCurrentPlayer();
                PlayerFriendship friendship = findFriendship(currentPlayer, targetPlayer);
                if (friendship != null && friendship.getLevel() >= 3) {
                    friendship.marry();
                    System.out.println("Married " + targetPlayer.getNickname());
                } else {
                    System.out.println("Friendship level too low for marriage!");
                }
                hide();
            }
        });
    }

    private PlayerFriendship findFriendship(Player player1, Player player2) {
        return null; // TODO: Implement friendship lookup
    }
}
