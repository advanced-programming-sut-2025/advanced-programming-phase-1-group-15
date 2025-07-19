package com.example.models.GraphicalModels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class NotificationLabel extends Label {
    public NotificationLabel(Skin skin) {
        super("", skin);
        this.setVisible(false);
    }

    public void showMessage(String text, Color color) {
        this.setText(text);
        this.setColor(color);
        this.getColor().a = 0f;
        this.setVisible(true);
        this.clearActions();
        this.addAction(Actions.sequence(
            Actions.fadeIn(0.4f),
            Actions.delay(1.5f),
            Actions.fadeOut(0.4f)
        ));
    }
}
