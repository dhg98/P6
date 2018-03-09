package es.ucm.fdi.control;

import java.io.*;

import es.ucm.fdi.control.eventbuilders.*;
import es.ucm.fdi.ini.*;
import es.ucm.fdi.model.*;


public class Controller {
	public static final int TICKS_POR_DEFECTO = 10;
	
	EventBuilder[] avaliableParsers = new EventBuilder[]{new NewRoadEventBuilder(), new NewVehicleEventBuilder(), new NewJunctionEventBuilder(), new MakeVehicleFaultyEventBuilder()};
	
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
	
	public void loadEvents() throws IOException {
		Ini iniS = new Ini(in);
		for (IniSection sec : iniS.getSections()) {
			simulator.insertaEvento(parseSection(sec));
		}
	}
	
	public void run() throws IOException {
		loadEvents();
		simulator.execute(out, ticks);
	}
	
	public void setOut(OutputStream out) {
		this.out = out;
	}

	public Event parseSection (IniSection sec) {
		try {
			Event j = null;
			for (EventBuilder e : avaliableParsers) {
				j = e.parse(sec);
				if (j != null) {
					return j;
				}
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("There was an error while trying to parse an IniSection", e);
		}
		throw new IllegalArgumentException("There is no such event.");
	}
}
