package es.ucm.fdi.model;

import java.util.*;

public class Junction extends SimObject {
	private List<IncomingRoads> junctionDeque;
	private List<Road> outgoingRoadsList;
	private Map<Road, IncomingRoads> junctionMap;
	private int trafficLight = -1;
	
	public Junction(String id) {
		super(id);
		junctionDeque = new ArrayList<>();
		outgoingRoadsList = new ArrayList<>();
		junctionMap = new HashMap<>() ;
	}
	
	public List<IncomingRoads> getJunctionDeque() {
		return junctionDeque;
	}

	public Map<Road, IncomingRoads> getJunctionMap() {
		return junctionMap;
	}

	public List<Road> getOutgoingRoadsList() {
		return outgoingRoadsList;
	}

	public void entraVehiculo(Vehicle v){
		if (!junctionMap.get(v.getRoad()).roadDeque.contains(v)) {
		junctionMap.get(v.getRoad()).roadDeque.offerLast(v); //Metemos el vehiculo en la cola
		}
	}
	
	public void advanceLight(){ 
		try {
			trafficLight = (trafficLight + 1) % junctionDeque.size();
		} catch (ArithmeticException e) {
			trafficLight = -1; //si el numero de carreteras entrantes es 0, se mantiene a -1 el semaforo.
		}
	}
	
	public static class IncomingRoads {
		 //boolean
		private Deque<Vehicle> roadDeque;
		private Road road;
		
		public IncomingRoads(Road road) {
			super();
			this.roadDeque = new ArrayDeque<>();
			this.road = road;
		}

		public Deque<Vehicle> getRoadDeque() {
			return roadDeque;
		}
		
	}
	
	protected void fillReportDetails(Map<String, String> out) {
		
		String aux = "";
		for(int i = 0; i < junctionDeque.size(); ++i){
			if(i == trafficLight) {
				aux += "(" + junctionDeque.get(i).road.getId() + ",green,[";
			} else {
				aux += "(" + junctionDeque.get(i).road.getId() + ",red,[";
			}
			
			for(Iterator<Vehicle> itr = junctionDeque.get(i).roadDeque.iterator(); itr.hasNext();){
				aux += itr.next().getId();
				if (itr.hasNext()) {
					aux += ",";
				}
			}
			if(i != junctionDeque.size() - 1) {
				aux += "]),";
			} else {
				aux += "])";
			}
		}
		out.put("queues", aux);
	}
	
	protected String getReportHeader() {
		return "junction_report";
	}
	
	public void avanza() {
		if(trafficLight != -1 && !junctionDeque.isEmpty() &&
				!junctionDeque.get(trafficLight ).roadDeque.isEmpty()) {
			//el array de incomingRoads no este vacio y la cola que indica el semaforo tampoco
			junctionDeque.get(trafficLight).roadDeque.getFirst().moverASiguienteCarretera(); //movemos el vehiculo a la carretera en funcion de su itinerario
			junctionDeque.get(trafficLight).roadDeque.removeFirst(); //eliminar vehiculo de la cola
		}
		advanceLight();
	}
}
