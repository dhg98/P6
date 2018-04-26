package es.ucm.fdi.model;

import java.util.Map;

public class JunctionWithTimeSlice extends Junction {

	public JunctionWithTimeSlice(String id) {
		super(id);
	}

	protected class IncomingRoadWithTimeSlice extends IncomingRoads {

		protected int timeSlice;
		//De no inicializarlo a -1, irian un ciclo por delante
		protected int usedTimeUnits = -1;
		protected boolean used = false;
		protected boolean fullyUsed = true;

		public IncomingRoadWithTimeSlice(Road road, int timeSlice) {
			super(road);
			this.timeSlice = timeSlice;
		}

		/**
		 * Advances a vehicle if there is any.
		 */
		@Override
		public void avanzaVeh() {
			//Si no se ha completado el intervalo de tiempo, avanzamos el coche si lo 
			//hay y modificamos los booleanos used y fullyUsed de acuerdo a esto.
			if (usedTimeUnits < timeSlice) {
				if (!roadDeque.isEmpty()) {
					roadDeque.pollFirst().moverASiguienteCarretera();
					used = true;
				} else {
					fullyUsed = false;
				}
				usedTimeUnits++;
			}
		}
	}

	/**
	 * Report a Junction given the statement of the project
	 */
	protected void fillReportDetails(Map<String, String> out) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < junctionDeque.size(); ++i) {
			IncomingRoadWithTimeSlice irs =
					(IncomingRoadWithTimeSlice) getJunctionDeque().get(i);
			sb.append("(" + irs.road.getId() + ",");
			if (i == trafficLight) {
				sb.append("green:" + (irs.timeSlice - irs.usedTimeUnits) + ",[");
			} else {
				sb.append("red,[");
			}
			sb.append(junctionDeque.get(i).junctionToString());

			if (i != junctionDeque.size() - 1) {
				sb.append("]),");
			} else {
				sb.append("])");
			}
		}
		out.put("queues", sb.toString());
	}
}
