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
	
	@Override
	public void execute(RoadMap r) {
		Junction start = r.getJunction(src);
		Junction end = r.getJunction(dest);
		Road road = new Road(id, length, maxSpeed, start, end);
		r.addRoad(road);
		start.getOutgoingRoadsList().add(road);
		Junction.IncomingRoads ir = new Junction.IncomingRoads(road);
		end.getJunctionDeque().add(ir);
	}
}
