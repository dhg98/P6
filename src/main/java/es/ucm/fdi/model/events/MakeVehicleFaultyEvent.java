package es.ucm.fdi.model.events;

import java.util.*;

import es.ucm.fdi.model.Describable;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.Vehicle;

public class MakeVehicleFaultyEvent extends Event implements Describable {
	private int tiempoAveria;
	private List<String> itCoches; 
	
	public MakeVehicleFaultyEvent(int time, List<String> a, int duration) {
		super(time);
		tiempoAveria = duration;
		itCoches = a;
	}
	
	/**
	 * Executes a NewMakeVehicleFaultyEvent faults a vehicle
	 */
	@Override
	public void execute(RoadMap rm) {
		try{
			for (int i = 0; i < itCoches.size(); ++i) {
				Vehicle v = rm.getVehicle(itCoches.get(i));
				v.setTiempoAveria(tiempoAveria);
				v.setVelAct(0);
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException ("The MakeVehicleFaultyEvent is incorrect", e);
		}
	}

	@Override
	public String getType() {
		List <String> idsList = new ArrayList<>();
		for (String id : itCoches) {
			idsList.add(id);
		}
		return "Break Vehicles [" + String.join(",", idsList) + "]";
	}

	@Override
	public void describe(Map<String, String> out) {
		out.put("Time", "" + getTime());
		out.put("Type", getType());
	}
}
