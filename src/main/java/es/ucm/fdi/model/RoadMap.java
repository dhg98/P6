package es.ucm.fdi.model;

import java.util.*;

public class RoadMap {
	private Map<String, SimObject> simObjects;
	private List<Junction> junctions = new ArrayList<>();
	private List<Road> roads = new ArrayList<>();
	private List<Vehicle> vehicles = new ArrayList<>();
	private List<Junction> junctionsRO;
	private List<Road> roadsRO;
	private List<Vehicle> vehiclesRO; 
	
	
	public Map<String, SimObject> getSimObjects() {
		return simObjects;
	}
	
	public RoadMap() {
		junctionsRO = Collections.unmodifiableList(junctions);
		roadsRO = Collections.unmodifiableList(roads);
		vehiclesRO = Collections.unmodifiableList(vehicles);
		simObjects = new HashMap<>();
	}
	
	public Vehicle getVehicle(String id) throws IllegalArgumentException {
		SimObject o = simObjects.get(id);
		if (o != null && o instanceof Vehicle) {
			return (Vehicle) o;
		}
		else {
			throw new IllegalArgumentException("The vehicle you are looking for doesn´t exist");
		}
	}

	public Junction getJunction(String id) throws IllegalArgumentException {
		SimObject o = simObjects.get(id);
		if (o != null && o instanceof Junction) {
			return (Junction) o;
		}
		else {
			throw new IllegalArgumentException("The junction you are looking for doesn´t exist");
		}
	}
	
	public Road getRoad(String id){
		SimObject o = simObjects.get(id);
		if (o != null && o instanceof Road) {
			return (Road) o;
		}
		else {
			throw new IllegalArgumentException("The road you are looking for doesn´t exist");
		}
	}
	
	public List<Junction> getJunctions(){
		return junctions;
	}
	
	public List<Vehicle> getVehicles(){
		return vehicles;
	}
	
	public List<Road> getRoads(){
		return roads;
	}
	
	public void addJunction(Junction j) throws IllegalArgumentException {
		if (simObjects.containsKey(j.getId())) {
			throw new IllegalArgumentException("Ya existe un objeto con este id");
		}
		else {
			simObjects.put(j.getId(), j);
			junctions.add(j);
		}		
	}
	
	public void addRoad(Road r) throws IllegalArgumentException {
		if (simObjects.containsKey(r.getId())) {
			throw new IllegalArgumentException("Ya existe un objeto con este id");
		}
		else {
			simObjects.put(r.getId(), r);
			roads.add(r);
		}	
	}
	
	public void addVehicle(Vehicle v) throws IllegalArgumentException {
		if (simObjects.containsKey(v.getId())) {
			throw new IllegalArgumentException("Ya existe un objeto con este id");
		}
		else {
			simObjects.put(v.getId(), v);
			vehicles.add(v);
		}	
	}
	
}
