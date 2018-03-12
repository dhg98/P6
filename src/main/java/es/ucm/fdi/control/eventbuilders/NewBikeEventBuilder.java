package es.ucm.fdi.control.eventbuilders;

import java.util.List;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewBikeEvent;

public class NewBikeEventBuilder implements EventBuilder {

	@Override
	public Event parse(IniSection sec) {
		if(!"new_vehicle".equals(sec.getTag()) && !"bike".equals(sec.getValue("type"))){
			return null;
		} else {
			try {
				int time = parseInt(sec, "time", 0);
				int maxspeed = parseInt(sec, "max_speed", 1);
				List<String> a = parseIdList(sec, "itinerary");
				if (isValidId(sec.getValue("id"))){
					return new NewBikeEvent(time, sec.getValue("id"), maxspeed, a);
				} else {
					throw new IllegalArgumentException("One of the ids you´ve tried to parse contains invalid characters.");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("There was an error parsing a bike event", e);
			}
		}
	}

}
