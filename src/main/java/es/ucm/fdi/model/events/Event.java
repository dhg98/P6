package es.ucm.fdi.model.events;

import java.util.Map;

import es.ucm.fdi.model.Describable;
import es.ucm.fdi.model.RoadMap;

/**
 * Represents a Event of simulation
 * 
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

	/**
	 * @return the type of event we are adding to the simulation.
	 */
	public abstract String getType();
	
	/**
	 * @return the information we have to show for each event.
	 */
	public abstract String toString();
	
	/**
	 * To describe an event, we have to put inside the map the time this
	 * event is goint to be added to the simulation and the type of event
	 * we are describing.
	 */
	public void describe(Map<String, String> out) {
	    out.put("Time", "" + getTime());
        out.put("Type", getType());
	}
}
