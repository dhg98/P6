package es.ucm.fdi.model;


public class NewMostCrowdedJunctionEvent extends NewJunctionEvent {

	public NewMostCrowdedJunctionEvent(int time, String id) {
		super(time, id);
	}
	
	@Override
	public void execute(RoadMap rm) {
		rm.addJunction(new MostCrowdedJunction(getId()));
	}

}
