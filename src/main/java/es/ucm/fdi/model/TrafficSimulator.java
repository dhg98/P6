package es.ucm.fdi.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import es.ucm.fdi.util.MultiTreeMap;

public class TrafficSimulator {
	private RoadMap r;
	private int timeCounter;
	private MultiTreeMap<Integer, Event> Events = new MultiTreeMap <>();
	
	public TrafficSimulator() {
		r = new RoadMap();
		timeCounter = 0;
	}
	
	public void insertaEvento(Event e) {
		Events.putValue(e.getTime(), e);
	}
	
	public void execute(OutputStream out, int pasosSimulacion) throws IOException{
		Map <String, String> report = new HashMap<>();
		int limiteTiempo = timeCounter + pasosSimulacion - 1;
		while (timeCounter <= limiteTiempo) {
			eventProcess();
			advance();
			++timeCounter;
			if (out != null) {
				writeReport(report, out);
			}
		}
	}
	
	public void eventProcess() throws IllegalArgumentException {
		try {
			ArrayList<Event> arrayEvent = Events.get(timeCounter);
			int i = 0;
			while (i < arrayEvent.size()) {
				Event e = arrayEvent.get(i);
				e.execute(r);
				++i;
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("There was an error while processing the events", e);
		}
	}
	
	public void advance() throws IllegalArgumentException {
		try {
			for (Junction j : r.getJunctions()) {
				j.avanza();
			}
			for (Road ro: r.getRoads()) {
				ro.avanza();
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("There was an error while advancing the objects", e);
		}
	}
	
	public void writeReport(Map<String, String> report, OutputStream out) throws IOException {
		for (Junction j : r.getJunctions()) {
			j.report(timeCounter, report);
		}
		for (Road ro: r.getRoads()) {
			ro.report(timeCounter, report);
		}
		for (Vehicle v: r.getVehicles()) {
			v.report(timeCounter, report);
		}
	}
}
