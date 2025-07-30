package com.example.models.animals;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.models.App;
import com.example.models.RandomGenerator;
import com.example.models.enums.Direction;
import com.example.models.map.AreaType;
import com.example.models.map.Tilable;
import com.example.models.map.Tile;
import com.example.models.time.DateAndTime;
import com.example.models.time.TimeObserver;

import java.util.ArrayList;

public class Animal implements Tilable, TimeObserver {
    private final AnimalType animalType;
    private String name;

    private final ArrayList<AnimalProductType> animalProductTypes = new ArrayList<>();
    private AnimalProduct currentProduct;

    Animation<TextureRegion> walkUpAnimation;
    Animation<TextureRegion> walkDownAnimation;
    Animation<TextureRegion> walkLeftAnimation;
    Animation<TextureRegion> walkRightAnimation;
    Animation<TextureRegion> eatingAnimation;
    TextureRegion currentFrame;
    Direction direction = Direction.DOWN;
    boolean isWalking = false;
    boolean isEating = false;
    float stateTime = 0f;
    static float walkingTime = 1f;
    static float eatingTime = 3f;
    int steps = 0;

    private int friendship = 0;
    private boolean petted = false;
    private boolean fed = false;

    private Tile tile;

    public Animal(AnimalType animalType, String name) {
        this.animalType = animalType;
        switch (animalType) {
            case CHICKEN -> {
                this.animalProductTypes.add(AnimalProductType.EGG);
                this.animalProductTypes.add(AnimalProductType.LARGE_EGG);
            }
            case DUCK -> {
                this.animalProductTypes.add(AnimalProductType.DUCK_EGG);
                this.animalProductTypes.add(AnimalProductType.DUCK_FEATHER);
            }
            case RABBIT -> {
                this.animalProductTypes.add(AnimalProductType.RABBIT_WOOL);
                this.animalProductTypes.add(AnimalProductType.RABBIT_LEG);
            }
            case DINOSAUR -> this.animalProductTypes.add(AnimalProductType.DINOSAUR_EGG);
            case COW -> {
                this.animalProductTypes.add(AnimalProductType.COW_MILK);
                this.animalProductTypes.add(AnimalProductType.COW_LARGE_MILK);
            }
            case GOAT -> {
                this.animalProductTypes.add(AnimalProductType.GOAT_MILK);
                this.animalProductTypes.add(AnimalProductType.GOAT_LARGE_MILK);
            }
            case SHEEP -> this.animalProductTypes.add(AnimalProductType.SHEEP_WOOL);
            case PIG -> this.animalProductTypes.add(AnimalProductType.TRUFFLE);
        }
        this.name = name;

        walkDownAnimation = animalType.walking_down;
        walkLeftAnimation = animalType.walking_left;
        walkRightAnimation = animalType.walking_right;
        walkUpAnimation = animalType.walking_up;
        eatingAnimation = animalType.eating;
        currentFrame = walkDownAnimation.getKeyFrame(0);
    }

    public AnimalType getAnimalType() {
        return animalType;
    }
    public String getAnimalTypeName() {
        return animalType.name().toLowerCase();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Maintenance getMaintenance() {
        return animalType.maintenance;
    }
    public int getPrice() {
        return (int) (animalType.price * (((double) friendship /1000) + 0.3));
    }

    public Tile getTile() {
        return tile;
    }
    public void setTile(Tile newTile) {
        if(tile != null) {
            tile.empty();
        }

        tile = newTile;
        tile.put(this);
    }

    public int getFriendship() {
        return friendship;
    }
    public void setFriendship(int friendship) {
        this.friendship = friendship;
    }

    public void pet() {
        if(!petted) {
            petted = true;
            friendship += 15;
        }
    }
    public boolean isPetted() {
        return petted;
    }

    public void feed() {
        if(!fed) {
            isEating = true;
            fed = true;
            friendship += 8;
        }
    }
    public boolean isFed() {
        return fed;
    }

    public ProductQuality calculateQuality() {
        double random = Math.random();
        double quality = ((double) friendship /1000) * (0.5 + 0.5 * random);

        if(quality <= 0.5) {
            return ProductQuality.NORMAL;
        }
        else if(quality <= 0.7) {
            return ProductQuality.SILVER;
        }
        else if(quality <= 0.9) {
            return ProductQuality.GOLD;
        }
        else {
            return ProductQuality.IRIDIUM;
        }
    }
    public void produce() {
        if(friendship > 100 && animalProductTypes.size() > 1) {
            double random = 0.5 + Math.random();
            double probability = (friendship + 150 * random) / 1500;

            if(probability > 0.5) {
                currentProduct = new AnimalProduct(animalProductTypes.get(1), calculateQuality());
            }
            else {
                currentProduct = new AnimalProduct(animalProductTypes.get(0), calculateQuality());
            }
        }
        else {
            currentProduct = new AnimalProduct(animalProductTypes.get(0), calculateQuality());
        }
    }
    public void setCurrentProduct(AnimalProduct currentProduct) {
        this.currentProduct = currentProduct;
    }
    public AnimalProduct getCurrentProduct() {
        return currentProduct;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == 9) {
            if(!fed) {
                friendship -= 20;
            }
            if(!petted) {
                friendship += (friendship/200) - 10;
            }
            if(!tile.getAreaType().equals(AreaType.COOP) &&
                    !tile.getAreaType().equals(AreaType.BARN)) {
                friendship -= 20;
            }

            if(fed && dateAndTime.getDay() % animalType.period == 0) {
                produce();
            }
            else {
                currentProduct = null;
            }
            petted = false;
            fed = false;
        }

        if(RandomGenerator.getInstance().randomBoolean()) {
            startRandomWalking();
        }
    }

    public void startRandomWalking() {
        isWalking = true;
        int dir = RandomGenerator.getInstance().randomInt(0, Direction.values().length - 1);
        direction = Direction.values()[dir];
        steps = RandomGenerator.getInstance().randomInt(1, 3);
    }
    public void moveOneTile() {
        switch (direction) {
            case UP:
                setTile(App.currentGame.getTile(tile.getPosition().x, tile.getPosition().y + 1));
                break;
            case DOWN:
                setTile(App.currentGame.getTile(tile.getPosition().x, tile.getPosition().y - 1));
                break;
            case LEFT:
                setTile(App.currentGame.getTile(tile.getPosition().x - 1, tile.getPosition().y));
                break;
            case RIGHT:
                setTile(App.currentGame.getTile(tile.getPosition().x + 1, tile.getPosition().y));
                break;
        }
    }
    public void updateAnimation(float delta) {
        if (isEating) {
            isWalking = false;
            stateTime += delta;
            currentFrame = eatingAnimation.getKeyFrame(stateTime, true);

            if (stateTime > eatingTime) {
                isEating = false;
                stateTime = 0;
            }
            return;
        }

        if (isWalking) {
            stateTime += delta;
            switch (direction) {
                case UP: currentFrame = walkUpAnimation.getKeyFrame(stateTime, true); break;
                case DOWN: currentFrame = walkDownAnimation.getKeyFrame(stateTime, true); break;
                case LEFT: currentFrame = walkLeftAnimation.getKeyFrame(stateTime, true); break;
                case RIGHT: currentFrame = walkRightAnimation.getKeyFrame(stateTime, true); break;
            }

            if (stateTime > walkingTime) {
                moveOneTile();
                steps--;
                stateTime = 0;

                if (steps == 0) {
                    isWalking = false;
                }
            }
        } else {
            stateTime = 0;
            switch (direction) {
                case UP: currentFrame = walkUpAnimation.getKeyFrame(0); break;
                case DOWN: currentFrame = walkDownAnimation.getKeyFrame(0); break;
                case LEFT: currentFrame = walkLeftAnimation.getKeyFrame(0); break;
                case RIGHT: currentFrame = walkRightAnimation.getKeyFrame(0); break;
            }
        }
    }

    @Override
    public Sprite getSprite() {
        return new Sprite(currentFrame);
    }
}
