package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.List;

public class NewBikeEvent extends NewVehicleEvent {

	public NewBikeEvent(int time, String id, int maxSpeed, List<String> itinerary) {
		super(time, id, maxSpeed, itinerary);
	}
	
	/**
	 * Executes a NewBikeEvent and adds the NewBike to the RoadMap
	 */
	@Override
	public void execute(RoadMap rm) {
		List <Junction> its = new ArrayList<>();
		for (String i : getItinerary()) {
			its.add(rm.getJunction(i));
		}
		for (Road ro : rm.getJunction(getItinerary().get(0)).getOutgoingRoadsList()) {
			if (ro.getEnd().getId().equals(rm.getJunction(getItinerary().get(1)).getId())) {
				Bike v = new Bike(getId(), getMaxSpeed(), ro, its);
				rm.addVehicle(v);
				ro.entraVehiculo(v);
				break;
			}
		}	

	}

}
