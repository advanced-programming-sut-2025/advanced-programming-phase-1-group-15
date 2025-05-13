package models.npcs;

import models.App;
import models.animals.Fish;
import models.animals.FishType;
import models.map.Map;
import models.map.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultNPCs {
    private static final DefaultNPCs defaultNPCs = new DefaultNPCs();


    public static DefaultNPCs getInstance() {
        return defaultNPCs;
    }

    private DefaultNPCs() {
        //createAll()
    }

    public HashMap<String,NPC> defaultOnes() {
        var gameMap = App.currentGame.getMap();
        HashMap<String,NPC> list = new HashMap<>();

        // Sebastian
        NPC seb = new NPC("Sebastian", "Programmer", gameMap.getTile(new Position(10, 5)));
        seb.favourites.add(new Fish(FishType.CRIMSON_FISH));
        seb.favourites.add(new Fish(FishType.CRIMSON_FISH));
        seb.favourites.add(new Fish(FishType.CRIMSON_FISH));
        seb.addQuestTemplate(new Fish(FishType.CRIMSON_FISH), 1, new Fish(FishType.CRIMSON_FISH), 5000, 0);
        seb.addQuestTemplate(new Fish(FishType.CRIMSON_FISH), 150, new Fish(FishType.CRIMSON_FISH), 50, 1);
        seb.addQuestTemplate(new Fish(FishType.CRIMSON_FISH), 1, null, 0, 2);
        list.put(seb.getName(),seb);

        // Abigail
        NPC abi = new NPC("Abigail", "Adventurer", gameMap.getTile(new Position(15, 8)));
        abi.favourites.add(new Fish(FishType.CRIMSON_FISH));
        abi.favourites.add(new Fish(FishType.CRIMSON_FISH));
        abi.favourites.add(new Fish(FishType.CRIMSON_FISH));
        abi.addQuestTemplate(new Fish(FishType.CRIMSON_FISH), 1, new Fish(FishType.CRIMSON_FISH), 500, 0);
        abi.addQuestTemplate(new Fish(FishType.CRIMSON_FISH), 50, new Fish(FishType.CRIMSON_FISH), 1, 1);
        abi.addQuestTemplate(new Fish(FishType.CRIMSON_FISH), 12, null, 750, 2);
        list.put(abi.getName(),abi);
//
//        // Harvey
//        NPC harv = new NPC("Harvey", "Doctor", gameMap.getTile(5, 12));
//        harv.favourites.add(new Coffee());
//        harv.favourites.add(new TruffleOil());
//        harv.favourites.add(new Wine());
//        harv.addQuestTemplate(new Salmon(), 1, null, 0, 0);
//        harv.addQuestTemplate(new Wine(), 1, new Salad(), 5, 1);
//        harv.addQuestTemplate(new Hardwood(), 10, new Gold(), 500, 2);
//        list.add(harv);
//
//        // Leah
//        NPC lea = new NPC("Leah", "Artist", gameMap.getTile(20, 3));
//        lea.favourites.add(new Salad());
//        lea.favourites.add(new Grape());
//        lea.favourites.add(new Wine());
//        lea.addQuestTemplate(new Salmon(), 1, new Recipe("Deluxe Scarecrow"), 3, 0);
//        lea.addQuestTemplate(new Wood(), 200, null, 0, 1);
//        lea.addQuestTemplate(new Wood(), 80, new Gold(), 1000, 2);
//        list.add(lea);
//
//        // Robin
//        NPC rob = new NPC("Robin", "Carpenter", gameMap.getTile(3, 7));
//        rob.favourites.add(new Spaghetti());
//        rob.favourites.add(new Wood());
//        rob.favourites.add(new IronBar());
//        rob.addQuestTemplate(new IronBar(), 10, new BeeHouse(), 3, 0);
//        rob.addQuestTemplate(new Wood(), 1000, null, 0, 1);
//        rob.addQuestTemplate(new Wood(), 0, new Gold(), 25000, 2);
//        list.add(rob);
//
        return list;
    }

    public NPC getNPCByName(String name) {
        return defaultOnes().getOrDefault(name, null);
    }
}
