package com.example.client.models.GraphicalModels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class NotificationLabel extends Label {
    private boolean isShowing = false;

    public NotificationLabel(Skin skin) {
        super("", skin);
        this.setVisible(false);
    }

    public void showMessage(String text, Color color, Runnable onComplete) {
        if (isShowing) return;
        isShowing = true;
        this.setText(text);
        this.setColor(color);
        this.getColor().a = 0f;
        this.setVisible(true);
        this.clearActions();
        SequenceAction sequence = Actions.sequence(
            Actions.fadeIn(0.4f),
            Actions.delay(20f),
            Actions.fadeOut(0.4f),
            Actions.run(() -> {
                isShowing = false;
                if (onComplete != null) {
                    onComplete.run();
                }
            })
        );
        this.addAction(sequence);
    }
}
