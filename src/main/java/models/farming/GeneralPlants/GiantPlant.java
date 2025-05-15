package models.farming.GeneralPlants;

import models.artisanry.ArtisanItem;
import models.artisanry.ArtisanItemType;
import models.farming.Harvestable;
import models.farming.Tree;
import models.map.AreaType;
import models.map.Tilable;
import models.map.Tile;
import models.time.DateAndTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GiantPlant extends PloughedPlace implements Tilable {
    private List<Tile> parts;
    int daysUntilHarvest = 0;

    public GiantPlant(List<Tile> parts) {
        super();
        this.parts= parts;
        ArrayList<PloughedPlace> ploughedParts = new ArrayList<>();
        for(Tile part: parts) {
            ploughedParts.add((PloughedPlace) part.getObjectInTile());
        }
        applyStateIfPossible(new PloughedState(this));
        applyStateIfPossible(new SeededState(this));
        applyStateIfPossible(new WateredState(this));
        ArrayList<Harvestable> harvestables = new ArrayList<>();
        for(PloughedPlace p: ploughedParts) {
            harvestables.add(p.getHarvestable());
        }
        this.daysUntilHarvest = minOfList((ArrayList<Integer>)
                harvestables.stream().map(Harvestable::getDaysUntilHarvest).collect(Collectors.toList()));

    }

    public void applyStateIfPossible(PlantState state) {
        for(Tile part: parts){
            PloughedPlace place = (PloughedPlace) part.getObjectInTile();
            if(state.getClass() == PloughedState.class){
                this.setState(new PloughedState(this));
                return;
            }
        }
    }

    public List<Tile> getParts() {
        return parts;
    }

    @Override
    public void update(DateAndTime dateAndTime) {
        for (Tile t : parts) {
            if (t.getObjectInTile() instanceof PloughedPlace pp && pp.getHarvestable() != null) {
                pp.getHarvestable().update(dateAndTime);
            }
        }
    }

    @Override
    public void unPlough() {
        for (Tile t : parts) {
            t.unplow();
        }
    }

    @Override
    public void thor() {
        for (Tile t : parts) {
            if (t.getAreaType() == AreaType.GREENHOUSE) continue;
            t.unplow();
            if (this.harvestable instanceof Tree) {
                t.setObjectInTile(new ArtisanItem(ArtisanItemType.COAL));
            }
        }
    }



    private static int minOfList(ArrayList<Integer> list) {
        int min = list.get(0);
        for(Integer i: list){
            min = Math.min(min, i);
        }
        return min;
    }

    @Override
    public void harvest() {
        if (harvestable != null) {
            harvestable.harvest(10);
        }
    }

    @Override
    public String printInfo() {
        if (harvestable == null) {
            return "Empty giant plant.";
        }
        int days = parts.stream()
                .map(t -> ((PloughedPlace) t.getObjectInTile()).getHarvestable().getDaysUntilHarvest())
                .min(Integer::compareTo)
                .orElse(0);
        return String.format(
                "Giant Plant: %s\nDays Until Harvest (min): %d\nCurrent State: %s\nWatered today: %s\nFertilized: %s",
                harvestable.getName(),
                days,
                getCurrentState().getClass().getSimpleName(),
                parts.stream().allMatch(Tile::isWatered) ? "Yes" : "No",
                fertilizer != null ? "Yes" : "No"
        );
    }

}
