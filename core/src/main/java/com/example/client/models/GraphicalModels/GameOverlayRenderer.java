package com.example.client.models.GraphicalModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.example.common.Game;
import com.example.common.map.Map;
import com.example.common.time.Season;
import com.example.common.weather.WeatherOption;

public class GameOverlayRenderer {
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final SpriteBatch spriteBatch;
    private float overlayTime = 0f;

    private final int tileSideLength;

    // Colors
    private final Color nightTint  = new Color(0.08f, 0.12f, 0.22f, 0.45f);
    private final Color duskTint   = new Color(0.6f, 0.35f, 0.15f, 0.45f);
    private final Color dayTint    = new Color(0f, 0f, 0f, 0f);

    private final Color rainTint   = new Color(0.15f, 0.18f, 0.25f, 0.18f);
    private final Color stormTint  = new Color(0.08f, 0.1f, 0.18f, 0.30f);
    private final Color snowTint   = new Color(0.95f, 0.95f, 1f, 0.10f);

    private final Color autumnTint = new Color(1f, 0.6f, 0.25f, 0.25f);
    private final Color springTint = new Color(0f, 0f, 0f, 0f);

    // Rain & snow
    private Texture rainTexture;
    private Texture snowTexture;

    private static final int MAX_DROPS = 400;
    private float[][] rainDrops = new float[MAX_DROPS][2];
    private float[][] snowDrops = new float[MAX_DROPS][2];
    private boolean dropsInitialized = false;

    // Lightning
    private float lightningTimer = 0f;
    private float nextLightningIn = MathUtils.random(5f, 12f);

    public GameOverlayRenderer(SpriteBatch spriteBatch, int tileSideLength) {
        this.spriteBatch = spriteBatch;
        this.tileSideLength = tileSideLength;
    }

    public void draw(float delta, Game game, MapCamera mapCamera) {
        overlayTime += delta;

        Color finalTint = calculateTint(game, delta);

        // Background tint overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(mapCamera.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(finalTint);
        shapeRenderer.rect(0, 0, Map.COLS * tileSideLength, Map.ROWS * tileSideLength);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        WeatherOption weather = game.getWeather().getCurrentWeather();
        if (weather == WeatherOption.RAINY || weather == WeatherOption.STORM) {
            drawRainEffect(delta, weather == WeatherOption.STORM, mapCamera);
        } else if (weather == WeatherOption.SNOW) {
            drawSnowEffect(delta, mapCamera);
        }
    }

    private Color calculateTint(Game game, float delta) {
        int hour = game.getDateAndTime().getHour();
        int minute = game.getDateAndTime().getMinute();
        WeatherOption weather = game.getWeather().getCurrentWeather();
        Season season = game.getDateAndTime().getSeason();

        Color base = new Color();
        if (hour >= 18) {
            base.set(nightTint);
        }
        else if (hour >= 16) {
            float t = ((hour - 16) * 60f + minute) / 120f;
            base.set(dayTint).lerp(duskTint, t);
        }
        else {
            base.set(dayTint);
        }

        // Weather tint
        Color weatherColor = switch (weather) {
            case RAINY -> rainTint;
            case STORM -> stormTint;
            case SNOW -> snowTint;
            default -> new Color(0, 0, 0, 0);
        };
        if (weatherColor.a > 0f) {
            base.lerp(weatherColor, 0.8f);
            base.a = Math.max(base.a, weatherColor.a);
        }

        // Season tint
        Color seasonColor = switch (season) {
            case AUTUMN -> autumnTint;
            case SPRING -> springTint;
            default -> new Color(0, 0, 0, 0);
        };
        base.lerp(seasonColor, seasonColor.a);

        // Storm lightning
        if (weather == WeatherOption.STORM) {
            lightningTimer += delta;
            if (lightningTimer >= nextLightningIn) {
                float flash = Math.abs(MathUtils.sin(overlayTime * 20f));
                if (flash > 0.7f) {
                    base.set(1f, 1f, 1f, 0.8f * (flash - 0.7f) * 3f);
                }
                if (flash < 0.05f) {
                    lightningTimer = 0f;
                    nextLightningIn = MathUtils.random(60f, 120f);
                }
            }
        }

        return base;
    }

    private void initDrops() {
        for (int i = 0; i < MAX_DROPS; i++) {
            rainDrops[i][0] = MathUtils.random(0, Map.COLS * tileSideLength);
            rainDrops[i][1] = MathUtils.random(0, Map.ROWS * tileSideLength);
            snowDrops[i][0] = MathUtils.random(0, Map.COLS * tileSideLength);
            snowDrops[i][1] = MathUtils.random(0, Map.ROWS * tileSideLength);
        }
        dropsInitialized = true;
    }

    private void drawRainEffect(float delta, boolean heavy, MapCamera mapCamera) {
        if (!dropsInitialized) initDrops();
        if (rainTexture == null) rainTexture = new Texture("Sprites/time/rain_drop.png");

        spriteBatch.setProjectionMatrix(mapCamera.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.setColor(1f, 1f, 1f, heavy ? 1f : 0.75f);

        float speed = 100f;
        for (int i = 0; i < MAX_DROPS; i++) {
            rainDrops[i][1] -= delta * speed;
            if (rainDrops[i][1] < -rainTexture.getHeight()) {
                rainDrops[i][0] = MathUtils.random(0, Map.COLS * tileSideLength);
                rainDrops[i][1] = Map.ROWS * tileSideLength + MathUtils.random(20, 100);
            }
            spriteBatch.draw(rainTexture, rainDrops[i][0], rainDrops[i][1]);
        }
        spriteBatch.setColor(1f, 1f, 1f, 1f);
        spriteBatch.end();
    }

    private void drawSnowEffect(float delta, MapCamera mapCamera) {
        if (!dropsInitialized) initDrops();
        if (snowTexture == null) snowTexture = new Texture("Sprites/time/snow_drop.png");

        spriteBatch.setProjectionMatrix(mapCamera.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.setColor(1f, 1f, 1f, 0.7f);

        float speed = 50f;
        for (int i = 0; i < MAX_DROPS; i++) {
            snowDrops[i][1] -= delta * speed;
            float drift = MathUtils.sinDeg((snowDrops[i][1] + overlayTime * 50) % 360) * 2f;

            if (snowDrops[i][1] < -snowTexture.getHeight()) {
                snowDrops[i][0] = MathUtils.random(0, Map.COLS * tileSideLength);
                snowDrops[i][1] = Map.ROWS * tileSideLength + MathUtils.random(20, 100);
            }
            spriteBatch.draw(snowTexture, snowDrops[i][0] + drift, snowDrops[i][1]);
        }
        spriteBatch.setColor(1f, 1f, 1f, 1f);
        spriteBatch.end();
    }

    public void dispose() {
        if (rainTexture != null) rainTexture.dispose();
        if (snowTexture != null) snowTexture.dispose();
    }
}
