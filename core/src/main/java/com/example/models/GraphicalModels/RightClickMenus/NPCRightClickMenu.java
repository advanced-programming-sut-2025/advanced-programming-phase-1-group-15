package com.example.models.GraphicalModels.RightClickMenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.models.App;
import com.example.models.GraphicalModels.PopUpMenus.FriendshipStatusMenu;
import com.example.models.GraphicalModels.PopUpMenus.GiftSelectionMenu;
import com.example.models.GraphicalModels.PopUpMenus.QuestDisplayMenu;
import com.example.models.Player;
import com.example.models.npcs.NPC;

public class NPCRightClickMenu extends RightClickMenu {
    private final NPC targetNPC;
    private final Player currentPlayer;

    public NPCRightClickMenu(Skin skin, NPC targetNPC, Runnable onHideCallback) {
        super(skin, onHideCallback);
        this.targetNPC = targetNPC;
        this.currentPlayer = App.currentGame.getCurrentPlayer();
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

                Dialog talkDialog = new Dialog("Conversation with " + targetNPC.getName(), skin);
                talkDialog.text(message);
                talkDialog.button("OK");
                talkDialog.show(stage);

                hide();
            }
        });

        giftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();

                GiftSelectionMenu giftMenu = new GiftSelectionMenu(
                    skin,
                    "Give Gift to " + targetNPC.getName(),
                    () -> {},
                    targetNPC
                );
                giftMenu.show();
            }
        });

        questsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();

                QuestDisplayMenu questMenu = new QuestDisplayMenu(
                    skin,
                    "Quests from " + targetNPC.getName(),
                    () -> {},
                    targetNPC
                );
                questMenu.show();
            }
        });

        friendshipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();

                FriendshipStatusMenu friendshipMenu = new FriendshipStatusMenu(
                    skin,
                    "Friendship Status with " + targetNPC.getName(),
                    () -> {},
                    targetNPC
                );
                friendshipMenu.show();
            }
        });
    }
}
