package com.example.models.GraphicalModels.PopUpMenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.example.models.animals.Animal;
import org.w3c.dom.Text;

public class AnimalMenu extends PopUpMenu {
    private static final float WIDTH = 600f;
    private static final float HEIGHT = 600f;
    private static final float PADDING = 10f;

    private final Animal animal;
    private Label messageLabel;

    public AnimalMenu(Skin skin, String title, Runnable onHideCallback, Animal animal) {
        super(skin, title, WIDTH, HEIGHT, onHideCallback);
        this.animal = animal;
    }

    @Override
    protected void populate(Window w) {
        Image animalImage = new Image(animal.getSprite());
        animalImage.setSize(64, 64);
        w.add(animalImage).size(64, 64).colspan(3).padBottom(PADDING).row();

        w.add(new Label("name: ", skin));
        TextField nameField = new TextField(animal.getName(), skin);
        w.add(nameField);

        TextButton editButton = new TextButton("Edit", skin);
        w.add(editButton).row();
        editButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                animal.setName(nameField.getText());
                messageLabel.setText("animal's name changed to " + animal.getName() + "!");
            }
        });

        TextButton petButton = new TextButton("Pet", skin);
        TextButton feedButton = new TextButton("Feed", skin);
        TextButton shepherdButton = new TextButton("Shepherd", skin);
        w.add(petButton).width(150);
        w.add(feedButton).width(150);
        w.add(shepherdButton).width(150).row();

        Table table = new Table();
        TextButton productsButton = new TextButton("Products", skin);
        TextButton sellButton = new TextButton("Sell", skin);
        w.add(productsButton).width(150);
        w.add(sellButton).width(150).row();

        messageLabel = new Label("", skin);
        w.add(messageLabel).colspan(3).padTop(PADDING).row();
    }
}
