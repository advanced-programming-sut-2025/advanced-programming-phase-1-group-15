package com.example.models.GraphicalModels.RightClickMenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.models.npcs.NPC;
import com.example.models.App;
import com.example.models.Player;

public class NPCRightClickMenu extends RightClickMenu {
    private final NPC targetNPC;

    public NPCRightClickMenu(Skin skin, NPC targetNPC, Runnable onHideCallback) {
        super(skin, onHideCallback);
        this.targetNPC = targetNPC;
    }

    @Override
    protected void setupMenuContent() {
        TextButton talkButton = new TextButton("Talk", skin);
        TextButton giftButton = new TextButton("Gift", skin);
        TextButton questsButton = new TextButton("Quests", skin);
        TextButton friendshipButton = new TextButton("Friendship", skin);

        menuTable.add(talkButton).pad(3).fillX().row();
        menuTable.add(giftButton).pad(3).fillX().row();
        menuTable.add(questsButton).pad(3).fillX().row();
        menuTable.add(friendshipButton).pad(3).fillX().row();

        talkButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = App.currentGame.getCurrentPlayer();
                String message = targetNPC.meet(currentPlayer);
                hide();
            }
        });

        giftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: Open gift selection menu
                hide();
            }
        });

        // Quests button
        questsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = App.currentGame.getCurrentPlayer();
                hide();
            }
        });

        friendshipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player currentPlayer = App.currentGame.getCurrentPlayer();
                if (targetNPC.getFriendships().containsKey(currentPlayer)) {
                    int friendshipLevel = targetNPC.getFriendships().get(currentPlayer).getLevel();
                    int friendshipPoints = targetNPC.getFriendships().get(currentPlayer).getPoints();
                    System.out.println("Friendship with " + targetNPC.getName() +
                            " - Level: " + friendshipLevel + ", Points: " + friendshipPoints);
                } else {
                    System.out.println("No friendship established with " + targetNPC.getName());
                }
                hide();
            }
        });
    }
}
