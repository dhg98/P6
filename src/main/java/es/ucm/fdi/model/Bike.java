package es.ucm.fdi.model;

import java.util.*;

import es.ucm.fdi.model.Vehicle;

/**
 * Represents a Bike given the statement of the project
 * @author Daniel Herranz
 *
 */
public class Bike extends Vehicle {
	
	public Bike(String id, int velMaxima, Road road, List<Junction> itinerario) {
		super(id, velMaxima, road, itinerario);
	}

	/**
	 * Modifies the faultTime given a number only if it is greater than the maxSpeed of the vehicle
	 */
	@Override
	public void setTiempoAveria(int n) {
		if (getVelAct() > getVelMax() / 2) {
			super.setTiempoAveria(n);
		}
	}

	/**
	 * Reports a Bike given the statement of the project.
	 */
	@Override
	protected void fillReportDetails(Map<String, String> out) {
		out.put("type", "bike");
		super.fillReportDetails(out);	
	}
}
