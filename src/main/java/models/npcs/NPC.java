package models.npcs;

import models.Player;
import models.relation.NPCFriendShip;
import models.tools.BackPackable;

import java.util.ArrayList;
import java.util.HashMap;

public class NPC {
    protected String name;
    protected String job;

    protected ArrayList<String> dialogues = new ArrayList<>();

    protected ArrayList<BackPackable> favourites = new ArrayList<>();

    protected ArrayList<Boolean> questsAvailability = new ArrayList<>();
    protected HashMap<BackPackable, Integer> quests = new HashMap<>();
    protected HashMap<BackPackable, Integer> rewards = new HashMap<>();

    protected HashMap<Player, NPCFriendShip> friendships = new HashMap<>();

    public void meet(String message) {

    }
    public void gift(BackPackable item) {

    }

    public void showQuests() {

    }
    public void finishQuest(BackPackable item) {

    }
}