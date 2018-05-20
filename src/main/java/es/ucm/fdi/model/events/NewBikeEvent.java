package es.ucm.fdi.model.events;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.model.Bike;
import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.RoadMap;

public class NewBikeEvent extends NewVehicleEvent {

	public NewBikeEvent(int time, String id, int maxSpeed,
			List<String> itinerary) {
		super(time, id, maxSpeed, itinerary);
	}

	/**
	 * Executes a NewBikeEvent and adds it to the RoadMap
	 */
	@Override
	public void execute(RoadMap rm) {
		List<Junction> its = new ArrayList<>();
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
	
	@Override
	public String toString() {
		return "New Bike Event";
	}
}
