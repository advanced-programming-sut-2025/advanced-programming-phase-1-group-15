package models.relation;

import models.tools.BackPackable;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerFriendShip {
    private int xp;
    private int level;

    private ArrayList<String> messages = new ArrayList<>();

    private HashMap<BackPackable,Integer> gifts = new HashMap<>();
}
