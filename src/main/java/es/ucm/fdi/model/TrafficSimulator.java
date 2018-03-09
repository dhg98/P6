package es.ucm.fdi.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.Map.Entry;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
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
		if (e.getTime() > timeCounter) {
			throw new RuntimeException("The time you have given for this event is previous than the current time");
		} else {
			Events.putValue(e.getTime(), e);
		}
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
			List<Event> arrayEvent = Events.get(timeCounter);
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
	
	IniSection createIniSection(Map<String, String> report) {
		IniSection ini = new IniSection(report.get(""));
		for (Entry<String, String> e : report.entrySet()) {
			ini.setValue(e.getKey(), e.getValue());
		}
		return ini;
	}
	
	public void writeReport(Map<String, String> report, OutputStream out) throws IOException {
		Ini file = new Ini();
		for (Junction j : r.getJunctions()) {
			j.report(timeCounter, report);
			file.addsection(createIniSection(report));
		}
		for (Road ro: r.getRoads()) {
			ro.report(timeCounter, report);
			file.addsection(createIniSection(report));
		}
		for (Vehicle v: r.getVehicles()) {
			v.report(timeCounter, report);
			file.addsection(createIniSection(report));
		}
		file.store(out);
	}
}
