package es.ucm.fdi.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class JunctionWithTimeSlice extends Junction {
	
	public JunctionWithTimeSlice(String id) {
		super(id);
	}

	private static class IncomingRoadWithTimeSlice extends IncomingRoads{
		
		private int timeSlice;
		private int usedTimeUnits;
		private int fullyUsed; 
		
		public IncomingRoadWithTimeSlice(Road road, int timeSlice, int usedTimeUnits, int fullyUsed) {
			super(road);
			this.timeSlice = timeSlice;
			this.usedTimeUnits = usedTimeUnits;
			this.fullyUsed = fullyUsed;
		}
		
		public int getTimeSlice() {
			return timeSlice;
		}

		public int getUsedTimeUnits() {
			return usedTimeUnits;
		}

		public boolean isFullyUsed() {
			return fullyUsed;
		}

		public boolean isUsed() {
			return true;
		}
		
	}
}
