package es.ucm.fdi.model;

import java.util.Map;

public class RoundRobinJunction extends JunctionWithTimeSlice {

	private int maxTimeSlice;
	private int minTimeSlice;

	public RoundRobinJunction(String id, int maxTimeSlice, int minTimeSlice) {
		super(id);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice = minTimeSlice;
	}

	/**
	 * Advances the light of a RoundRobin Junction given the statement of the project.
	 */
	@Override
	public void advanceLight() {
		IncomingRoadWithTimeSlice irs = (IncomingRoadWithTimeSlice) getJunctionDeque().
				get(getTrafficLight());
		if (irs.timeSlice == irs.usedTimeUnits) {
			if (irs.fullyUsed) {
				irs.timeSlice = Math.min(maxTimeSlice, irs.timeSlice + 1);
			} else if (!irs.used) {
				irs.timeSlice = Math.max(minTimeSlice, irs.timeSlice - 1);
			}
			irs.usedTimeUnits = 0;
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

		// Al introducir una nueva carretera, se modifica el semaforo para que en el
		// siguiente tick, al aumentar su valor se aumente de forma correcta y se ponga
		// a 0, dejando pasar a la carretera que se agrego primero.

		setTrafficLight(getJunctionDeque().size() - 1);
	}
}
