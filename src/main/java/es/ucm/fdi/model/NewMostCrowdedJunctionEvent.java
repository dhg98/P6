package es.ucm.fdi.model;


public class NewMostCrowdedJunctionEvent extends NewJunctionEvent {

	public NewMostCrowdedJunctionEvent(int time, String id) {
		super(time, id);
	}
	
	@Override
	public void execute(RoadMap r) {
		MostCrowdedJunction j = new MostCrowdedJunction(getId());
		r.addJunction(j);
	}

}
