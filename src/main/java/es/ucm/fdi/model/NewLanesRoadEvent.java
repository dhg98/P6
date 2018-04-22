package es.ucm.fdi.model;

public class NewLanesRoadEvent extends NewRoadEvent {
	
	private int lanes; 
	
	public NewLanesRoadEvent(int time, String id, String src, String dest, int maxSpeed, int length, int lanes) {
		super(time, id, src, dest, maxSpeed, length);
		this.lanes = lanes;
	}
	
	/**
	 * Executes a NewLanesRoadEvent and adds the NewBike to the RoadMap
	 */
	@Override
	public void execute(RoadMap rm) {
		Junction start = rm.getJunction(getSrc());
		Junction end = rm.getJunction(getDest());
		LanesRoad road = new LanesRoad(getId(), getLength(), getMaxSpeed(), start, end, lanes);
		rm.addRoad(road);
		start.getOutgoingRoadsList().add(road);
		end.addIncomingRoad(road);
	}

}
