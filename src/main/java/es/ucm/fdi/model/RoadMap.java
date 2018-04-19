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
	
	/**
	 * Get a Vehicle from the RoadMap
	 * @param id
	 * @return a vehicle if the id exists in the RoadMap or throws an Exception
	 * @throws IllegalArgumentException
	 */
	public Vehicle getVehicle(String id) {
		SimObject o = simObjects.get(id);
		if (o != null && o instanceof Vehicle) {
			return (Vehicle) o;
		}
		else {
			throw new IllegalArgumentException("The vehicle you are looking for doesn´t exist");
		}
	}

	/**
	 * Get a Junction from the RoadMap
	 * @param id
	 * @return a junction if the id exists in the RoadMap or throws an Exception
	 * @throws IllegalArgumentException
	 */
	public Junction getJunction(String id) {
		SimObject o = simObjects.get(id);
		if (o != null && o instanceof Junction) {
			return (Junction) o;
		}
		else {
			throw new IllegalArgumentException("The junction you are looking for doesn´t exist");
		}
	}
	
	/**
	 * Get a Road from the RoadMap
	 * @param id
	 * @return a road if the id exists in the RoadMap or throws an Exception
	 * @throws IllegalArgumentException
	 */
	public Road getRoad(String id) {
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
	
	public List<Junction> getJunctionsRO() {
		return junctionsRO;
	}

	public List<Road> getRoadsRO() {
		return roadsRO;
	}

	public List<Vehicle> getVehiclesRO() {
		return vehiclesRO;
	}

	/**
	 * Adds a Junction if the the id does not exist
	 * @param j
	 * @throws IllegalArgumentException
	 */
	public void addJunction(Junction j) {
		if (simObjects.containsKey(j.getId())) {
			throw new IllegalArgumentException("There is an object with this id.");
		}
		else {
			simObjects.put(j.getId(), j);
			junctions.add(j);
		}		
	}
	
	/**
	 * Adds a Road if the the id does not exist
	 * @param j
	 * @throws IllegalArgumentException
	 */
	public void addRoad(Road r) {
		if (simObjects.containsKey(r.getId())) {
			throw new IllegalArgumentException("There is an object with this id.");
		}
		else {
			simObjects.put(r.getId(), r);
			roads.add(r);
		}	
	}
	
	/**
	 * Adds a Vehicle if the the id does not exist
	 * @param j
	 * @throws IllegalArgumentException
	 */
	public void addVehicle(Vehicle v) {
		if (simObjects.containsKey(v.getId())) {
			throw new IllegalArgumentException("There is an object with this id.");
		}
		else {
			simObjects.put(v.getId(), v);
			vehicles.add(v);
		}	
	}
	
}
