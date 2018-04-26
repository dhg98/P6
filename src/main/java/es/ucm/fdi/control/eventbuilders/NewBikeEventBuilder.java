package es.ucm.fdi.control.eventbuilders;

import java.util.List;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewBikeEvent;

public class NewBikeEventBuilder implements EventBuilder {

	@Override
	public Event parse(IniSection sec) {
		if (!"new_vehicle".equals(sec.getTag()) || !"bike".equals(sec.getValue("type"))) {
			return null;
		} else {
			try {
				int time = parseInt(sec, "time", 0);
				int maxspeed = parseInt(sec, "max_speed", 1);
				List<String> a = parseIdList(sec, "itinerary");
				if (isValidId(sec.getValue("id"))) {
					return new NewBikeEvent(time, sec.getValue("id"), maxspeed, a);
				} else {
					throw new IllegalArgumentException(
							"The id " + sec.getValue("id") + " contains invalid"
									+ "characters in the IniSection\n" + sec);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Error while building a NewBikeEvent\n"
						+ e.getMessage(), e);
			}
		}
	}

}
