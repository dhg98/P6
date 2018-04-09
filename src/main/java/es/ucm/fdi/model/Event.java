package es.ucm.fdi.model;

/**
 * Represents a Event of simulation
 * @author Daniel Herranz
 *
 */
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
	
	public abstract String getType();
}
