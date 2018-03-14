package es.ucm.fdi.model;

import java.util.*;

import es.ucm.fdi.util.MultiTreeMap;

public class Road extends SimObject {
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

	public void entraVehiculo(Vehicle v) {
		street.putValue(v.getLocation(), v);
		numVehicles++;
		
	}
	
	public void saleVehiculo(Vehicle v) {
		street.removeValue(v.getLocation(), v);
		numVehicles--;
	}
	
	public void avanza() {
		modificarVelBase();
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
	
	protected void fillReportDetails(Map<String, String> out) {
		List <String> vehicleList = new ArrayList<>();
		if(numVehicles > 0) {
			for(Vehicle v: street.innerValues()) {
				vehicleList.add("(" + v.getId() + "," + Integer.toString(v.getLocation()) + ")");
			}
		}
		out.put("state", String.join(",", vehicleList));
	}
	
	protected String getReportHeader() {
		return "road_report";
	}
	
	public void decreaseNumVehicle(){
		numVehicles--;
	}
	
	public int modificarVelBase(){
		return Math.min(maxVel, (maxVel / Math.max(numVehicles, 1)) + 1);
	}
}
