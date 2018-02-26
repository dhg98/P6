package es.ucm.fdi.model;

import java.util.*;

public class Junction extends SimObject {
	private ArrayList<IncomingRoads> junctionDeque;
	private ArrayList<Road> outgoingRoadsList;
	private Map<Road, IncomingRoads> junctionMap;
	private int trafficLight = 0;
	
	public Junction(String id) {
		super(id);
		junctionDeque = new ArrayList<>();
		outgoingRoadsList = new ArrayList<>();
		junctionMap = new HashMap<>() ;
	}

	public ArrayList<Road> getOutgoingRoadsList() {
		return outgoingRoadsList;
	}

	public void entraVehiculo(Vehicle v){
		junctionMap.get(v.getRoad()).roadDeque.offerLast(v); //Metemos el vehiculo en la cola
	}
	
	public void advanceLight(){ 
		trafficLight = (trafficLight + 1) % junctionDeque.size();
	}
	
	private class IncomingRoads {
		 //boolean
		private ArrayDeque<Vehicle> roadDeque;
		private Road road;
		
		public IncomingRoads(Road road) {
			super();
			this.roadDeque = new ArrayDeque<>();
			this.road = road;
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
		out.put("state", aux);
	}
	
	protected String getReportHeader() {
		return "junction_report";
	}
	
	public void avanza() {
		
		if(!junctionDeque.get(trafficLight).roadDeque.isEmpty()) {
			junctionDeque.get(trafficLight).roadDeque.getFirst().moverASiguienteCarretera(); //movemos el vehiculo a la carretera en funcion de su itinerario
			junctionDeque.get(trafficLight).roadDeque.removeFirst(); //eliminar vehiculo de la cola
		}
		advanceLight();
	}
}
