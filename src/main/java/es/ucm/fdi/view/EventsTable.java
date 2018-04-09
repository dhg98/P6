package es.ucm.fdi.view;

import java.util.List;

import javax.swing.JTable;

import es.ucm.fdi.model.Event;

public class EventsTable {
	String[] COLUMN_NAME = {"#", "Time", "Type"};
	
private JTable table;
	
	public EventsTable(List<Event> eventList) {
		Object [][] rowData = new Object[eventList.size()][COLUMN_NAME.length];
		int row = 0;
		for (Event event : eventList) {
			rowData[row][0] = row;
			rowData[row][1] = event.getTime();
			rowData[row][2] = event.getType();
			row++;
		}
		table = new JTable(rowData, COLUMN_NAME);
	}
	
	public EventsTable() {
		table = new JTable(null, COLUMN_NAME);
	}

	public JTable getTable() {
		return table;
	}

}
