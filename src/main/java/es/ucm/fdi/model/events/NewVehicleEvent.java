package es.ucm.fdi.model.events;

import java.util.*;

import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.Vehicle;

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

	public String getId() {
		return id;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public List<String> getItinerary() {
		return itinerary;
	}

	/**
	 * Executes a NewVehicleEvent and adds it to the RoadMap
	 */
	@Override
	public void execute(RoadMap rm) {
		List<Junction> its = new ArrayList<>();
		for (String i : itinerary) {
			its.add(rm.getJunction(i));
		}
		for (Road ro : rm.getJunction(itinerary.get(0)).getOutgoingRoadsList()) {
			if (ro.getEnd().getId().equals(rm.getJunction(itinerary.get(1)).getId())) {
				Vehicle v = new Vehicle(id, maxSpeed, ro, its);
				rm.addVehicle(v);
				ro.entraVehiculo(v);
				break;
			}
		}
	}

	@Override
	public String getType() {
		return "New Vehicle " + id;
	}
	
	@Override
	public String toString() {
		return "New Vehicle Event";
	}

}
