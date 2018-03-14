package es.ucm.fdi.control.eventbuilders;

import java.util.List;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.*;

public class NewCarEventBuilder implements EventBuilder {

	@Override
	public Event parse(IniSection sec) {
		if(!"new_vehicle".equals(sec.getTag()) || !"car".equals(sec.getValue("type"))){
			return null;
		} else {
			try {
				int time = parseInt(sec, "time", 0);
				int maxspeed = parseInt(sec, "max_speed", 1);
				List<String> a = parseIdList(sec, "itinerary");
				int resistanceKm = parseInt(sec, "resistance", 1);
				double faultProbability = parseDouble(sec, "fault_probability", 0);
				int maxFaultDuration = parseInt(sec, "max_fault_duration", 1);
				long seed = parseLong(sec, "seed", 1);
				if (isValidId(sec.getValue("id"))){
					return new NewCarEvent(time, sec.getValue("id"), maxspeed, a, resistanceKm, faultProbability, maxFaultDuration, seed);
				} else {
					throw new IllegalArgumentException("One of the ids you´ve tried to parse contains invalid characters.");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("There was an error parsing a car event", e);
			}
		}
	}

}
