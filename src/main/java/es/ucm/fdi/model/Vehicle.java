package es.ucm.fdi.model;

import java.util.*;

/**
 * Represents a Vehicle in the Simulator
 * 
 * @author Daniel Herranz
 *
 */
public class Vehicle extends SimObject implements Describable {
	public static final String REPORT_HEADER = "vehicle_report";

	private int velMax;
	private int velAct;
	private Road road;
	private int location;
	private int kilometrage = 0;
	private List<Junction> itinerario;
	private int contadorCruce;
	private int tiempoAveria;
	private boolean haLlegado;

	public Vehicle(String id, int velMaxima, Road road, List<Junction> itinerario) {
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

	public boolean getHaLlegado() {
		return haLlegado;
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

	/**
	 * Report of a Vehicle given the statement of the project
	 */
	protected void fillReportDetails(Map<String, String> out) {
		out.put("speed", Integer.toString(velAct));
		out.put("kilometrage", Integer.toString(kilometrage));
		out.put("faulty", Integer.toString(tiempoAveria));
		if (haLlegado) {
			out.put("location", "arrived");
		} else {
			out.put("location", "(" + road.getId() + "," + Integer.toString(location) + ")");
		}
	}

	public String toStringItinerary() {
		List<String> itinerary = new ArrayList<>();
		for (Junction j : itinerario) {
			itinerary.add(j.getId());
		}
		return "[" + String.join(",", itinerary) + "]";
	}

	protected String getReportHeader() {
		return REPORT_HEADER;
	}

	/**
	 * Adds n steps to the atribute tiempoAveria.
	 * 
	 * @param n
	 */
	public void setTiempoAveria(int n) {
		tiempoAveria += n;
	}

	/**
	 * Modifies the speed of the car if it is less than the maxSpeed.
	 * 
	 * @param velA
	 */
	public void setVelocidadActual(int velA) {
		if (velA >= velMax) {
			velAct = velMax;
		} else {
			velAct = velA;
		}
	}

	/**
	 * Advance a vehicle given the statement of the project.
	 */
	public void avanza() {
		if (tiempoAveria > 0) {
			tiempoAveria--;
			velAct = 0;
		} else {
			int locationAux = location + velAct;
			if (locationAux >= road.getSize()) {
				kilometrage += road.getSize() - location;
				location = road.getSize();
				// El vehiculo se introduce en la cola del cruce del final de la carretera.
				road.getEnd().entraVehiculo(this); 
				velAct = 0;
			} else {
				location = locationAux;
				kilometrage += velAct;
			}
		}
	}

	/**
	 * Move a vehicle to the next road given his itinerary.
	 */
	public void moverASiguienteCarretera() {
		road.saleVehiculo(this);
		velAct = 0;
		if (contadorCruce + 1 == itinerario.size()) {
			haLlegado = true;
		} else {
			int i;
			List<Road> out = itinerario.get(contadorCruce).getOutgoingRoadsList();
			for (i = 0; i < out.size(); ++i) {
				if (out.get(i).getEnd() == itinerario.get(contadorCruce + 1)) {
					// Mete el this en la carretera siguiente de acuerdo a su itinerario
					out.get(i).entraVehiculo(this); 
					// Cambia el atributo road a la nueva carretera en la que se encuentra
					road = out.get(i); 
					// Establece su posicion a cero
					location = 0; 
					contadorCruce++;
					break;
				}
			}
			if (i == out.size()) {
				throw new IllegalStateException("The itinerary followed by the vehicle"
						+ "whose id is " + getId() + " is incorrect after the junction "
						+ itinerario.get(contadorCruce).getId());
			}
		}
	}

	/**
	 * To describe a vehicle, we have to put inside the map the id,
	 * the road this vehicle is in, the location in the road,
	 * the speed in the road, the total kilometrage, the faulty units
	 * (if there are any) and the itinerary the vehicle is following.
	 */
	@Override
	public void describe(Map<String, String> out) {
		out.put("ID", getId());
		out.put("Road", road.getId());
		out.put("Location", "" + location);
		out.put("Speed", "" + velAct);
		out.put("Km", "" + kilometrage);
		out.put("Faulty Units", "" + tiempoAveria);
		out.put("Itinerary", toStringItinerary());
	}
}
