package es.ucm.fdi.model;

import java.util.*;

public class Vehicle extends SimObject {

	private int velMax;
	private int velAct;
	private Road road;
	private int location;
	private int kilometrage = 0;
	private List<Junction> itinerario; 
	private int contadorCruce;
	private int tiempoAveria;
	private boolean haLlegado;
	
	public Vehicle(String id, int velMaxima, Road road, List<Junction> itinerario){
		super(id);
		this.velMax = velMaxima;
		velAct = 0;
		location = 0;
		tiempoAveria = 0;
		this.itinerario = itinerario;
		haLlegado = itinerario.size() == 0;
		this.road = road;
		contadorCruce = 1;
	}
	
	public int getVelMax() {
		return velMax;
	}

	public int getVelAct() {
		return velAct;
	}

	public int getKilometrage() {
		return kilometrage;
	}

	public Road getRoad() {
		return road;
	}

	public void setRoad(Road road) {
		this.road = road;
	}

	public int getTiempoAveria() {
		return tiempoAveria;
	}
	
	public void setVelAct(int velAct) {
		this.velAct = velAct;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}
	
	protected void fillReportDetails(Map<String, String> out) {
		out.put("speed", Integer.toString(velAct));
		out.put("kilometrage", Integer.toString(kilometrage));
		out.put("faulty", Integer.toString(tiempoAveria));
		if(haLlegado) {
			out.put("location", "arrived");
		} else {
			out.put("location", "(" + road.getId() + "," + Integer.toString(location) + ")");
		}
	}
	
	protected String getReportHeader() {
		return "vehicle_report";
	}
	
	public void setTiempoAveria(int n){
		tiempoAveria += n;
	}

	public void setVelocidadActual(int velA) {
		if(velA >= velMax){
			velAct = velMax;
		} else {
			velAct = velA;
		}
	}
	
	public void avanza() {
		if(tiempoAveria > 0) {
			tiempoAveria--;
			if(tiempoAveria == 0) {
				getRoad().setNumFaultyVehicles(getRoad().getNumFaultyVehicles() - 1);
			}
		} else {
			int locationAux = location + velAct;
			if(locationAux >= road.getSize()) {
				kilometrage += road.getSize() - location;
				location = road.getSize();
				road.getEnd().entraVehiculo(this); //El vehiculo se introduce en la cola del cruce del final de la carretera.
				velAct = 0;
			} else {
				location = locationAux;
				kilometrage += velAct;
			}
		}
	}
	
	public void moverASiguienteCarretera(){
		road.saleVehiculo(this);
		velAct = 0;
		if(contadorCruce + 1 == itinerario.size()) {
			haLlegado = true;
		}
		else{
			int i;
			List<Road> out = itinerario.get(contadorCruce).getOutgoingRoadsList();
			for(i = 0; i < out.size(); ++i) {
				if(out.get(i).getEnd() == itinerario.get(contadorCruce +1)){
					out.get(i).entraVehiculo(this); //Mete el this en la carretera siguiente de acuerdo a su itinerario
					road = out.get(i); //Cambia el atributo road a la nueva carretera en la que se encuentra
					location = 0; //Establece su posicion a cero
					contadorCruce++;
				}
			}
			if(i == itinerario.get(contadorCruce).getOutgoingRoadsList().size()) {
				throw new RuntimeException("The itinerary followed by the vehicle whose id is " + getId()
						+ " is incorrect after the junction " + itinerario.get(contadorCruce).getId());
				}
		}
	}
}
