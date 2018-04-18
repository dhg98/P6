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
			if(irs.fullyUsed) {
				irs.setTimeSlice(Math.min(maxTimeSlice, irs.getTimeSlice() + 1));
			} else if(!irs.used) {
				irs.setTimeSlice(Math.max(minTimeSlice, irs.getTimeSlice() - 1));
			}
			irs.setUsedTimeUnits(0);	
			super.advanceLight();
		}
	}
	
	/**
	 * Report a Junction given the statement of the project
	 */
	protected void fillReportDetails(Map<String, String> out) {
		super.fillReportDetails(out);
		out.put("type", "rr");
	}
	
	@Override
	public void addIncomingRoad(Road r) {
		IncomingRoadWithTimeSlice ir = new IncomingRoadWithTimeSlice(r, maxTimeSlice);
		getJunctionDeque().add(ir);
		getJunctionMap().put(r, ir);
		setTrafficLight(getJunctionDeque().size() - 1); //al introducir una nueva carretera, se modifica el semaforo para que en el siguiente tick, al aumentar su valor
		//se aumente de forma correcta y se ponga a 0, dejando pasar a la carretera que se agrego primero.
	}
}
