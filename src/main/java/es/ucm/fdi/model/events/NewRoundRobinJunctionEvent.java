package es.ucm.fdi.model.events;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.RoundRobinJunction;

public class NewRoundRobinJunctionEvent extends NewJunctionEvent {

	private int maxTimeSlice;
	private int minTimeSlice;

	public NewRoundRobinJunctionEvent(int time, String id,
			int maxTimeSlice, int minTimeSlice) {
		super(time, id);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice = minTimeSlice;
	}

	@Override
	public void execute(RoadMap r) {
		r.addJunction(new RoundRobinJunction(getId(), maxTimeSlice, minTimeSlice));
	}

	@Override
	public String toString() {
		return "New Round Robin Event";
	}
}
