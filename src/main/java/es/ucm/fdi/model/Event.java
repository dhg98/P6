package es.ucm.fdi.model;

/**
 * Represents a Event of simulation
 * @author Daniel Herranz
 *
 */
public abstract class Event implements Describable {
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
	
	public abstract void execute(RoadMap rm);
	
	public abstract String getType();
}
