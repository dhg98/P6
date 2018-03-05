package es.ucm.fdi.control.eventbuilders;

import java.util.ArrayList;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.MakeVehicleFaultyEvent;
import es.ucm.fdi.model.NewJunctionEvent;
import es.ucm.fdi.model.NewVehicleEvent;

public class MakeVehicleFaultyEventBuilder implements EventBuilder {

	@Override
	public Event parse(IniSection sec) {
		if(!sec.getTag().equals("new_vehicle")){
			return null;
		} else {
			try {
				int time = this.parseInt(sec, "time", 0);
				int duration = parseInt(sec, "duration", 0);
				ArrayList<String> a = parseIdList(sec, "vehicles");
				return new MakeVehicleFaultyEvent(time, a, duration);
				
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("There was an error parsing a vehicle event", e);
			}
		}
	}

}
