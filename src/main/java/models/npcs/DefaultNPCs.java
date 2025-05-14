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
    }

    public HashMap<String,NPC> defaultOnes = makeDefaultOnes();

    public HashMap<String, NPC> getDefaultOnes() {
        return defaultOnes;
    }

    public HashMap<String, NPC> makeDefaultOnes() {
        Map gameMap = App.currentGame.getMap();
        HashMap<String, NPC> list = new HashMap<>();

        // Sebastian
        NPC seb = new NPC("Sebastian", "Programmer", gameMap.getTile(new Position(25, 27)));
        seb.favourites.add(new Fish(FishType.MIDNIGHT_CARP));
        seb.favourites.add(new Fish(FishType.GLACIER_FISH));
        seb.favourites.add(new Fish(FishType.SQUID));
        seb.addQuestTemplate(new Fish(FishType.MIDNIGHT_CARP), 2, new Fish(FishType.SQUID), 1, 0);
        seb.addQuestTemplate(new Fish(FishType.GLACIER_FISH), 3, new Fish(FishType.TUNA), 2, 1);
        seb.addQuestTemplate(new Fish(FishType.SQUID), 5, new Fish(FishType.FLOUNDER), 1, 2);
        list.put(seb.getName(), seb);

        // Abigail
        NPC abi = new NPC("Abigail", "Adventurer", gameMap.getTile(new Position(25, 25)));
        abi.favourites.add(new Fish(FishType.FLOUNDER));
        abi.favourites.add(new Fish(FishType.HERRING));
        abi.favourites.add(new Fish(FishType.SUNFISH));
        abi.addQuestTemplate(new Fish(FishType.FLOUNDER), 1, new Fish(FishType.HERRING), 2, 0);
        abi.addQuestTemplate(new Fish(FishType.HERRING), 4, new Fish(FishType.RAINBOW_TROUT), 1, 1);
        abi.addQuestTemplate(new Fish(FishType.SUNFISH), 6, new Fish(FishType.DORADO), 1, 2);
        list.put(abi.getName(), abi);

        // Harvey
        NPC harv = new NPC("Harvey", "Doctor", gameMap.getTile(new Position(27, 25)));
        harv.favourites.add(new Fish(FishType.SARDINE));
        harv.favourites.add(new Fish(FishType.SHAD));
        harv.favourites.add(new Fish(FishType.SQUID));
        harv.addQuestTemplate(new Fish(FishType.SARDINE), 1, new Fish(FishType.SQUID), 2, 0);
        harv.addQuestTemplate(new Fish(FishType.SHAD), 5, new Fish(FishType.TUNA), 1, 1);
        harv.addQuestTemplate(new Fish(FishType.TUNA), 10, new Fish(FishType.FLOUNDER), 3, 2);
        list.put(harv.getName(), harv);

        // Leah
        NPC lea = new NPC("Leah", "Artist", gameMap.getTile(new Position(27, 27)));
        lea.favourites.add(new Fish(FishType.LIONFISH));
        lea.favourites.add(new Fish(FishType.GHOST_FISH));
        lea.favourites.add(new Fish(FishType.HERRING));
        lea.addQuestTemplate(new Fish(FishType.HERRING), 3, new Fish(FishType.GHOST_FISH), 1, 0);
        lea.addQuestTemplate(new Fish(FishType.LIONFISH), 2, new Fish(FishType.RAINBOW_TROUT), 2, 1);
        lea.addQuestTemplate(new Fish(FishType.RAINBOW_TROUT), 5, new Fish(FishType.LEGEND), 1, 2);
        list.put(lea.getName(), lea);

        // Robin
        NPC rob = new NPC("Robin", "Carpenter", gameMap.getTile(new Position(23, 27)));
        rob.favourites.add(new Fish(FishType.TILAPIA));
        rob.favourites.add(new Fish(FishType.SUNFISH));
        rob.favourites.add(new Fish(FishType.DORADO));
        rob.addQuestTemplate(new Fish(FishType.TILAPIA), 4, new Fish(FishType.PERCH), 3, 0);
        rob.addQuestTemplate(new Fish(FishType.SUNFISH), 2, new Fish(FishType.GLACIER_FISH), 1, 1);
        rob.addQuestTemplate(new Fish(FishType.DORADO), 6, new Fish(FishType.ANGLER), 1, 2);
        list.put(rob.getName(), rob);

        return list;
    }

    public NPC getNPCByName(String name) {
        return defaultOnes.getOrDefault(name, null);
    }
}
