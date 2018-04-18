package es.ucm.fdi.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;

public class JunctionWithTimeSlice extends Junction {
	
	public JunctionWithTimeSlice(String id) {
		super(id);
	}

	protected class IncomingRoadWithTimeSlice extends IncomingRoads {
		
		private int timeSlice;
		private int usedTimeUnits = -1;
		protected boolean used = false;
		protected boolean fullyUsed = true;
		
		public IncomingRoadWithTimeSlice(Road road, int timeSlice) {
			super(road);
			this.timeSlice = timeSlice;
		}
		
		@Override
		public void avanzaVeh() {
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

		public void setTimeSlice(int timeSlice) {
			this.timeSlice = timeSlice;
		}

		public void setUsedTimeUnits(int usedTimeUnits) {
			this.usedTimeUnits = usedTimeUnits;
		}

		public int getTimeSlice() {
			return timeSlice;
		}

		public int getUsedTimeUnits() {
			return usedTimeUnits;
		}		
	}
	
	/**
	 * Report a Junction given the statement of the project
	 */
	protected void fillReportDetails(Map<String, String> out) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < junctionDeque.size(); ++i) {
			IncomingRoadWithTimeSlice irs = (IncomingRoadWithTimeSlice)getJunctionDeque().get(i);
			sb.append("(" + irs.road.getId() + ",");
			if(i == trafficLight) {
				sb.append("green:" + (irs.getTimeSlice() - irs.getUsedTimeUnits()) + ",[");
			} else {
				sb.append("red,[");
			}
			sb.append(junctionDeque.get(i).junctionToString(out));
			
			if(i != junctionDeque.size() - 1) {
				sb.append("]),");
			} else {
				sb.append("])");
			}
		}
		out.put("queues", sb.toString());
	}
}
