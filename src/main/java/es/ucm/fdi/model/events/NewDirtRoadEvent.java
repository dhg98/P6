package es.ucm.fdi.model.events;

import es.ucm.fdi.model.DirtRoad;
import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.RoadMap;

public class NewDirtRoadEvent extends NewRoadEvent {

	public NewDirtRoadEvent(int time, String id, String src,
			String dest, int maxSpeed, int length) {
		super(time, id, src, dest, maxSpeed, length);
	}

	/**
	 * Executes a NewDirtRoadEvent and adds it to the RoadMap
	 */
	@Override
	public void execute(RoadMap rm) {
		Junction start = rm.getJunction(getSrc());
		Junction end = rm.getJunction(getDest());
		DirtRoad road = new DirtRoad(getId(), getLength(),
				getMaxSpeed(), start, end);
		rm.addRoad(road);
		start.getOutgoingRoadsList().add(road);
		end.addIncomingRoad(road);
	}
}
