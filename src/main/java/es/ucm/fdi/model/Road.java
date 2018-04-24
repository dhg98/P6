package es.ucm.fdi.model;

import java.util.*;

import es.ucm.fdi.util.MultiTreeMap;

/**
 * Represents a Road in the Simulator
 * @author Daniel Herranz
 *
 */
public class Road extends SimObject implements Describable {
	public static final String REPORT_HEADER = "road_report";
	
	private int size;
	private int maxVel;
	private int numVehicles = 0;
	private Junction start; //¿Atributo necesario?
	private Junction end;
	private MultiTreeMap<Integer, Vehicle> street  = new MultiTreeMap<>(Collections.reverseOrder());
	
	public Road(String id, int size, int maxVel, Junction start, Junction end) {
		super(id);
		this.size = size;
		this.maxVel = maxVel;
		this.start = start;
		this.end = end;
	}
	
	public MultiTreeMap<Integer, Vehicle> getStreet() {
		return street;
	}

	public void setStreet(MultiTreeMap<Integer, Vehicle> street) {
		this.street = street;
	}

	public int getMaxVel() {
		return maxVel;
	}

	public int getNumVehicles() {
		return numVehicles;
	}

	public int getSize() {
		return size;
	}

	public Junction getEnd() {
		return end;
	}

	public Junction getStart() {
		return start;
	}

	/**
	 * Puts a vehicle int the Road at the position number 0.
	 * @param v
	 */
	public void entraVehiculo(Vehicle v) {
		street.putValue(0, v);
		numVehicles++;
		
	}
	
	/**
	 * Removes a vehicle from the Road
	 * @param v
	 */
	public void saleVehiculo(Vehicle v) {
		street.removeValue(v.getLocation(), v);
		numVehicles--;
	}
	
	/**
	 * Advances a Road given the statement of the project
	 */
	public void avanza() {
		MultiTreeMap<Integer, Vehicle> actualizado = new MultiTreeMap<>(Collections.reverseOrder());
		boolean averiado = false;
		int velBase = modificarVelBase();
		for(Vehicle v: street.innerValues()){
			if(!averiado) {
				if(v.getTiempoAveria() > 0) {
					averiado = true;
				} else {
					v.setVelocidadActual(velBase);
				}
			} else {
				if (v.getTiempoAveria() > 0) {
					v.setVelAct(0);
				} else {
					v.setVelocidadActual(velBase / 2);
				}
			}
			v.avanza();
			actualizado.putValue(v.getLocation(), v);
		}
		street = actualizado;
	}
	
	/**
	 * Reports a Road given the statement of the project
	 */
	protected void fillReportDetails(Map<String, String> out) {
		out.put("state", toStringRoad());
	}
	
	public String toStringRoad() {
		List <String> vehicleList = new ArrayList<>();
		if(numVehicles > 0) {
			for(Vehicle v: street.innerValues()) {
				vehicleList.add("(" + v.getId() + "," + Integer.toString(v.getLocation()) + ")");
			}
		}
		return String.join(",", vehicleList);
	}
	
	public String toStringVehicles() {
		List <String> vehicleList = new ArrayList<>();
		for(Vehicle v: street.innerValues()) {
			vehicleList.add(v.getId());
		}
		return "[" + String.join(",", vehicleList) + "]";
	}
	
	protected String getReportHeader() {
		return REPORT_HEADER;
	}
	
	/**
	 * Modifies the Base velocity of the Road given the statement of the project
	 * @return
	 */
	public int modificarVelBase(){
		return Math.min(maxVel, (maxVel / Math.max(numVehicles, 1)) + 1);
	}

	@Override
	public void describe(Map<String, String> out) {
		out.put("ID", getId());
		out.put("Source", start.getId());
		out.put("Target", end.getId());
		out.put("Lenght", "" + size);
		out.put("Max Speed", "" + maxVel);
		out.put("Vehicles", toStringVehicles());
	}
}
