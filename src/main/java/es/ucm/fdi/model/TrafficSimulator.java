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
	
	public void execute(OutputStream out) throws IOException{
		Map <String, String> report = new HashMap<>();
		eventProcess();
		advance();
		writeReport(report, out);
		++timeCounter;
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
		for (SimObject e : r.getSimObjects().values()) {
			e.fillReportDetails(report);
		}
		IniSection ini = new IniSection();
		for (String e : report.values()) {
			store(out);
		}
	}
}
