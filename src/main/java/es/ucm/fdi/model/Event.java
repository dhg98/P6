package es.ucm.fdi.model;

public abstract class Event {
	private int time;

	public Event(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	public abstract void execute(RoadMap r);
}
