package com.example.models.GraphicalModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.example.models.Player;

public class MapCamera {


    private OrthographicCamera camera;
    private Player player;

    public MapCamera(Player player) {
        this.player = player;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void update(){
        if (player == null) return;
        camera.position.x = player.getPosition().x;
        camera.position.y = player.getPosition().y;
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
