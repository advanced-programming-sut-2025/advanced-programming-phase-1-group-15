package com.example.models.GraphicalModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.example.models.Player;

public class HUDCamera {
    private OrthographicCamera camera;

    private final Integer screenWidth = 1920;
    private final Integer screenHeight = 1080;

    public HUDCamera() {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void update(){
        camera.position.set(screenWidth/2f, screenHeight/2f, 0);
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
}
