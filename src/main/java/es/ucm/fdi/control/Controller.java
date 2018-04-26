package es.ucm.fdi.control;

import java.io.*;

import es.ucm.fdi.control.eventbuilders.*;
import es.ucm.fdi.ini.*;
import es.ucm.fdi.model.*;
import es.ucm.fdi.model.events.Event;

/**
 * Launches the simulator
 * 
 * @author Daniel Herranz
 *
 */
public class Controller {
	// Array for parsing the events
	EventBuilder[] avaliableParsers = new EventBuilder[] { 
					new NewDirtRoadEventBuilder(),
					new NewLanesRoadEventBuilder(), 
					new NewRoadEventBuilder(), 
					new NewCarEventBuilder(),
					new NewBikeEventBuilder(), 
					new NewVehicleEventBuilder(), 
					new NewRoundRobinJunctionEventBuilder(),
					new NewMostCrowdedJunctionEventBuilder(), 
					new NewJunctionEventBuilder(),
					new MakeVehicleFaultyEventBuilder() };

	private TrafficSimulator simulator;
	private int ticks;
	private InputStream in;
	private OutputStream out;

	public Controller(TrafficSimulator simulator, int ticks, InputStream in, OutputStream out) {
		this.simulator = simulator;
		this.ticks = ticks;
		this.in = in;
		this.out = out;
	}

	public Controller(TrafficSimulator simulator, InputStream in) {
		this.simulator = simulator;
		this.in = in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public TrafficSimulator getSimulator() {
		return simulator;
	}

	/**
	 * Insert the events from an Ini File to the simulator.
	 * 
	 * @throws IOException
	 */
	public void loadEvents() throws IOException {
		// Vaciamos el multiTreeMap por si hubiera elementos anteriormente.
		// Esto sucede cuando se carga un fichero despues de haber cargado
		// eventos por primera vez y se quiere volver a cargar eventos
		getSimulator().getEvents().clear();
		try {
			Ini iniS = new Ini(in);
			for (IniSection sec : iniS.getSections()) {
				simulator.insertaEvento(parseSection(sec));
			}
		} catch (IOException e) {	
			throw new IOException("Error loading the ini section from the InputStream " + in, e);
		} catch (IniError i) {
			simulator.notifyError("There was an error parsing an Ini because of " + i);
		}
	}

	/**
	 * Runs the simulator by loading the events and simulating with that events
	 * 
	 * @throws IOException
	 */
	public void run() throws IOException {
		loadEvents();
		simulator.execute(out, ticks);
	}

	public void setOut(OutputStream out) {
		this.out = out;
	}

	/**
	 * Parse the IniSection and return the Event that matches with that IniSection
	 * if it does. If it does not, throws IllegalArgumentException
	 * 
	 * @param sec
	 * @return Event or Exception
	 */
	public Event parseSection(IniSection sec) {
		Event j = null;
		try {
			for (EventBuilder e : avaliableParsers) {
				if ((j = e.parse(sec)) != null) {
					break;
				}
			}
			if (j == null) {
				throw new IllegalArgumentException("Could not parse section " + sec);
			}
			
		} catch (IllegalArgumentException e) {
			simulator.notifyError(e.getMessage() + "\nCould not parse section " + sec + 
							" because it doesn't have the arguments we needed.");
		}
		return j;
	}
}
