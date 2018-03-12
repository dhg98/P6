package es.ucm.fdi.model;

import java.util.List;

public class NewBikeEvent extends NewVehicleEvent {

	public NewBikeEvent(int time, String id, int maxSpeed, List<String> itinerary) {
		super(time, id, maxSpeed, itinerary);
	}

	@Override
	public void execute(RoadMap r) {
		

	}

}
