package es.ucm.fdi.model.events;

import java.util.Map;

import es.ucm.fdi.model.Describable;
import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.RoadMap;

public class NewJunctionEvent extends Event implements Describable {
	private String id;

	public NewJunctionEvent(int time, String id) {
		super(time);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	/**
	 * Executes a NewJunctionEvent and adds it to the RoadMap
	 */
	@Override
	public void execute(RoadMap rm) {
		rm.addJunction(new Junction(id));
	}

	@Override
	public String getType() {
		return "New Junction " + id;
	}

	@Override
	public void describe(Map<String, String> out) {
		out.put("Time", "" + getTime());
		out.put("Type", getType());
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
}