package es.ucm.fdi.model;

import java.util.*;

public class NewCarEvent extends NewVehicleEvent {

	private int resistanceKm;
	private double faultProbability;
	private int maxFaultDuration;
	private long seed;
	
	public NewCarEvent(int time, String id, int maxSpeed,
			List<String> itinerary, int resistanceKm, double faultProbability,
			int maxFaultDuration, long seed) {
		super(time, id, maxSpeed, itinerary);
		this.resistanceKm = resistanceKm;
		this.faultProbability = faultProbability;
		this.maxFaultDuration = maxFaultDuration;
		this.seed = seed;
	}
	
	/**
	 * Executes a NewCarEvent and adds the NewBike to the RoadMap
	 */
	@Override
	public void execute(RoadMap rm) {
		List <Junction> its = new ArrayList<>();
		for (String i : getItinerary()) {
			its.add(rm.getJunction(i));
		}
		for (Road ro : rm.getJunction(getItinerary().get(0)).getOutgoingRoadsList()) {
			if (ro.getEnd().getId().equals(rm.getJunction(getItinerary().get(1)).getId())) {
				Car v = new Car(getId(), getMaxSpeed(), ro, its, resistanceKm, faultProbability, maxFaultDuration, seed);
				rm.addVehicle(v);
				ro.entraVehiculo(v);
				break;
			}
		}		
	}

}
