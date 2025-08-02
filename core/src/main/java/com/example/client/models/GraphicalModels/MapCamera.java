package com.example.client.models.GraphicalModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.example.common.map.Map;
import com.example.common.Player;

public class MapCamera {
    private final OrthographicCamera camera;
    private Player player;
    private static final float ZOOM = 2.0f;

    public MapCamera(Player player) {
        this.player = player;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth() / ZOOM, Gdx.graphics.getHeight() / ZOOM);
    }

    public void update() {
        if (player == null) return;

        float tileSize = 16f;
        float mapWidth = Map.COLS * tileSize;
        float mapHeight = Map.ROWS * tileSize;

        camera.position.x = Math.max(camera.viewportWidth / 2f,
            Math.min(player.getPosition().getX() * tileSize, mapWidth - camera.viewportWidth / 2f));
        camera.position.y = Math.max(camera.viewportHeight / 2f,
            Math.min(player.getPosition().getY() * tileSize, mapHeight - camera.viewportHeight / 2f));

        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width / ZOOM;
        camera.viewportHeight = height / ZOOM;
        camera.update();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
