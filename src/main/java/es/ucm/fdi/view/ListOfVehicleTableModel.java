package es.ucm.fdi.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.TrafficSimulator.Listener;
import es.ucm.fdi.model.Vehicle;

public class ListOfVehicleTableModel extends AbstractTableModel implements Listener {
	private ArrayList <Vehicle> elements = new ArrayList<>(); //Mismo motivo que en SimWindow
	private String[] fieldNames = {"ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
	
	@Override
	public int getRowCount() {
		return elements.size();
	}

	@Override
	public int getColumnCount() {
		return fieldNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Map<String, String> description = new HashMap<>();
		elements.get(rowIndex).describe(description);
		return description.get(fieldNames[columnIndex]);
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		elements = (ArrayList<Vehicle>) map.getVehicles();
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		elements.clear();		
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
		
	}

	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		elements = (ArrayList<Vehicle>) map.getVehicles();
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events,
			String error) {
		
	}
}
