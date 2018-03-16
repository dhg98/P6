package es.ucm.fdi.model;

public class NewDirtRoadEvent extends NewRoadEvent {

	public NewDirtRoadEvent(int time, String id, String src, String dest, int maxSpeed, int length) {
		super(time, id, src, dest, maxSpeed, length);
	}
	
	/**
	 * Executes a NewDirtRoadEvent and adds the NewBike to the RoadMap
	 */
	@Override
	public void execute(RoadMap roadMap) {
		Junction start = roadMap.getJunction(getSrc());
		Junction end = roadMap.getJunction(getDest());
		DirtRoad road = new DirtRoad(getId(), getLength(), getMaxSpeed(), start, end);
		roadMap.addRoad(road);
		start.getOutgoingRoadsList().add(road);
		end.addIncomingRoad(road);
	}

}
