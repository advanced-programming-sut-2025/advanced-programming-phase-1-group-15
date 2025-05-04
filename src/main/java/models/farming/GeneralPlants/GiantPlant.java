package models.farming.GeneralPlants;

import models.farming.Harvestable;
import models.map.Tilable;
import models.map.Tile;
import models.time.DateAndTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GiantPlant extends PloughedPlace implements Tilable {
    private List<Tile> parts;
    private int daysUntilHarvest; //?

    public GiantPlant(List<Tile> parts) {
        super();
        this.parts= parts;
        ArrayList<PloughedPlace> ploughedParts = new ArrayList<>();
        for(Tile part: parts) {
            ploughedParts.add((PloughedPlace) part.getObjectInTile());
        }
        applyStateIfPossible(new PloughedState(this));
        applyStateIfPossible(new SeededState(this));
        applyStateIfPossible(new FertilizedState(this));
        applyStateIfPossible(new WateredState(this));
        ArrayList<Harvestable> harvestables = new ArrayList<>();
        for(PloughedPlace p: ploughedParts) {
            harvestables.add(p.getHarvestable());
        }
        this.daysUntilHarvest = minOfList((ArrayList<Integer>)
                harvestables.stream().map(Harvestable::getDaysUntilHarvest).collect(Collectors.toList()));

        // TODO: days Until Harvest Calculation
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


    @Override
    public void update(DateAndTime dateAndTime) {
    }

    private static int minOfList(ArrayList<Integer> list) {
        int min = list.get(0);
        for(Integer i: list){
            min = Math.min(min, i);
        }
        return min;
    }

}
