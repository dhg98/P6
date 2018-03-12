package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.List;

public class NewBikeEvent extends NewVehicleEvent {

	public NewBikeEvent(int time, String id, int maxSpeed, List<String> itinerary) {
		super(time, id, maxSpeed, itinerary);
	}

	@Override
	public void execute(RoadMap r) {
		List <Junction> its = new ArrayList<>();
		for (String i : getItinerary()) {
			its.add(r.getJunction(i));
		}
		for (Road ro : r.getJunction(getItinerary().get(0)).getOutgoingRoadsList()) {
			if (ro.getEnd().getId().equals(r.getJunction(getItinerary().get(1)).getId())) {
				Bike v = new Bike(getId(), getMaxSpeed(), ro, its);
				r.addVehicle(v);
				ro.entraVehiculo(v);
				break;
			}
		}	

	}

}
