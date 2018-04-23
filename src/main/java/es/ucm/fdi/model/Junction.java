package es.ucm.fdi.model;

import java.util.*;

/**
 * Represents a Junction in the Simulator
 * @author Daniel Herranz
 *
 */
public class Junction extends SimObject implements Describable {
	public static final String REPORT_HEADER = "junction_report";
	
	protected List<IncomingRoads> junctionDeque = new ArrayList<>();
	protected List<Road> outgoingRoadsList = new ArrayList<>();
	protected Map<Road, IncomingRoads> junctionMap = new HashMap<>();
	protected int trafficLight = 0;
	
	public int getTrafficLight() {
		return trafficLight;
	}

	public void setTrafficLight(int trafficLight) {
		this.trafficLight = trafficLight;
	}

	public Junction(String id) {
		super(id);
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

	/**
	 * Push a Vehicle in the Queue of the Road
	 * @param v
	 */
	public void entraVehiculo(Vehicle v){
		if (!junctionMap.get(v.getRoad()).roadDeque.contains(v)) {
			junctionMap.get(v.getRoad()).roadDeque.offerLast(v); //Metemos el vehiculo en la cola siempre que no este ya.
		}
	}
	
	/**
	 * Modifies the traffic Light
	 */
	public void advanceLight(){ 
		try {
			trafficLight = (trafficLight + 1) % junctionDeque.size();
		} catch (ArithmeticException e) {
			trafficLight = 0; //Desde donde se realiza esta llamada esta excepcion no se va a dar nunca, porque se comprueba que junctionDeque.size() != 0.
		}
	}
	
	public static class IncomingRoads {
		protected Deque<Vehicle> roadDeque;
		protected Road road;
		
		public IncomingRoads(Road road) {
			super();
			this.roadDeque = new ArrayDeque<>();
			this.road = road;
		}
		
		public Road getRoad() {
			return road;
		}

		public Deque<Vehicle> getRoadDeque() {
			return roadDeque;
		}
		
		public void avanzaVeh() {
			if (!roadDeque.isEmpty()) {
				roadDeque.pollFirst().moverASiguienteCarretera();
			}
		}
		
		public String junctionToString() {
			StringBuilder sb = new StringBuilder();
			
			for(Iterator<Vehicle> itr = roadDeque.iterator(); itr.hasNext();){
				sb.append(itr.next().getId());
				if (itr.hasNext()) {
					sb.append(",");
				}
			}
			return sb.toString();
		}
	}
	
	/**
	 * Report a Junction given the statement of the project
	 */
	protected void fillReportDetails(Map<String, String> out) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < junctionDeque.size(); ++i) {
			sb.append("(" + junctionDeque.get(i).road.getId() + ",");
			if(i == trafficLight) {
				sb.append("green,[");
			} else {
				sb.append("red,[");
			}
			sb.append(junctionDeque.get(i).junctionToString());
			
			if(i != junctionDeque.size() - 1) {
				sb.append("]),");
			} else {
				sb.append("])");
			}
		}
		out.put("queues", sb.toString());
	}
	
	public void getColorLights(StringBuilder red, StringBuilder green) {
		if (!junctionDeque.isEmpty()) {
			IncomingRoads greenLight = junctionDeque.get(trafficLight);
			for (IncomingRoads ir : junctionDeque) {
				if (greenLight == ir) {
					green.append("(" + ir.getRoad().getId() + ",green,[" + ir.junctionToString() + "])");
				} else {
					red.append("(" + ir.getRoad().getId() + ",red,[" + ir.junctionToString() + "]),");
				}
			}
			if (red.length() != 0) {
				red.deleteCharAt(red.length() - 1);
			}
		}
	}
	
	protected String getReportHeader() {
		return REPORT_HEADER;
	}
	
	/**
	 * Advance a Junction. It moves only the first car of the queue and advances the Traffic Light
	 */
	public void avanza() {
		if(!junctionDeque.isEmpty()) {
			junctionDeque.get(trafficLight).avanzaVeh();
			advanceLight();
		}
	}
	
	/**
	 * Adds a Incoming Road to the Junction
	 * @param r
	 */
	public void addIncomingRoad(Road r) {
		IncomingRoads ir = new IncomingRoads(r);
		junctionDeque.add(ir);
		junctionMap.put(r, ir);
		trafficLight = junctionDeque.size() - 1; //al introducir una nueva carretera, se modifica el semaforo para que en el siguiente tick, al aumentar su valor
		//se aumente de forma correcta y se ponga a 0, dejando pasar a la carretera que se agrego primero.
	}

	@Override
	public void describe(Map<String, String> out) {
		out.put("ID", getId());
		StringBuilder red = new StringBuilder(), green = new StringBuilder();
		getColorLights(red, green);
		out.put("Green", "[" + green.toString() + "]");
		out.put("Red", "[" + red.toString() + "]");
	}
}
