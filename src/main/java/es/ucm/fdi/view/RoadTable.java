package es.ucm.fdi.view;

import java.util.List;

import javax.swing.JTable;

import es.ucm.fdi.model.Road;

public class RoadTable {
	private static final Object[] COLUMN_NAME = {"ID", "Source", "Target", "Lenght", "Max Speed", "Vehicles"};

	private JTable table;
	
	public RoadTable(List<Road> roadList) {
		Object [][] rowData = new Object[roadList.size()][COLUMN_NAME.length];
		int row = 0;
		for (Road road : roadList) {
			rowData[row][0] = road.getId();
			rowData[row][1] = road.getStart();
			rowData[row][2] = road.getEnd();
			rowData[row][3] = road.getSize();
			rowData[row][4] = road.getMaxVel();
			rowData[row][5] = road.toStringVehicles();
			row++;
		}
		table = new JTable(rowData, COLUMN_NAME);
	}
	
	public RoadTable() {
		table = new JTable(null, COLUMN_NAME);
	}

	public JTable getTable() {
		return table;
	}
	
}
