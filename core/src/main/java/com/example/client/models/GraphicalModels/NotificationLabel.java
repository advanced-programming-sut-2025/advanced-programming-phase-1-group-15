package com.example.client.models.GraphicalModels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class NotificationLabel extends Label {
    private boolean isShowing = false;

    public NotificationLabel(Skin skin) {
        super("", skin);
        setVisible(false);
    }

    public boolean showMessage(String text, Color color, Runnable onComplete) {
        if (isShowing) return false;
        isShowing = true;

        setText(text);
        setColor(new Color(color.r, color.g, color.b, 0f));
        setVisible(true);
        clearActions();

        addAction(Actions.sequence(
            Actions.fadeIn(0.4f),
            Actions.delay(6f),
            Actions.fadeOut(0.4f),
            Actions.run(() -> {
                setVisible(false);
                isShowing = false;
                if (onComplete != null) onComplete.run();
            })
        ));

        return true;
    }

    public boolean showMessage(String text, Color color, Runnable onComplete, float duration) {
        if (isShowing) return false;
        isShowing = true;

        setText(text);
        setColor(new Color(color.r, color.g, color.b, 0f));
        setVisible(true);
        clearActions();

        addAction(Actions.sequence(
            Actions.fadeIn(0.4f),
            Actions.delay(duration),
            Actions.fadeOut(0.4f),
            Actions.run(() -> {
                setVisible(false);
                isShowing = false;
                if (onComplete != null) onComplete.run();
            })
        ));

        return true;
    }

    public void cancel() {
        clearActions();
        setVisible(false);
        isShowing = false;
    }

    public boolean isShowing() {
        return isShowing;
    }
}
