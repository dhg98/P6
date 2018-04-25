package es.ucm.fdi.model;

import java.util.Map;

public class MostCrowdedJunction extends JunctionWithTimeSlice {

	public MostCrowdedJunction(String id) {
		super(id);
	}

	@Override
	public void advanceLight() {

		IncomingRoadWithTimeSlice irs = (IncomingRoadWithTimeSlice) getJunctionDeque().get(getTrafficLight());
		if (irs.timeSlice == irs.usedTimeUnits) {
			int maximo = -1, i = 0, posMax = 0;
			for (IncomingRoads itrs : getJunctionDeque()) {
				if (itrs.getRoadDeque().size() > maximo && i != getTrafficLight()) {
					maximo = itrs.getRoadDeque().size();
					posMax = i;
				}
				i++;
			}
			setTrafficLight(posMax);
			irs = (IncomingRoadWithTimeSlice) getJunctionDeque().get(posMax);
			irs.timeSlice = Math.max(maximo / 2, 1);
			irs.usedTimeUnits = 0;
		}
	}

	/**
	 * Reports a Most Crowded Junction given the statement of the project.
	 */
	@Override
	protected void fillReportDetails(Map<String, String> out) {
		super.fillReportDetails(out);
		out.put("type", "mc");
	}

	@Override
	public void addIncomingRoad(Road r) {
		IncomingRoadWithTimeSlice ir = new IncomingRoadWithTimeSlice(r, 0);
		getJunctionDeque().add(ir);
		getJunctionMap().put(r, ir);

		// al introducir una nueva carretera, se modifica el semaforo para que
		// en el siguiente tick, al aumentar su valor se aumente de forma correcta
		// y se ponga a 0, dejando pasar a la carretera que se agregó primero.
		
		setTrafficLight(getJunctionDeque().size() - 1);
	}
}
