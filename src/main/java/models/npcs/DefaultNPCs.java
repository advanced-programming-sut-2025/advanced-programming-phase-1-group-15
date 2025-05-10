package models.npcs;

import models.App;
import models.map.Map;

import java.util.ArrayList;
import java.util.List;

public class DefaultNPCs {
    private static final DefaultNPCs defaultNPCs = new DefaultNPCs();


    public DefaultNPCs getInstance() {
        return defaultNPCs;
    }

    private DefaultNPCs() {
        //createAll()
    }

    public ArrayList<NPC> defaultOnes(){
        Map map = App.currentGame.getMap();
        ArrayList<NPC> list = new ArrayList<>();
        return null;
    }

}
