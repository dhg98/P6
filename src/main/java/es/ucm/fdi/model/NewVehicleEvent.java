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
		//Vehicle v = Vehicle(id, maxSpeed, 0, , 0, 0, itinerary);
		
	}

}
