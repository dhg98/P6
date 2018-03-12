package es.ucm.fdi.model;

public class NewLanesRoadEvent extends NewRoadEvent {
	
	private int lanes; 
	
	public NewLanesRoadEvent(int time, String id, String src, String dest, int maxSpeed, int length, int lanes) {
		super(time, id, src, dest, maxSpeed, length);
		this.lanes = lanes;
	}
	
	@Override
	public void execute(RoadMap roadMap) {
		Junction start = roadMap.getJunction(getSrc());
		Junction end = roadMap.getJunction(getDest());
		LanesRoad road = new LanesRoad(getId(), getLength(), getMaxSpeed(), start, end, lanes);
		roadMap.addRoad(road);
		start.getOutgoingRoadsList().add(road);
		end.addIncomingRoad(road);
	}

}
