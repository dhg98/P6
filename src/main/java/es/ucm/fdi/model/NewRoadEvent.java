package es.ucm.fdi.model;

public class NewRoadEvent extends Event {
	private int maxSpeed;
	private int length;
	private String src;
	private String id;
	private String dest;
	
	public NewRoadEvent(int time, String id, String src, String dest, int maxSpeed, int length) {
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
	public void execute(RoadMap roadMap) {
		Junction start = roadMap.getJunction(src);
		Junction end = roadMap.getJunction(dest);
		Road road = new Road(id, length, maxSpeed, start, end);
		roadMap.addRoad(road);
		start.getOutgoingRoadsList().add(road);
		end.addIncomingRoad(road);
	}
}
