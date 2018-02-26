package es.ucm.fdi.model;

import java.util.*;

import es.ucm.fdi.util.MultiTreeMap;

public class Road extends SimObject {
	private int size;
	private int maxVel;
	private int velBase;
	private int numVehicles = 0;
	private Junction start; //¿Atributo necesario?
	private Junction end;
	private MultiTreeMap<Integer, Vehicle> street;
	
	public Road(String id, int size, int maxVel, MultiTreeMap<Integer, Vehicle> street, Junction start, Junction end) {
		super(id);
		this.size = size;
		this.maxVel = maxVel;
		this.street = street;
		this.start = start;
		this.end = end;
		street = new MultiTreeMap<>((a,b) -> a-b);
	}
	
	public int getSize() {
		return size;
	}

	public Junction getEnd() {
		return end;
	}

	//Preguntar por entraVehiculo y saleVehiculo (cual es la posicion en la que se inserta y de cual es de la que se elimina)
	public void entraVehiculo(Vehicle v) {
		street.removeValue(v.getLocation(), v);
		/*ArrayList<Vehicle> aux = street.get(v.getLocation());
		aux.add(v);*/
		numVehicles++;
		//street.set(v.getLocation(), aux); Se supone que las referencias son iguales y deberia modificarlo sin necesitar el set
		
	}
	
	public void saleVehiculo(Vehicle v){
		street.putValue(v.getLocation(), v);
		/*ArrayList<Vehicle> aux = street.get(v.getLocation());
		aux.remove(v);*/
		numVehicles--;
	}
	
	public void avanza() {
		
		modificarVelBase();
		MultiTreeMap<Integer, Vehicle> actualizado = new MultiTreeMap<>((a,b) -> a-b);
		boolean averiado = false;
		
		for(Vehicle v: street.innerValues()){
			if(!averiado) {
				if(v.getTiempoAveria() > 0) {
					averiado = true;
				} else {
					v.setVelAct(velBase);
				}
			} else {
				v.setVelAct(velBase / 2);
			}
			v.avanza();
			actualizado.putValue(v.getLocation(), v);
		}
		street = actualizado;
	}
	
	protected void fillReportDetails(Map<String, String> out) {
		
		String aux = "";
		for(Vehicle v: street.innerValues()) {
			aux += "(" + v.getId() + "," + Integer.toString(v.getLocation()) + ")";
		}
		out.put("state", aux);
	}
	
	protected String getReportHeader() {
		return "road_report";
	}
	
	public void decreaseNumVehicle(){
		numVehicles--;
	}
	
	public void modificarVelBase(){
		velBase = Math.min(maxVel, (maxVel / Math.max(numVehicles, 1)) + 1);
	}
}
