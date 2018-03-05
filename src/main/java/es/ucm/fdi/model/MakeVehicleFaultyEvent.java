package es.ucm.fdi.model;

import java.util.ArrayList;

public class MakeVehicleFaultyEvent extends Event {
	private int tiempoAveria;
	private ArrayList<String> itCoches; 
	
	public MakeVehicleFaultyEvent(int time, ArrayList<String> a, int duration) {
		super(time);
		tiempoAveria = duration;
		itCoches = a;
	}
	
	@Override
	public void execute(RoadMap r) {
		try{
			for (int i = 0; i < itCoches.size(); ++i) {
				r.getVehicle(itCoches.get(i)).setTiempoAveria(tiempoAveria);
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException ("The MakeVehicleFaultyEvent is incorrect", e);
		}
	}
}
