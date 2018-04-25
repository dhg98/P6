package es.ucm.fdi.model.events;

import java.util.Map;

import es.ucm.fdi.model.Describable;
import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.RoadMap;

public class NewRoadEvent extends Event implements Describable {
	private int maxSpeed;
	private int length;
	private String src;
	private String id;
	private String dest;

	public NewRoadEvent(int time, String id, String src, String dest,
			int maxSpeed, int length) {
		super(time);
		this.maxSpeed = maxSpeed;
		this.length = length;
		this.src = src;
		this.id = id;
		this.dest = dest;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getLength() {
		return length;
	}

	public String getSrc() {
		return src;
	}

	public String getId() {
		return id;
	}

	public String getDest() {
		return dest;
	}

	/**
	 * Executes a NewRoadEvent and adds the NewBike to the RoadMap
	 */
	@Override
	public void execute(RoadMap rm) {
		Junction start = rm.getJunction(src);
		Junction end = rm.getJunction(dest);
		Road road = new Road(id, length, maxSpeed, start, end);
		rm.addRoad(road);
		start.getOutgoingRoadsList().add(road);
		end.addIncomingRoad(road);
	}

	@Override
	public String getType() {
		return "New Road " + id;
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
