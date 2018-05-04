package es.ucm.fdi.launcher;

import java.util.List;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.TrafficSimulator.Listener;
import es.ucm.fdi.model.events.Event;

public class ErrorListener implements Listener {
	@Override
	public void registered(int time, RoadMap map, List<Event> events) {}
	@Override
	public void reset(int time, RoadMap map, List<Event> events) {}
	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {}
	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {}
	
	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events,
			String error) {
		System.err.println(error);
	}
}
