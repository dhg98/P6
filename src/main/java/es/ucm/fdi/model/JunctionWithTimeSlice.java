package es.ucm.fdi.model;

import java.util.ArrayDeque;
import java.util.Deque;

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
}
