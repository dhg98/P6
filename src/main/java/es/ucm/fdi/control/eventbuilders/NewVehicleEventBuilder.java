package es.ucm.fdi.control.eventbuilders;

import java.util.*;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.*;

public class NewVehicleEventBuilder implements EventBuilder {

	@Override
	public Event parse(IniSection sec) {
		if(!sec.getTag().equals("new_vehicle")){
			return null;
		} else {
			try {
				int time = this.parseInt(sec, "time", 0);
				int maxspeed = parseInt(sec, "max_speed", 1);
				List<String> a = parseIdList(sec, "itinerary");
				if (isValidId(sec.getValue("id"))){
					return new NewVehicleEvent(time, sec.getValue("id"), maxspeed, a);
				} else {
					throw new IllegalArgumentException("One of the ids you´ve tried to parse contains invalid characters.");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("There was an error parsing a vehicle event", e);
			}
		}
	}

}
