package models.farming.GeneralPlants;

import models.map.Tilable;
import models.map.Tile;
import models.time.DateAndTime;

import java.util.ArrayList;
import java.util.List;

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

}
