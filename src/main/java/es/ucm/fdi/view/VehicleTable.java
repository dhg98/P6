package es.ucm.fdi.view;

import java.util.List;

import javax.swing.JTable;

import es.ucm.fdi.model.Vehicle;

public class VehicleTable {
	String[] COLUMN_NAME = {"ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
	
	private JTable table;
	
	public VehicleTable(List<Vehicle> vehicleList) {
		Object [][] rowData = new Object[vehicleList.size()][COLUMN_NAME.length];
		int row = 0;
		for (Vehicle vehicle : vehicleList) {
			rowData[row][0] = vehicle.getId();
			rowData[row][1] = vehicle.getRoad();
			rowData[row][2] = vehicle.getLocation();
			rowData[row][3] = vehicle.getVelAct();
			rowData[row][4] = vehicle.getKilometrage();
			rowData[row][5] = vehicle.getTiempoAveria();
			rowData[row][6] = vehicle.toStringItinerary();
			row++;
		}
		table = new JTable(rowData, COLUMN_NAME);
	}

	public VehicleTable() {
		table = new JTable(null, COLUMN_NAME);
	}

	public JTable getTable() {
		return table;
	}
	
}
