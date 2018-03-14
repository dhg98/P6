package es.ucm.fdi.control.eventbuilders;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.*;

public class NewJunctionEventBuilder implements EventBuilder {

	@Override
	public Event parse(IniSection sec) throws IllegalArgumentException {
		if(!"new_junction".equals(sec.getTag())){
			return null;
		} else {
			try {
				int time = this.parseInt(sec, "time", 0);
				if (isValidId(sec.getValue("id"))){
					return new NewJunctionEvent(time, sec.getValue("id"));
				} else {
					throw new IllegalArgumentException("The id you´ve tried to parse contains invalid characters.");
				}
				
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("The time you've given is negative", e);
			}
		}
	}
	
	
		
}
