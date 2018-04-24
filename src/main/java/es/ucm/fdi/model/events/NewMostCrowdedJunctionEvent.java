package es.ucm.fdi.model.events;

import es.ucm.fdi.model.MostCrowdedJunction;
import es.ucm.fdi.model.RoadMap;

public class NewMostCrowdedJunctionEvent extends NewJunctionEvent {

	public NewMostCrowdedJunctionEvent(int time, String id) {
		super(time, id);
	}
	
	@Override
	public void execute(RoadMap rm) {
		rm.addJunction(new MostCrowdedJunction(getId()));
	}

}
