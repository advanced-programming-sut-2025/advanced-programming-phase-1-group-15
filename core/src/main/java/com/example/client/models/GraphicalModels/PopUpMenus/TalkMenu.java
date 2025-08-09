package com.example.client.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.client.controllers.ClientGameController;
import com.example.common.Player;
import com.example.common.Result;
import org.w3c.dom.Text;

public class TalkMenu extends PopUpMenu{
    private final Player targetPlayer;
    public TalkMenu(Skin skin, Player targetPlayer, Runnable onHideCallback) {
        super(skin,"Talk",400f,300f,onHideCallback);
        this.targetPlayer = targetPlayer;
    }

    @Override
    protected void populate(Window w) {
        Table contentTable = new Table();
        contentTable.defaults().pad(5);

        TextField messageField = new TextField("", skin);
        contentTable.add(messageField).expandX().fillX().row();

        TextButton talkButton = new TextButton("Send", skin);
        talkButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                String message = messageField.getText().trim();
                if (!message.isEmpty()) {
                    Result result = ClientGameController.talkFriendship(targetPlayer.getUsername(), message);
                    showResultDialog(result.message());
                }
            }
        });

        contentTable.add(talkButton).expandX().fillX().row();
        w.add(contentTable).expand().fill();
    }

}
