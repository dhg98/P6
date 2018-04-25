package es.ucm.fdi.control.eventbuilders;

import java.util.ArrayList;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.*;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.MakeVehicleFaultyEvent;

public class MakeVehicleFaultyEventBuilder implements EventBuilder {

	@Override
	public Event parse(IniSection sec) {
		if (!sec.getTag().equals("make_vehicle_faulty")) {
			return null;
		} else {
			try {
				int time = this.parseInt(sec, "time", 0);
				int duration = parseInt(sec, "duration", 0);
				ArrayList<String> a = parseIdList(sec, "vehicles");
				return new MakeVehicleFaultyEvent(time, a, duration);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Error while building NewVehicleEvent", e);
			}
		}
	}

}
