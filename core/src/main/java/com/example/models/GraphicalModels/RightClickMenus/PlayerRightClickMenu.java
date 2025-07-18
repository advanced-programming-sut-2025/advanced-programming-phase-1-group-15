package com.example.models.GraphicalModels.RightClickMenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.models.Player;
import com.example.models.App;
import com.example.models.relation.PlayerFriendship;

public class PlayerRightClickMenu extends RightClickMenu {
    private final Player targetPlayer;

    public PlayerRightClickMenu(Skin skin, Player targetPlayer, Runnable onHideCallback) {
        super(skin, onHideCallback);
        this.targetPlayer = targetPlayer;
    }

    @Override
    protected void setupMenuContent() {
        TextButton talkButton = new TextButton("Talk", skin);
        TextButton giftButton = new TextButton("Gift", skin);
        TextButton hugButton = new TextButton("Hug", skin);
        TextButton buyFlowerButton = new TextButton("Buy Flower", skin);
        TextButton marryButton = new TextButton("Marry", skin);
        TextButton friendshipButton = new TextButton("Friendship", skin);

        menuTable.add(talkButton).pad(3).fillX().row();
        menuTable.add(giftButton).pad(3).fillX().row();
        menuTable.add(hugButton).pad(3).fillX().row();
        menuTable.add(buyFlowerButton).pad(3).fillX().row();
        menuTable.add(marryButton).pad(3).fillX().row();
        menuTable.add(friendshipButton).pad(3).fillX().row();

        // Talk button
        talkButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = App.currentGame.getCurrentPlayer();
                PlayerFriendship friendship = findFriendship(currentPlayer, targetPlayer);
                if (friendship != null) {
                    friendship.talk(currentPlayer, "Hello!");
                    System.out.println("Talked to " + targetPlayer.getNickname());
                }
                hide();
            }
        });

        // Gift button
        giftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: Open gift selection menu
                System.out.println("Opening gift menu for " + targetPlayer.getNickname());
                hide();
            }
        });

        // Hug button
        hugButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = App.currentGame.getCurrentPlayer();
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
                Player currentPlayer = App.currentGame.getCurrentPlayer();
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
                Player currentPlayer = App.currentGame.getCurrentPlayer();
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

        // Friendship button
        friendshipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = App.currentGame.getCurrentPlayer();
                PlayerFriendship friendship = findFriendship(currentPlayer, targetPlayer);
                if (friendship != null) {
                    System.out.println("Friendship with " + targetPlayer.getNickname() +
                        " - Level: " + friendship.getLevel() +
                        ", XP: " + friendship.getXP() +
                        ", Married: " + friendship.isMarry());
                } else {
                    System.out.println("No friendship established with " + targetPlayer.getNickname());
                }
                hide();
            }
        });
    }

    private PlayerFriendship findFriendship(Player player1, Player player2) {
        // You'll need to implement this method to find the friendship between two players
        // This assumes you have a way to access all friendships in your game
        // For now, this is a placeholder
        return null; // TODO: Implement friendship lookup
    }
}
