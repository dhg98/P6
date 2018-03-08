package es.ucm.fdi.model;

import java.util.ArrayList;

public class NewVehicleEvent extends Event {

	private String id;
	private int maxSpeed;
	private ArrayList<String> itinerary;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, ArrayList<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.itinerary = itinerary;
	}
	
	@Override
	public void execute(RoadMap r) {
		ArrayList <Junction> its = new ArrayList<>();
		for (String i : itinerary) {
			its.add(r.getJunction(i));
		}
		for (Road ro : r.getJunction(itinerary.get(0)).getOutgoingRoadsList()) {
			if (ro.getEnd().getId().equals(r.getJunction(itinerary.get(1)))) {
				Vehicle v = new Vehicle(id, maxSpeed, 0, ro, 0, 0, its);
				r.addVehicle(v);
				ro.entraVehiculo(v);
				break;
			}
		}		
	}

}
