package es.ucm.fdi.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.Map.Entry;

import es.ucm.fdi.ini.*;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.util.*;

/**
 * Simulates the execution
 * 
 * @author Daniel Herranz
 *
 */
public class TrafficSimulator {
	private RoadMap rm;
	private List<Listener> listeners = new ArrayList<>();
	private int timeCounter;
	private MultiTreeMap<Integer, Event> events = new MultiTreeMap<>();

	public TrafficSimulator() {
		rm = new RoadMap();
		timeCounter = 0;
	}

	public MultiTreeMap<Integer, Event> getEvents() {
		return events;
	}

	public RoadMap getRm() {
		return rm;
	}

	public void setTimeCounter(int timeCounter) {
		this.timeCounter = timeCounter;
	}

	public int getTimeCounter() {
		return timeCounter;
	}

	public void setRm(RoadMap rm) {
		this.rm = rm;
	}

	public void setEvents(MultiTreeMap<Integer, Event> events) {
		this.events = events;
	}

	/**
	 * Insert the event in order to execute it if the time is previous than the
	 * Simulation Time.
	 * 
	 * @param e
	 * @throws RuntimeException
	 */
	public void insertaEvento(Event e) {
		if (e.getTime() < timeCounter) {
			notifyError("The time you have given to the event (" + e.getTime() + ")" + e
					+ " is previous than the current time (" + timeCounter + ")");
		} else {
			events.putValue(e.getTime(), e);
			notifyEventAdded();
		}
	}

	public void addSimulatorListener(Listener l) {
		listeners.add(l);
		notifyRegistered(l);
	}

	public void removeListener(Listener l) {
		listeners.remove(l);
	}

	public void notifyRegistered(Listener o) {
		o.registered(timeCounter, rm, events.valuesList());
	}

	public void notifyReset() {
		for (Listener l : listeners) {
			l.reset(timeCounter, rm, events.valuesList());
		}
	}

	public void notifyEventAdded() {
		for (Listener l : listeners) {
			l.eventAdded(timeCounter, rm, events.valuesList());
		}
	}

	public void notifyAdvanced() {
		for (Listener l : listeners) {
			l.advanced(timeCounter, rm, events.valuesList());
		}
	}

	public void notifyError(String error) {
		for (Listener l : listeners) {
			l.simulatorError(timeCounter, rm, events.valuesList(), error);
		}
	}

	/**
	 * Execute the simulation of the trafficSimulator a number of pasosSimulacion
	 * 
	 * @param out
	 * @param pasosSimulacion
	 * @throws IOException
	 */
	public void execute(OutputStream out, int pasosSimulacion) throws IOException {
		int limiteTiempo = timeCounter + pasosSimulacion - 1;
		while (timeCounter <= limiteTiempo) {
			eventProcess();
			advance();
			notifyAdvanced();
			++timeCounter;
			try {
				writeReport(out);
			} catch (IOException e) {
				notifyError(e.getMessage());
			}
		}
	}

	/**
	 * Process the events for the timeCounter of the simulator
	 * 
	 * @throws IllegalArgumentException
	 */
	public void eventProcess() {
		if (events.get(timeCounter) != null) {
			for (Event e : events.get(timeCounter)) {
				try {
					e.execute(rm);
				} catch (IllegalArgumentException m) {
					notifyError("There was an error while processing the event" + e.toString());
				}
			}
		}

	}

	/**
	 * Append reports for each object to main report
	 * 
	 * @param objects
	 * @param report
	 */
	public void fillReport(Iterable<? extends SimObject> objects, Ini report) {
		Map<String, String> objectReport = new LinkedHashMap<>();
		for (SimObject o : objects) {
			o.report(timeCounter, objectReport);
			report.addsection(createIniSection(objectReport));
			objectReport.clear();
		}
	}

	/**
	 * We advance the Roads and the Junctions.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void advance() {

		for (Road r : rm.getRoads()) {
			if (r.getNumVehicles() > 0) {
				try {
					r.avanza();
				} catch (IllegalArgumentException e) {
					notifyError("There was an error while advancing the road" + r.getId());
				}
			}
		}
		for (Junction j : rm.getJunctions()) {
			try {
				j.avanza();
			} catch (IllegalArgumentException e) {
				notifyError("There was an error while advancing the junction" + j.getId());
			}
		}

	}

	/**
	 * Create an IniSection from a Map given the statement of the project
	 * 
	 * @param report
	 * @return
	 */
	IniSection createIniSection(Map<String, String> report) {
		IniSection ini = new IniSection(report.get(""));
		report.remove("");
		for (Entry<String, String> e : report.entrySet()) {
			ini.setValue(e.getKey(), e.getValue());
		}
		return ini;
	}

	/**
	 * Writes a report in a OutputStream. The order is Junctions, then Roads and at
	 * last Vehicles.
	 * 
	 * @param report
	 * @param out
	 * @throws IOException
	 */
	public void writeReport(OutputStream out) throws IOException {
		Ini file = new Ini();
		fillReport(rm.getJunctions(), file);
		fillReport(rm.getRoads(), file);
		fillReport(rm.getVehicles(), file);
		file.store(out);
	}

	public interface Listener {
		public void registered(int time, RoadMap map, List<Event> events);

		public void reset(int time, RoadMap map, List<Event> events);

		public void eventAdded(int time, RoadMap map, List<Event> events);

		public void advanced(int time, RoadMap map, List<Event> events);

		public void simulatorError(int time, RoadMap map, List<Event> events, String error);
	}

	public void reset() {
		timeCounter = 0;
		rm = new RoadMap();
		events.clear();
		notifyReset();
	}
}
