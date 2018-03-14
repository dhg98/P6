package es.ucm.fdi.model;

import java.util.*;

import es.ucm.fdi.model.Vehicle;

public class Bike extends Vehicle {
	
	public Bike(String id, int velMaxima, Road road, List<Junction> itinerario) {
		super(id, velMaxima, road, itinerario);
	}

	@Override
	public void setTiempoAveria(int n) {
		if (getVelAct() > getVelMax() / 2) {
			super.setTiempoAveria(n);
		}
	}

	@Override
	protected void fillReportDetails(Map<String, String> out) {
		out.put("type", "bike");
		super.fillReportDetails(out);	
	}
	
	

}
