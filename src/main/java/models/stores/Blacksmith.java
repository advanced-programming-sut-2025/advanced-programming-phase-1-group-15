package models.stores;

import models.foraging.ForagingMineralType;
import models.map.AreaType;
import models.map.Tile;
import models.time.DateAndTime;
import models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class Blacksmith extends Store {
    public static int[] coordinates = {47, 51, 40, 43};

    private HashMap<BlackSmithItems, Integer> Sold = new HashMap<>();

    public Blacksmith(ArrayList<ArrayList<Tile>> storeTiles) {
        runner = Runner.CLINT;
        opensAt = 9;
        closesAt = 16;

        this.areaType = AreaType.STORE;
        this.tiles = storeTiles;

        for(ArrayList<Tile> row : storeTiles) {
            for(Tile tile : row) {
                tile.setArea(this);
            }
        }

        for(BlackSmithItems item : BlackSmithItems.values()) {
            Sold.put(item, 0);
        }
    }

    @Override
    public void build() {

    }

    public void resetSoldItems() {
        Sold.replaceAll((i, v) -> 0);
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        if(dateAndTime.getHour() == opensAt) {
            resetSoldItems();
        }
    }

    @Override
    public String displayItems() {
        StringBuilder display = new StringBuilder();

        display.append("Name    Description    Price\n");
        for(BlackSmithItems item : BlackSmithItems.values()) {
            display.append(item.getName()).append("\t");
            display.append("\"").append(item.foragingMineralType.description).append("\"").append("\t");
            display.append(item.price).append("\n");
        }

        display.append("and different upgrades: \n");
        display.append("""
                Copper Tool\tCopper Bar(5)\t2,000g
                Steel Tool\tIron Bar(5)\t5,000g
                Gold Tool\tGold Bar(5)\t10,000g
                Iridium Tool\tIridium Bar(5)\t25,000g
                Copper Trash Can\tCopper Bar(5)\t1,000g
                Steel Trash Can\tIron Bar(5)\t2,500g
                Gold Trash Can\tGold Bar(5)\t5,000g
                Iridium Trash Can\tIridium Bar(5)\t12,500g""");

        return display.toString();
    }

    public void upgradeTool(Tool tool) {

    }
}
