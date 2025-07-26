package com.example.models.GraphicalModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NPCUIHelper {
    private static NPCUIHelper instance;
    private TextureRegion messageIndicator;
    private TextureRegion whitePixel;

    private NPCUIHelper() {
        createMessageIndicator();
    }

    public static NPCUIHelper getInstance() {
        if (instance == null) {
            instance = new NPCUIHelper();
        }
        return instance;
    }

    private void createMessageIndicator() {
        // Create a simple message indicator (yellow circle with exclamation mark)
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);

        // Fill with yellow background
        pixmap.setColor(Color.YELLOW);
        pixmap.fillCircle(8, 8, 7);

        // Add black border
        pixmap.setColor(Color.BLACK);
        pixmap.drawCircle(8, 8, 7);

        // Add exclamation mark (simplified)
        pixmap.setColor(Color.BLACK);
        pixmap.drawLine(8, 4, 8, 10); // Vertical line
        pixmap.drawPixel(8, 12); // Dot

        Texture texture = new Texture(pixmap);
        messageIndicator = new TextureRegion(texture);
        pixmap.dispose();

        // Create white pixel for other UI purposes
        Pixmap whitePixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        whitePixmap.setColor(Color.WHITE);
        whitePixmap.fill();
        Texture whiteTexture = new Texture(whitePixmap);
        whitePixel = new TextureRegion(whiteTexture);
        whitePixmap.dispose();
    }

    public void drawMessageIndicator(SpriteBatch batch, int x, int y, int size) {
        batch.draw(messageIndicator, x, y, size, size);
    }

    public void drawColoredRectangle(SpriteBatch batch, int x, int y, int width, int height, Color color) {
        Color oldColor = batch.getColor();
        batch.setColor(color);
        batch.draw(whitePixel, x, y, width, height);
        batch.setColor(oldColor);
    }

    public void dispose() {
        if (messageIndicator != null) {
            messageIndicator.getTexture().dispose();
        }
        if (whitePixel != null) {
            whitePixel.getTexture().dispose();
        }
    }
}
