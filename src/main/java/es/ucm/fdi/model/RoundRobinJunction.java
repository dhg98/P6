package es.ucm.fdi.model;

import java.util.Iterator;
import java.util.Map;

import es.ucm.fdi.model.Junction.IncomingRoads;

public class RoundRobinJunction extends JunctionWithTimeSlice {
	
	private int maxTimeSlice;
	private int minTimeSlice;
	
	public RoundRobinJunction(String id, int maxTimeSlice, int minTimeSlice) {
		super(id);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice = minTimeSlice;
	}
	
	@Override
	public void advanceLight() {
		IncomingRoadWithTimeSlice irs = (IncomingRoadWithTimeSlice)getJunctionDeque().get(getTrafficLight());
		if(irs.getTimeSlice() == irs.getUsedTimeUnits()) {
	
			if(irs.isUsed()) {
				irs.setTimeSlice(Math.min(maxTimeSlice, irs.getTimeSlice() + 1));
			} else if(irs.getNumVehicles() == 0) {
				irs.setTimeSlice(Math.max(minTimeSlice, irs.getTimeSlice() - 1));
			}
			
			irs.setUsedTimeUnits(0);	
			
			try {	
				setTrafficLight((getTrafficLight() + 1) % getJunctionDeque().size());
			} catch (ArithmeticException e) {
				setTrafficLight(0); //Desde donde se realiza esta llamada esta excepcion no se va a dar nunca, porque se comprueba que junctionDeque.size() != 0.
			}
		} else {
			irs.setUsedTimeUnits(irs.getUsedTimeUnits()+ 1);
		}
		
	}
	
	/**
	 * Report a Junction given the statement of the project
	 */
	protected void fillReportDetails(Map<String, String> out) {
		
		String aux = "";
		for(int i = 0; i < getJunctionDeque().size(); ++i){
			
			IncomingRoadWithTimeSlice irs = (IncomingRoadWithTimeSlice)getJunctionDeque().get(i);
			if(i == getTrafficLight()) {
				aux += "(" + irs.getRoad().getId() + ",green:" + (irs.getTimeSlice() - irs.getUsedTimeUnits()) + ",[";
			} else {
				aux += "(" + irs.getRoad().getId() + ",red,[";
			}
			
			for(Iterator<Vehicle> itr = irs.getRoadDeque().iterator(); itr.hasNext();){
				aux += itr.next().getId();
				if (itr.hasNext()) {
					aux += ",";
				}
			}
			if(i != getJunctionDeque().size() - 1) {
				aux += "]),";
			} else {
				aux += "])";
			}
		}
		out.put("queues", aux);
		out.put("type", "rr");
	}
	
	@Override
	public void addIncomingRoad(Road r) {
		IncomingRoadWithTimeSlice ir = new IncomingRoadWithTimeSlice(r, maxTimeSlice , 0, 0);
		getJunctionDeque().add(ir);
		getJunctionMap().put(r, ir);
		setTrafficLight(getJunctionDeque().size() - 1); //al introducir una nueva carretera, se modifica el semaforo para que en el siguiente tick, al aumentar su valor
		//se aumente de forma correcta y se ponga a 0, dejando pasar a la carretera que se agrego primero.
	}
}
