package es.ucm.fdi.model;

import java.util.*;

public class NewVehicleEvent extends Event implements Describable {

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
	 * Executes a NewVehicleEvent and adds the NewBike to the RoadMap
	 */
	@Override
	public void execute(RoadMap rm) {
		List <Junction> its = new ArrayList<>();
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
	public void describe(Map<String, String> out) {
		out.put("Time", "" + getTime());
		out.put("Type" , getType());
	}

}
