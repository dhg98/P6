package es.ucm.fdi.model;

import java.util.*;

public class NewVehicleEvent extends Event {

	private String id;
	private int maxSpeed;
	private List<String> itinerary;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, List<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.itinerary = itinerary;
	}
	
	@Override
	public void execute(RoadMap r) {
		List <Junction> its = new ArrayList<>();
		for (String i : itinerary) {
			its.add(r.getJunction(i));
		}
		for (Road ro : r.getJunction(itinerary.get(0)).getOutgoingRoadsList()) {
			if (ro.getEnd().getId().equals(r.getJunction(itinerary.get(1)))) {
				Vehicle v = new Vehicle(id, maxSpeed, ro, its);
				r.addVehicle(v);
				ro.entraVehiculo(v);
				break;
			}
		}		
	}

}
