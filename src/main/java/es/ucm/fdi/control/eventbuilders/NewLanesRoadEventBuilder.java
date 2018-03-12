package es.ucm.fdi.control.eventbuilders;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.NewRoadEvent;

public class NewLanesRoadEventBuilder implements EventBuilder {

	@Override
	public Event parse(IniSection sec) {
		if(!sec.getTag().equals("new_road")){
			return null;
		} else {
			try {
				int time = this.parseInt(sec, "time", 0);
				int maxspeed = parseInt(sec, "max_speed", 1);
				int length = parseInt(sec, "length", 1);
				if (isValidId(sec.getValue("id")) && isValidId(sec.getValue("src")) && isValidId(sec.getValue("dest"))){
					return new NewRoadEvent(time, sec.getValue("id"), sec.getValue("src"), sec.getValue("dest"), maxspeed, length);
				} else {
					throw new IllegalArgumentException("One of the ids you´ve tried to parse contains invalid characters.");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("One of the numbers you've given is negative", e);
			}
		}
	}
}
