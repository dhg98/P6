package es.ucm.fdi.model;

public class NewRoundRobinJunctionEvent extends NewJunctionEvent {

	private int maxTimeSlice;
	private int minTimeSlice;
	
	public NewRoundRobinJunctionEvent(int time, String id, int maxTimeSlice, int minTimeSlice) {
		super(time, id);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice = minTimeSlice;
	}
	
	@Override
	public void execute(RoadMap r) {
		RoundRobinJunction j = new RoundRobinJunction(getId(), maxTimeSlice, minTimeSlice);
		r.addJunction(j);
	}


}
