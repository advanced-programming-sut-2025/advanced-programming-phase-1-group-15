package com.example.client.models.GraphicalModels.RightClickMenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.client.models.ClientApp;
import com.example.client.models.GraphicalModels.PopUpMenus.FriendshipStatusMenu;
import com.example.client.models.GraphicalModels.PopUpMenus.GiftSelectionMenu;
import com.example.client.models.GraphicalModels.PopUpMenus.PopUpMenu;
import com.example.client.models.GraphicalModels.PopUpMenus.QuestDisplayMenu;
import com.example.common.Player;
import com.example.common.npcs.NPC;
import com.example.common.npcs.NPCFriendShip;

public class NPCRightClickMenu extends RightClickMenu {
    private final NPC targetNPC;
    private final Player currentPlayer;
    private PopUpMenu activePopupMenu;

    public NPCRightClickMenu(Skin skin, NPC targetNPC, Runnable onHideCallback) {
        super(skin, onHideCallback);
        this.targetNPC = targetNPC;
        this.currentPlayer = ClientApp.currentGame.getCurrentPlayer();
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
                showTalkDialog();
                hide();
            }
        });

        giftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showGiftMenu();
            }
        });

        questsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showQuestMenu();
            }
        });

        friendshipButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showFriendshipMenu();
            }
        });
    }

    private void showTalkDialog() {
        String message = targetNPC.meet(currentPlayer);

        Dialog talkDialog = new Dialog("Conversation with " + targetNPC.getName(), skin) {
            @Override
            protected void result(Object object) {
                remove();
            }
        };

        Table contentTable = new Table();
        Label messageLabel = new Label(message, skin);
        messageLabel.setWrap(true);

        contentTable.add(messageLabel).width(400).pad(20);

        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);

        talkDialog.getContentTable().add(scrollPane).width(450).height(200);

        NPCFriendShip friendship = targetNPC.getFriendships().get(currentPlayer);
        if (friendship != null) {
            String statusInfo = String.format("\n\n Friendship Level: %d | Points: %d",
                friendship.getLevel(), friendship.getPoints());
            Label statusLabel = new Label(statusInfo, skin);
            statusLabel.setFontScale(0.8f);
            talkDialog.getContentTable().row();
            talkDialog.getContentTable().add(statusLabel).pad(5);
        }

        talkDialog.button("Close", true);
        talkDialog.show(stage);
    }

    private void showGiftMenu() {
        hide();
        GiftSelectionMenu giftMenu = new GiftSelectionMenu(
            skin,
            " Give Gift to " + targetNPC.getName(),
            () -> {
                activePopupMenu = null;
            },
            targetNPC
        );
        activePopupMenu = giftMenu;
        registerPopupMenu(activePopupMenu);
        activePopupMenu.show();
    }

    private void showQuestMenu() {
        hide();
        QuestDisplayMenu questMenu = new QuestDisplayMenu(
            skin,
            " Quests from " + targetNPC.getName(),
            () -> {
                activePopupMenu = null;
            },
            targetNPC
        );
        activePopupMenu = questMenu;
        registerPopupMenu(activePopupMenu);
        activePopupMenu.show();
    }

    private void showFriendshipMenu() {
        hide();
        FriendshipStatusMenu friendshipMenu = new FriendshipStatusMenu(
            skin,
            " Friendship Status with " + targetNPC.getName(),
            () -> {
                activePopupMenu = null;
            },
            targetNPC
        );
        activePopupMenu = friendshipMenu;
        registerPopupMenu(activePopupMenu);
        activePopupMenu.show();
    }

    @Override
    public void hide() {
        if (activePopupMenu != null && activePopupMenu.isVisible()) {
            activePopupMenu.hide();
            activePopupMenu = null;
        }
        super.hide();
    }

    private void registerPopupMenu(PopUpMenu popupMenu) {
        if (ClientApp.currentGame != null) {
            ClientApp.getCurrentGameView().setPopUpMenu(popupMenu);
        }
    }
}
