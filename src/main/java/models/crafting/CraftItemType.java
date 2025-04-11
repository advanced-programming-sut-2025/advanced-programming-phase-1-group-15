package models.crafting;

import models.tools.BackPackable;

import java.util.HashMap;

public enum CraftItemType {
    CHERRY_BOMB("4 copper ore + 1 coal = 1 cherry bomb",
            new HashMap<>(),
            Ability.miningAbility,
            1,
            50);

    public String recepie;
    public HashMap<BackPackable, Integer> ingredients;
    public Ability ability;
    public int levelRequired;
    public int price;

    CraftItemType(String recepie, HashMap<BackPackable, Integer> ingredients, Ability ability, int levelRequired, int price) {
        this.recepie = recepie;
        this.ingredients = ingredients;
        this.ability = ability;
        this.levelRequired = levelRequired;
        this.price = price;
    }

}
